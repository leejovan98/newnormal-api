package com.example.accountsservice.user;

public class UserNotVerifiedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

    public UserNotVerifiedException() {
        super("account is not verified");
    }
}
