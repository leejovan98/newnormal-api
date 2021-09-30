package com.example.accountsservice.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.accountsservice.login.LoginFailedException;
import com.example.accountsservice.user.UserAlreadyExistsException;
import com.example.accountsservice.user.UserNotVerifiedException;
import com.example.accountsservice.verification.VerificationNotFoundException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
	
	@ResponseStatus(code=HttpStatus.UNAUTHORIZED, reason="login failed")
	@ExceptionHandler(LoginFailedException.class)
	public void handleLoginFailedException() {
	}
	
	@ResponseStatus(code=HttpStatus.FORBIDDEN, reason="account is not verified")
	@ExceptionHandler(UserNotVerifiedException.class)
	public void handleUserNotVerifiedException() {
	}
	
	@ResponseStatus(code=HttpStatus.BAD_REQUEST, reason="username / email already in use")
	@ExceptionHandler(UserAlreadyExistsException.class)
	public void handleUserAlreadyExistsException() {
	}
	
	@ResponseStatus(code=HttpStatus.NOT_FOUND, reason="verification code is invalid or has already been activated")
	@ExceptionHandler(VerificationNotFoundException.class)
	public void handleVerificationNotFoundException() {
	}
}
