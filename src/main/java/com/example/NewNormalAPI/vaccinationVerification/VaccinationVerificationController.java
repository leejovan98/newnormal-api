package com.example.NewNormalAPI.vaccinationVerification;

import java.util.Date;
import java.util.Objects;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.NewNormalAPI.jwt.util.JwtUtil;
import com.example.NewNormalAPI.user.User;
import com.example.NewNormalAPI.user.UserDetailsServiceImpl;

@Controller
public class VaccinationVerificationController {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsSvc;
	
	@Autowired
	private VaccinationCertificateProcessor processor;
	
	@Autowired
	private UserDetailsServiceImpl userSvc;
	
	/**
	 * Gets page for uploading of vaccination status
	 * 
	 * @param jwt
	 * @return "vaxupload.html"
	 */
	@GetMapping("/vaccination/verify")
	public String getPage(@CookieValue(value="jwt", required=false) String jwt) {
		if(Objects.isNull(jwt)) return "unauth";
		User user = userDetailsSvc.loadUserEntityByUsername(jwtUtil.extractUsername(jwt));    
		if(Objects.isNull(user)) return "unauth";
		
		if(user.getVaccinated().equalsIgnoreCase("Y")) return "alreadyverified";
		
		return "vaxupload";
	}
	
	/**
	 * Allows user to upload vaccination status 
	 * 
	 * @param model
	 * @param jwt
	 * @param image
	 * @param response
	 * @return "error.html" if there is no image
	 * @return "unauth.html" if user unauthenticated
	 * @return "manualverification" if user updates vaccination status
	 * @return "complete" if vaccination status upload is successful
	 */
	@PostMapping("/vaccination/verify")
	public String uploadVaccination(Model model, @CookieValue("jwt") String jwt, @RequestParam MultipartFile image, 
			HttpServletResponse response) {
		if(Objects.isNull(image) || image.isEmpty()) return "error";
		if(Objects.isNull(jwt)) return "unauth";
		String username = jwtUtil.extractUsername(jwt);
		User user = userDetailsSvc.loadUserEntityByUsername(username); 

		if(Objects.isNull(user) || !jwtUtil.validateToken(jwt, user)) return "unauth";
		
		try {
			Date date = processor.process(image, username);
			if(Objects.nonNull(date)) userSvc.updateVaccinationStatus(username, date);
			else return "manualverification";
		} catch(IllegalArgumentException e) {
			return "error";
		}
	
		
		return "complete";
	}

}
