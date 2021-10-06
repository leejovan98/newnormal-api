package com.example.EventsService.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EventFullySubscribedException extends Exception {
    public EventFullySubscribedException(String message) {
        super(message);
    }
}
