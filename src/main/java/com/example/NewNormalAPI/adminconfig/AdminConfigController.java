package com.example.NewNormalAPI.adminconfig;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    
    /** Gets all configurations for admin
     * 
     * @return list of admin configurations
     */
    @GetMapping("/admin/configuration")
    @ResponseStatus(HttpStatus.OK)
    public List<AdminConfig> getConfigs() {
    	return adminConfigSvc.getAllAdminConfig();
    }

    /**
     * Update admin configuration
     * 
     * @param adminConfig
     */
    @PostMapping("/admin/configuration")
    @ResponseStatus(HttpStatus.OK)
    public void updateAdminConfig(@RequestBody AdminConfig adminConfig) throws PropertyDoesNotExistException {
        try {
            adminConfigSvc.update(adminConfig);
        } catch (PropertyDoesNotExistException e) {
        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Property Value Received");
        }
    }
    
    /**
     * Returns a list of all the users
     * 
     * @return list of all users
     */
    @GetMapping("/admin/users")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() {
    	return userSvc.loadAllUsers();
    }
    
    /**
     * Promotes user to faculty 
     * 
     * @param user
     */
    @PostMapping("/admin/users")
    @ResponseStatus(HttpStatus.OK)
    public void promoteUser(@RequestBody User user) {
    	User curUser = userSvc.loadUserEntityByUsername(user.getUsername());
    	if(Objects.isNull(curUser)) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Such User");
    	} else if(curUser.getAuthorities().contains(new SimpleGrantedAuthority("admin")) || 
    			curUser.getAuthorities().contains(new SimpleGrantedAuthority("faculty"))) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "unable to promote user to 'faculty'");
    	}
    	curUser.setAuthorities("faculty");
    	userSvc.update(curUser);
    }
}