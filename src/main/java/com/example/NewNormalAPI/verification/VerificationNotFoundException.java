package com.example.NewNormalAPI.verification;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class VerificationNotFoundException extends RuntimeException{
	
   private static final long serialVersionUID = 1L;

   public VerificationNotFoundException() {
       super("Verification link is either invalid or account has already been verified");
   }

}
