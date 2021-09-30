package com.example.accountsservice.mailer;

public interface MailerSvc {

	public void sendVerificationCode(String to, String verificationCode);
	
}
