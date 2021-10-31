package com.example.NewNormalAPI.adminconfig;

import java.util.List;

import com.example.NewNormalAPI.event.Event;
import com.example.NewNormalAPI.event.EventsService;
import com.example.NewNormalAPI.user.User;
import com.example.NewNormalAPI.user.UserDetailsServiceImpl;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
    @PutMapping("/accounts/admin")
    @ResponseStatus(HttpStatus.OK)
    public void promoteStudent(User user) {
        user.setAuthorities("faculty");
        userSvc.update(user);
    }

    // Update capacity
    @PostMapping("/accounts/admin")
    @ResponseStatus(HttpStatus.OK)
    public void updateCapacity(AdminConfig adminConfig) {
        int capacity = Integer.parseInt(adminConfig.getValue());
        List<Event> allEvents = eventSvc.getAllEvents();
        for (Event event : allEvents) {
            event.setMaxSubscribers(capacity);
            eventSvc.update(event);
        }
    }

        // Allow adajacent bookings
        @PutMapping("/accounts/admin")
        @ResponseStatus(HttpStatus.OK)
        public void allowAdajacentBookings(AdminConfig adminConfig) {
            Boolean isAllow = Boolean.parseBoolean(adminConfig.getValue());
            if (!(isAllow)) {
                List<String> allLocations = Event.getAllLocations();
                for (String location : allLocations) {
                    if (location.getRoomNo() % 2 == 0) {
                        location.setAvailableForBooking() = false;
                    }
                }
            }
        }
}