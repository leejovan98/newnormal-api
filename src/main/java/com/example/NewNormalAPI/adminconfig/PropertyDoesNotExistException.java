package com.example.NewNormalAPI.adminconfig;

public class PropertyDoesNotExistException extends Exception {
    private static final long serialVersionUID = 1L;

    public PropertyDoesNotExistException(String message) {
        super(message);
    }
}