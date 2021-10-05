package com.example.EventsService.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED)
public class UserNotAuthorisedException extends Exception {
    public UserNotAuthorisedException(String message) {
        super(message);
    }
}
