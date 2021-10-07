package com.example.NewNormalAPI.user;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.NewNormalAPI.mailer.Mail;
import com.example.NewNormalAPI.mailer.MailerSvc;
import com.example.NewNormalAPI.verification.Verification;
import com.example.NewNormalAPI.verification.VerificationNotFoundException;
import com.example.NewNormalAPI.verification.VerificationService;

@RestController
public class UserController {

    private UserDetailsServiceImpl userSvc;
    private BCryptPasswordEncoder encoder;
    private VerificationService verifSvc;
    private MailerSvc mailer;

    // Constructor
    @Autowired
    public UserController(UserDetailsServiceImpl userSvc, BCryptPasswordEncoder encoder,
                          VerificationService verifSvc, MailerSvc mailer) {
        this.userSvc = userSvc;
        this.encoder = encoder;
        this.verifSvc = verifSvc;
        this.mailer = mailer;
    }

    /**
    * Checks if User already exists in the database
    * Adds user if does not exist 
    *
    * @param user
    * @throws UserExistsException
    * @return user
    */
    @PostMapping("/accounts/user")
    @ResponseStatus(HttpStatus.OK)
    public void addUser(@RequestBody User user) {

        user.setPassword(encoder.encode(user.getPassword()));
        User newUser = userSvc.createUser(user);
        Verification v = constructVerification(newUser);
        verifSvc.save(v);
        
        // prepare mail details
        Mail mail = new Mail();
        mail.setTo(newUser.getEmail());
        mail.setSubject("Account Verification");
        Map<String, Object> props = new HashMap<>();
        props.put("verificationCode", v.getVerificationCode());
        mail.setProperties(props);
        
        mailer.sendVerificationCode(mail);
    }
    
	@GetMapping("/accounts/verify/{code}")
	@ResponseStatus(HttpStatus.OK)
	public void verifyUser(@PathVariable String code) {

		Optional<Verification> search = verifSvc.findById(code);
		if(search.isEmpty()) throw new VerificationNotFoundException(code);
		
		Verification verification = search.get();
		User user = verification.getUser();
		
		user.setVerified("Y");
		user.setVerification(null); // delete corresponding verification record
		
		userSvc.update(user);
	}
    
    /**
     * Generates verification code using current date and time
     *
     * @param id   user id
     * @return verification code
     */
    public String generateVerificationCode(Long id) {
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyHHmmss");
        Date currDt = new Date();

        return id.toString() + format.format(currDt);
    }

    /**
     * Creates a Verification object containing the verification code
     *
     * @param user
     * @return v   (verification code)
     */
    public Verification constructVerification(User user) {
        Verification v = new Verification();
        v.setUser(user);
        v.setVerificationCode(generateVerificationCode(user.getId()));
        return v;
    }


}
