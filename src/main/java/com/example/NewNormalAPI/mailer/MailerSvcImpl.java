package com.example.NewNormalAPI.mailer;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.example.NewNormalAPI.event.Event;

import lombok.Data;

@Service
@ConfigurationProperties(prefix="mailer")
@Data
@EnableAsync
public class MailerSvcImpl implements MailerSvc {

	@Autowired
	JavaMailSender sender;
	
	@Autowired
	SpringTemplateEngine templateEngine;
	
	private String baseUri;
	private String verificationPath;
	private String eventInvitationPath;
	
        /**
         * Sends verification code to user's email for users to verify their accounts
         * 
         * @param mail which is user's email address
         */
	@Async
	public void sendVerificationCode(Mail mail) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(mail.getTo());
		message.setSubject(mail.getSubject());
		String verificationCode = (String) mail.getProperties().get("verificationCode");
//		message.setSubject("Account Verification");
		message.setText("Please click the following link to verify your account: " + baseUri + verificationPath + verificationCode);
		sender.send(message);
	}

        /**
         * Sends created event to user's email
         * 
         * @param mail which is user's email address
         * @throws MessagingException
         */
	@Async
	public void sendEventCreation(Mail mail) throws MessagingException {
		MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        Context context = new Context();
        
        Event event = (Event) mail.getProperties().get("event");
        
        Map<String, Object> vars = new HashMap<>();
        vars.put("event", event);
        vars.put("inviteLink", baseUri + eventInvitationPath + event.getInviteCode());
        context.setVariables(vars);
    
        String html = templateEngine.process("eventcreation", context);
        helper.setTo(mail.getTo());
        helper.setText(html, true);
        helper.setSubject(mail.getSubject());
        sender.send(message);
	}
	
	/**
         * Sends subscription confirmation to user's email address
         * 
         * @param mail which is user's email address
         * @throws MessagingException
         */
	@Async
	public void sendSubscriptionConfirmation(Mail mail) throws MessagingException {
		MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        Context context = new Context();
        
        Event event = (Event) mail.getProperties().get("event");
        
        Map<String, Object> vars = new HashMap<>();
        vars.put("event", event);
        context.setVariables(vars);
    
        String html = templateEngine.process("subscriptionconfirmation", context);
        helper.setTo(mail.getTo());
        helper.setText(html, true);
        helper.setSubject(mail.getSubject());
        sender.send(message);
	}

	
}
