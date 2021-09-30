package com.example.accountsservice.verification;

public class VerificationNotFoundException extends RuntimeException{
	
   private static final long serialVersionUID = 1L;

   public VerificationNotFoundException(String code) {
       super("Verification code " + code + " is invalid or has already been activated");
   }

}
