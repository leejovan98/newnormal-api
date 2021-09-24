package com.example.NewNormalAPI.login;

import com.example.NewNormalAPI.user.CustomUserDetailsService;
// import com.example.NewNormalAPI.user.UserExistsException;

import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginRequestController {
    private CustomUserDetailsService userService;

    public LoginRequestController(CustomUserDetailsService userService) {
        this.userService = userService;
    }

    /**
     * User login authentication
     * 
     * @return true if user logs in successfully
     */
    @GetMapping("/login")
    public Boolean userLogin(String username, String password) {
        UserDetails userDetails = userService.loadUserByUsername(username);

        if (userDetails == null) {
            return false;
        }

        if (password.equals(userDetails.getPassword())) {
            return true; // login successfully
        }
        return false;
    }
}
