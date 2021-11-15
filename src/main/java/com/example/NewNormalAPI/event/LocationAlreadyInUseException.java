package com.example.NewNormalAPI.event;

public class LocationAlreadyInUseException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public LocationAlreadyInUseException(String message) {
        super(message);
    }
}
