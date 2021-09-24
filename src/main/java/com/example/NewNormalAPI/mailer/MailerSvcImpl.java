package com.example.NewNormalAPI.mailer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@ConfigurationProperties(prefix="serivce.mailer")
public class MailerSvcImpl implements MailerSvc {

	@Autowired
	JavaMailSender sender;
	
	private String baseUrl;
	
	
	public void sendVerificationCode(String to, String verificationCode) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject("Account Verification");
		message.setText("Please click the following link to verify your account: " + baseUrl + verificationCode);
		sender.send(message);
	}

	
}
