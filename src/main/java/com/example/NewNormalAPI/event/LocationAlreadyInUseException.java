package com.example.EventsService.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LocationAlreadyInUseException extends Exception {
    public LocationAlreadyInUseException(String message) {
        super(message);
    }
}
