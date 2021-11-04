package com.example.NewNormalAPI.adminconfig;

import java.util.List;

import com.example.NewNormalAPI.event.Event;
import com.example.NewNormalAPI.event.EventsService;
import com.example.NewNormalAPI.user.User;
import com.example.NewNormalAPI.user.UserDetailsServiceImpl;
import com.example.NewNormalAPI.venue.VenueRepo;
import com.example.NewNormalAPI.venue.VenueService;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

    // Update capacity
    @PostMapping("/admin/configuration")
    @ResponseStatus(HttpStatus.OK)
    public void updateAdminConfig(AdminConfig adminConfig) throws PropertyDoesNotExistException {
        try {
            adminConfigSvc.update(adminConfig);
        } catch (PropertyDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }
}