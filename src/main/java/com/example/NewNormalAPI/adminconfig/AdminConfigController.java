package com.example.NewNormalAPI.adminconfig;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.NewNormalAPI.event.EventsService;
import com.example.NewNormalAPI.user.User;
import com.example.NewNormalAPI.user.UserDetailsServiceImpl;

@RestController
public class AdminConfigController {

    private AdminConfigService adminConfigSvc;
    private UserDetailsServiceImpl userSvc;
    private EventsService eventSvc;

    // Constructor
    @Autowired
    public AdminConfigController(AdminConfigService adminConfigSvc, UserDetailsServiceImpl userSvc,
            EventsService eventSvc) {
        this.adminConfigSvc = adminConfigSvc;
        this.userSvc = userSvc;
        this.eventSvc = eventSvc;
    }

    // Promote user from student to faculty
    @PutMapping("/admin/configuration")
    @ResponseStatus(HttpStatus.OK)
    public void promoteStudent(User user) {
        user.setAuthorities("faculty");
        userSvc.update(user);
    }
    
    @GetMapping("/admin/configuration")
    @ResponseStatus(HttpStatus.OK)
    public List<AdminConfig> getConfigs() {
    	return adminConfigSvc.getAllAdminConfig();
    }

    // Update capacity
    @PostMapping("/admin/configuration")
    @ResponseStatus(HttpStatus.OK)
    public void updateAdminConfig(@RequestBody AdminConfig adminConfig) throws PropertyDoesNotExistException {
        try {
            adminConfigSvc.update(adminConfig);
        } catch (PropertyDoesNotExistException e) {
        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Property Value Received");
        }
    }
    
    @GetMapping("/admin/users")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() {
    	return userSvc.loadAllUsers();
    }
    
    @PostMapping("/admin/users")
    @ResponseStatus(HttpStatus.OK)
    public void promoteUser(@RequestBody User user) {
    	User curUser = userSvc.loadUserEntityByUsername(user.getUsername());
    	if(Objects.isNull(curUser)) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Such User");
    	}
    	curUser.setAuthorities("faculty");
    	userSvc.update(curUser);
    	
    }
}