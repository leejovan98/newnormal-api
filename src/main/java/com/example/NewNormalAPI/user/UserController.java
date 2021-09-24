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

import jdk.jfr.Description;

@RestController
public class UserController {
    @Autowired
    private UserRepository users;
    @Autowired
    private BCryptPasswordEncoder encoder;

    // Constructor
    public UserController(UserRepository users, BCryptPasswordEncoder encoder) {
        this.users = users;
        this.encoder = encoder;
    }

    // @GetMapping("/users")
    // public List<User> getUsers() {
    //     return users.findAll();
    // }

    /**
    * Checks if User already exists in the database
    * @param user
    * @throws userAlreadyExistsException TODO: Create exception
    * @return user
    */
    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user) throws UserAlreadyExistsException{
        //TODO: use service to check if user is already in database
        if (/* user already in database*/) {
            // throw exception
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return users.save(user);
    }
