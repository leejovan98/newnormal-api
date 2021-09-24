package com.example.NewNormalAPI.user;

import org.springframework.http.HttpStatus;
import com.example.NewNormalAPI.mailer.MailerSvcImpl;
import com.example.NewNormalAPI.verification.Verification;
import com.example.NewNormalAPI.verification.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class UserController {
	
	private CustomUserDetailsService userSvc;
    private BCryptPasswordEncoder encoder;
    private VerificationService verifSvc;
    private MailerSvcImpl mailer;

    // Constructor
    @Autowired
    public UserController(CustomUserDetailsService userSvc, BCryptPasswordEncoder encoder,
                          VerificationService verifSvc, MailerSvcImpl mailer) {
        this.userSvc = userSvc;
        this.encoder = encoder;
        this.verifSvc = verifSvc;
        this.mailer = mailer;
    }

    // @GetMapping("/users")
    // public List<User> getUsers() {
    //     return users.findAll();
    // }

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
    public User addUser(@RequestBody User user) throws UserExistsException{

        user.setPassword(encoder.encode(user.getPassword()));
        Verification v = constructVerification(user);
        verifSvc.save(v);
        mailer.sendVerificationCode(user.getEmail(), v.getVerificationCode());
        return userSvc.createUser(user);
    }

    public String generateVerificationCode(Long id) {
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyHHmmss");
        Date currDt = new Date();

        return id.toString() + format.format(currDt);
    }

    public Verification constructVerification(User user) {
        Verification v = new Verification();
        v.setUser(user);
        v.setVerificationCode(generateVerificationCode(user.getId()));
        return v;
    }


}
