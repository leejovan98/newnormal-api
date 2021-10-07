package com.example.NewNormalAPI.mailer;

import javax.mail.MessagingException;

public interface MailerSvc {

	public void sendVerificationCode(Mail mail);
	
	public void sendEventCreation(Mail mail) throws MessagingException;
	
	public void sendSubscriptionConfirmation(Mail mail) throws MessagingException;
}
