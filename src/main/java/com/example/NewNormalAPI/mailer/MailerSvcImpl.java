package com.example.NewNormalAPI.mailer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import lombok.Data;

@Service
@ConfigurationProperties(prefix="serivce.mailer")
@Data
@EnableAsync
public class MailerSvcImpl implements MailerSvc {

	@Autowired
	JavaMailSender sender;
	
	private String baseUrl;
	
	@Async
	public void sendVerificationCode(String to, String verificationCode) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject("Account Verification");
		message.setText("Please click the following link to verify your account: " + baseUrl + verificationCode);
		sender.send(message);
	}

	public void setBaseUrl(String baseUrl){
		this.baseUrl = baseUrl;
	}

	
}
