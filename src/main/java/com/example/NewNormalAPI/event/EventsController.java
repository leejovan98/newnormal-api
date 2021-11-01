package com.example.NewNormalAPI.event;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.NewNormalAPI.jwt.util.JwtUtil;
import com.example.NewNormalAPI.mailer.Mail;
import com.example.NewNormalAPI.mailer.MailerSvc;
import com.example.NewNormalAPI.user.User;
import com.example.NewNormalAPI.user.UserDetailsServiceImpl;

@RestController
public class EventsController {
    private EventsService eventsSvc;
    private UserDetailsServiceImpl userDetailsSvc;
    private MailerSvc mailer;
    private JwtUtil jwtUtil;

    // Constructor
    @Autowired
    public EventsController(EventsService eventsSvc, MailerSvc mailer, UserDetailsServiceImpl userDetailsSvc,
            JwtUtil jwtUtil) {
        this.eventsSvc = eventsSvc;
        this.mailer = mailer;
        this.userDetailsSvc = userDetailsSvc;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Allows Faculty to create event Sends a confirmation email if event
     * successfully created
     * 
     * @param user, event
     * @throws LocationAlreadyInUseException, UserNotAuthorisedException
     * @return event
     * @throws MessagingException
     */
    @PostMapping("/events")
    @ResponseStatus(HttpStatus.CREATED)
    // TODO: update cookie value when JWT portion is completed
    public Event createEvent(HttpServletRequest rqst, @Valid @RequestBody Event event) 
            throws UserNotAuthorisedException, LocationAlreadyInUseException, MessagingException {
    	
    	String jwt = jwtUtil.extractJWTString(rqst);
    	User user = userDetailsSvc.loadUserEntityByUsername(jwtUtil.extractUsername(jwt));         
    	event.setOrganizer(user);
        event = eventsSvc.save(event);
        String inviteCode = generateInvitationCode(event);
        event.setInviteCode(inviteCode);
        user.getEvents().add(event);
        userDetailsSvc.update(user);

        Mail mail = new Mail();
        mail.setTo(user.getEmail());
        mail.setSubject("Event Creation");
        Map<String, Object> props = new HashMap<>();
		props.put("event", event);
		mail.setProperties(props);
		mailer.sendEventCreation(mail);
        
        return event;
    }

    /**
     * returns event details based on the invite code passed
     */
    @GetMapping("/events/{inviteCode}")
    public Event getEventDetails(@PathVariable String inviteCode) {
        return eventsSvc.getEventByInviteCode(inviteCode);
    }

    /**
     * Allows Student member to subscribe to event Sends a confirmation email if
     * event successfully subscribed to
     * 
     * @param user, event
     * @throws EventFullySubscribedException
     * @return event
     * @throws MessagingException
     */
    @PostMapping("/events/{inviteCode}/subscribe")
    @ResponseStatus(HttpStatus.OK)
    public void subscribeEvent(HttpServletRequest rqst, @PathVariable String inviteCode) throws MessagingException {

    	String jwt = jwtUtil.extractJWTString(rqst);
    	User user = userDetailsSvc.loadUserEntityByUsername(jwtUtil.extractUsername(jwt)); 
		Event event = eventsSvc.getEventByInviteCode(inviteCode);
		if(event.getNumSubscribers() >= event.getMaxSubscribers()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Event has been fully subscribed");
		}
		
		if(event.getSubscribers().contains(user)) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Already subscribed");
		}

		event.getSubscribers().add(user);
		event.setNumSubscribers(event.getNumSubscribers() + 1); 

		user.getEvents().add(event);
		userDetailsSvc.update(user);
		Mail mail = new Mail();
		mail.setTo(user.getEmail());
		mail.setSubject("Subscription Confirmed");
		Map<String, Object> props = new HashMap<>();
		props.put("event", event);
		mail.setProperties(props);
		
		mailer.sendSubscriptionConfirmation(mail);
    }

    /**
     * Creates a Verification object containing the verification code
     *
     * @param user
     * @return v (verification code)
     */
    public String generateInvitationCode(Event event) {
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyHHmmss");
        Date currDt = new Date();
        String code = event.getId() + format.format(currDt);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(code.getBytes(StandardCharsets.UTF_8));

    }

    /**
     * returns up to 10 upcoming public events
     */
    @GetMapping("/events/featured")
    public List<Event> getFeatured() {
        List<Event> events = eventsSvc.getFeaturedPublicEvents();
        return events;
    }
}