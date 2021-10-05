package com.example.EventsService.controller;

import java.util.Collection;

import com.example.EventsService.exceptions.LocationAlreadyInUseException;
import com.example.EventsService.exceptions.UserNotAuthorisedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.events.Event;
import org.springframework.http.HttpStatus;

@RestController
public class EventsController {
    private EventsService eventsSvc;
    private UserSubscriptionService userSubscriptionSvc;
    private MailerSvcImpl mailer;

    // Constructor
    @Autowired
    public EventsController(EventsService eventsSvc, MailerSvcImpl mailer) {
        this.eventsSvc = eventsSvc;
        this.mailer = mailer;
    }

    /**
     * Allows Faculty to create event Sends a confirmation email if event
     * successfully created
     * 
     * @param user, event
     * @throws LocationAlreadyInUseException, UserNotAuthorisedException
     * @return event
     */
    @PostMapping("/events/create")
    @ResponseStatus(HttpStatus.OK)
    public Event createEvent(@RequestBody User user, Event event)
            throws UserNotAuthorisedException, LocationAlreadyInUseException {

        Collection<GrantedAuthority> userAuthorities = user.getAuthorities();

        if (!(userAuthorities.equals("faculty"))) {
            throw new UserNotAuthorisedException("You are not authorised to create this event.");
        } else if (eventsSvc.LocationAlreadyInUse(event.getLocation(), event.getDatetime())) {
            throw new LocationAlreadyInUseException("Location has already been booked for this timeslot.");
        } else {
            eventsSvc.save(event);
        }

        mailer.sendConfirmationEmail(user.getEmail(), event.toString());
        return event;
    }

    /**
     * Allows Student member to subscribe to event Sends a confirmation email if
     * event successfully subscribed to
     * 
     * @param user, event
     * @throws EventFullySubscribedException
     * @return event
     */
    @PostMapping("/events/subscribe")
    @ResponseStatus(HttpStatus.OK)
    public void subscribeEvent(@RequestBody User user, Event event) throws EventFullySubscribedException {

        Collection<GrantedAuthority> userAuthorities = user.getAuthorities();

        if (event.getNumSubscribers() + 1 >= event.getLocation().getCapacity()) {
            throw new EventFullySubscribedException("Event has been fully subscribed.");
        } else {
            userSubscriptionSvc.save(event);
        }

        mailer.sendCofnrimationEmail(user.getEmail(), event.toString());
    }
}