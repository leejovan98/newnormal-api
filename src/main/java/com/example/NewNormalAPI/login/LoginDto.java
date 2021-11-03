package com.example.NewNormalAPI.login;

import com.example.NewNormalAPI.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDto {
	
	private String jwt;
	
	private User user;
}
