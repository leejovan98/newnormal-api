package com.example.NewNormalAPI.user;

public class UserNotVerifiedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

    public UserNotVerifiedException(String email) {
        super("account associated to email=" + email + " is not verified");
    }
}
