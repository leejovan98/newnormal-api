package com.example.NewNormalAPI.user;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
    * @throws UserExistsException
    * @return user
    */
    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user) throws UserExistsException{
        userService = new CustomUserDetailsService(users);
        
        userService.createUser(user);
        user.setPassword(encoder.encode(user.getPassword()));
        return users.save(user);
    }
