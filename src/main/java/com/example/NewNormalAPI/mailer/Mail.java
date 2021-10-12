package com.example.NewNormalAPI.mailer;

import java.util.Map;

import lombok.Data;

@Data
public class Mail {
	
	private String to;
	private String subject;
	private Map<String, Object> properties;
}
