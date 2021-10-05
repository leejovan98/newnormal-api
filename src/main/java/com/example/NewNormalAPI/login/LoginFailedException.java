package com.example.NewNormalAPI.login;

public class LoginFailedException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public LoginFailedException() {
        super("incorrect username or password received");
    }
    
}
