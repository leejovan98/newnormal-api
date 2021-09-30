package com.example.accountsservice.verification;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.example.accountsservice.user.User;

import lombok.Data;

@Entity
@Data
public class Verification {

	@Id
	private String verificationCode;

	@OneToOne
	@JoinColumn(name="user_id")
	private User user;

	@Override
	public String toString() {
		return "Verification [verificationCode=" + verificationCode + ", userId=" + user.getId() + "]";
	}
	
}
