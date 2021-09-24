package com.example.NewNormalAPI.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	private CustomUserDetailsService userSvc;
    private BCryptPasswordEncoder encoder;

    // Constructor
    @Autowired
    public UserController(CustomUserDetailsService userSvc, BCryptPasswordEncoder encoder) {
        this.userSvc = userSvc;
        this.encoder = encoder;
    }

    // @GetMapping("/users")
    // public List<User> getUsers() {
    //     return users.findAll();
    // }

    /**
    * Checks if User already exists in the database
    * 
    * @param user
    * @throws UserExistsException
    * @return user
    */
    @PostMapping("/users")
    public User addUser(@RequestBody User user) throws UserExistsException{
        user.setPassword(encoder.encode(user.getPassword()));
        return userSvc.createUser(user);
    }
}
