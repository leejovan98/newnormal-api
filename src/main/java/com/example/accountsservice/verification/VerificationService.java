package com.example.accountsservice.verification;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationService{
	
	@Autowired
	private VerificationRepository verifications;
	
	public void delete(Verification verification) {
		verifications.delete(verification);
	}
	
	public Verification save(Verification verification) {
		return verifications.save(verification);
	}
	
	public Optional<Verification> findById(String verificationCode){
		return verifications.findById(verificationCode);
	}

}
