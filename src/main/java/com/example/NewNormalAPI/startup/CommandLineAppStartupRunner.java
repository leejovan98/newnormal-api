package com.example.NewNormalAPI.startup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.NewNormalAPI.user.User;
import com.example.NewNormalAPI.user.UserAlreadyExistsException;
import com.example.NewNormalAPI.user.UserDetailsServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Component  
@Slf4j
public class CommandLineAppStartupRunner implements CommandLineRunner {
	
    @Autowired
    private UserDetailsServiceImpl userSvc;
    
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public void run(String...args) throws Exception {
		User admin = new User();
		admin.setAuthorities("admin");
		admin.setUsername("admin");
		admin.setPassword(encoder.encode("adminpassword"));
		admin.setVerified("Y");
		try {
			userSvc.createUser(admin);
			log.info("admin account created");
		} catch(UserAlreadyExistsException e) {
			log.info("admin account already exists");
		}
    }
}
