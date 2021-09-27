package com.example.NewNormalAPI.login;

public class LoginFailedException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public LoginFailedException(String email) {
        super("login attempt failed for email=" + email);
    }
    
}
