package com.example.NewNormalAPI.verification;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationService{
	
	@Autowired
	private VerificationRepository verifications;
	
	/**
	 * Deletes verification of account creation
	 * 
	 * @param verification
	 */
	public void delete(Verification verification) {
		verifications.delete(verification);
	}
	
	/**
	 * Saves verification of account creation
	 * 
	 * @param verification
	 * @return
	 */
	public Verification save(Verification verification) {
		return verifications.save(verification);
	}
	
	/**
	 * Finds verification code in repository
	 * 
	 * @param verificationCode
	 * @return verification
	 */
	public Optional<Verification> findById(String verificationCode){
		return verifications.findById(verificationCode);
	}

}
