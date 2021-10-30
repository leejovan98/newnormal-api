package com.example.NewNormalAPI.vaccinationVerification;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VaccinationVerificationController {
	
	@GetMapping("/vaccination/verification")
	public String uploadPage(@CookieValue("jwt") String jwtCookie, Model model) {
		System.out.println(jwtCookie);
		return "hi";
	}

}
