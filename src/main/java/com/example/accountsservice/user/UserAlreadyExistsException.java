package com.example.accountsservice.user;

public class UserAlreadyExistsException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public UserAlreadyExistsException() {
        super("Username or email already in use");
    }
    
}
