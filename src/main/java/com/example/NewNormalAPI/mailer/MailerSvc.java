package com.example.NewNormalAPI.mailer;

public interface MailerSvc {

	public void sendVerificationCode(String to, String verificationCode);
	
}
