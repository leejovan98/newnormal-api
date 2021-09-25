package com.example.NewNormalAPI.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.NewNormalAPI.user.User;
import com.example.NewNormalAPI.user.UserDetailsServiceImpl;

@RestController
public class LoginRequestController {
    private UserDetailsServiceImpl userService;

    @Autowired
    public LoginRequestController(UserDetailsServiceImpl userService) {
        this.userService = userService;
    }

    /**
     * User login authentication
     * 
     * @return true if user logs in successfully
     */
    @GetMapping("/login")
    public User userLogin(@RequestBody User user) {
        return userService.authenticate(user);
    }
}
