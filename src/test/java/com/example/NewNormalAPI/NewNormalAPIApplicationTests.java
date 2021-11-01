package com.example.NewNormalAPI;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.NewNormalAPI.adminconfig.AdminConfigRepo;
import com.example.NewNormalAPI.event.EventRepository;
import com.example.NewNormalAPI.user.UserRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NewNormalAPIApplicationTests {
	
	@Autowired
	private EventRepository events;

	@Autowired
	private UserRepository users;

	@Autowired
	private AdminConfigRepo adminConfigs;

	@AfterEach
	void tearDown() {
		// clear the database after each test
		events.deleteAll();
		users.deleteAll();
		adminConfigs.deleteAll();
	}
	
	@Test
	void contextLoads() {
		assertEquals(123, 123);
	}

}
