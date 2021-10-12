package com.example.NewNormalAPI.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.NewNormalAPI.event.UserNotAuthorisedException;
import com.example.NewNormalAPI.login.LoginFailedException;
import com.example.NewNormalAPI.user.UserAlreadyExistsException;
import com.example.NewNormalAPI.user.UserNotVerifiedException;
import com.example.NewNormalAPI.verification.VerificationNotFoundException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
	
	@ResponseStatus(code=HttpStatus.UNAUTHORIZED, reason="No token passed")
	@ExceptionHandler(MissingRequestCookieException.class)
	public void handleMissingRequestCookieException() {
	}
	
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
	
	
	@ResponseStatus(code=HttpStatus.FORBIDDEN, reason="You are not authorised to create this event")
	@ExceptionHandler(UserNotAuthorisedException.class)
	public void handleUserNotAuthorisedException() {
	}
}
