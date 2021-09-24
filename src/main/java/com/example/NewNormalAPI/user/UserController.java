/**
 * Controller for user.
 * 
 * @version 1.0 , 24 Sept 2021
 * @author Chew Chong Jun
 */

package com.example.NewNormalAPI.user;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private UserRepository users;
    private BCryptPasswordEncoder encoder;

    public UserController(UserRepository users, BCryptPasswordEncoder encoder) {
        this.users = users;
        this.encoder = encoder;
    }

    // @GetMapping("/users")
    // public List<User> getUsers() {
    //     return users.findAll();
    // }

    /**
    * Using BCrypt encoder to encrypt the password for storage 
    * @param user
    * @return 
    */
    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user){
        // use service to check if user is already in database
        user.setPassword(encoder.encode(user.getPassword()));
        return users.save(user);
    }
