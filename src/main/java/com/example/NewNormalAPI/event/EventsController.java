package com.example.NewNormalAPI.event;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

import com.example.NewNormalAPI.adminconfig.AdjacentBookingException;
import com.example.NewNormalAPI.adminconfig.AdminConfig;
import com.example.NewNormalAPI.adminconfig.AdminConfigService;
import com.example.NewNormalAPI.jwt.util.JwtUtil;
import com.example.NewNormalAPI.mailer.Mail;
import com.example.NewNormalAPI.mailer.MailerSvc;
import com.example.NewNormalAPI.user.User;
import com.example.NewNormalAPI.user.UserDetailsServiceImpl;
import com.example.NewNormalAPI.venue.Venue;
import com.example.NewNormalAPI.venue.VenueService;

@RestController
public class EventsController {
    private EventsService eventsSvc;
    private VenueService venueSvc;
    private UserDetailsServiceImpl userDetailsSvc;
    private MailerSvc mailer;
    private JwtUtil jwtUtil;
    private AdminConfigService adminConfigSvc;

    // Constructor
    @Autowired
    public EventsController(EventsService eventsSvc, MailerSvc mailer, UserDetailsServiceImpl userDetailsSvc,
            JwtUtil jwtUtil, AdminConfigService adminConfigSvc, VenueService venueSvc) {
        this.eventsSvc = eventsSvc;
        this.mailer = mailer;
        this.userDetailsSvc = userDetailsSvc;
        this.jwtUtil = jwtUtil;
        this.adminConfigSvc = adminConfigSvc;
        this.venueSvc = venueSvc;
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
    public Event createEvent(HttpServletRequest rqst, @Valid @RequestBody Event event)
            throws UserNotAuthorisedException, LocationAlreadyInUseException, MessagingException,
            AdjacentBookingException {

    	Venue v = venueSvc.findByBuildingAndLevelAndRoomNumber(event.getVenue().getBuilding(), 
    			event.getVenue().getLevel(), event.getVenue().getRoomNumber());
    	event.setVenue(v);
    	
    	if(Objects.isNull(v)) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid venue");
    	}
    	
        if (!(isAllowAdjacentBooking()) && event.getVenue().getRoomNumber() % 2 == 0) {
            throw new AdjacentBookingException();
        }

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
     * Returns event details based on the invite code passed
     * 
     * @param inviteCode
     * @return event object and its details
     */
    @GetMapping("/events/{inviteCode}")
    public Event getEventDetails(@PathVariable String inviteCode) {
        return eventsSvc.getEventByInviteCode(inviteCode);
    }

    /**
     * Allows Student member to subscribe to event Sends a confirmation email if event successfully subscribed to
     * 
     * @param user
     * @param event
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
        if (event.getNumSubscribers() >= event.getMaxSubscribers()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Event has been fully subscribed");
        }

        if (event.getSubscribers().contains(user)) {
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
     * Returns up to 10 upcoming public events
     * 
     * @return up to 10 upcoming public events
     */
    @GetMapping("/events/featured")
    public List<Event> getFeatured() {
        List<Event> events = eventsSvc.getFeaturedPublicEvents();
        return events;
    }

    /**
     * Allows for whether users can book rooms that are next to each other or not
     * 
     * @return true or false depending on the admin configuration value
     */
    public boolean isAllowAdjacentBooking() {
        List<AdminConfig> allAdminConfig = adminConfigSvc.getAllAdminConfig();
        for (AdminConfig adminConfig : allAdminConfig) {
            if (adminConfig.getProperty().equalsIgnoreCase("allow adjacent booking") && adminConfig.getValue().equals("N")) {
                return false;
            }
        }
        return true;
    }
}