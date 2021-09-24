package com.example.NewNormalAPI.login;

import com.example.NewNormalAPI.user.CustomUserDetailsService;
// import com.example.NewNormalAPI.user.UserExistsException;
import com.example.NewNormalAPI.user.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginRequestController {
    private CustomUserDetailsService userService;

    @Autowired
    public LoginRequestController(CustomUserDetailsService userService) {
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
