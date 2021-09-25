package com.example.NewNormalAPI.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AccNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public AccNotFoundException() {
        super("Account not found");
    }
    
}
