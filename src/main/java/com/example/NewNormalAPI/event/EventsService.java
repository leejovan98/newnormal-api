package com.example.NewNormalAPI.event;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EventsService {

    private EventRepository eventRepo;
    
    @Autowired
    public EventsService(EventRepository eventRepo) {
        this.eventRepo = eventRepo;
    }

    /**
     * Determines whether location is already in use or not
     * 
     * @param event
     * @return true if 
     */
    public boolean locationAlreadyInUse(Event event) {
        List<Event> myList = eventRepo.findByVenueIdAndStartDatetimeLessThanAndStartDatetimeGreaterThan(event.getVenue().getId(), event.getStopDatetime(), event.getStartDatetime());
        myList.addAll(eventRepo.findByVenueIdAndStopDatetimeGreaterThanAndStopDatetimeLessThan(event.getVenue().getId(), event.getStartDatetime(), event.getStopDatetime()));
        System.out.println(myList);
        return !myList.isEmpty();
    }

    /**
     * Saves an event
     * 
     * @param event
     * @return the saved event
     * @throws LocationAlreadyInUseException
     */
    public Event save(Event event) throws LocationAlreadyInUseException {
    	
    	if(event.getStartDatetime().before(new Date()) || event.getStopDatetime().before(event.getStartDatetime())) 
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Start/End Times");
    	
    	if(locationAlreadyInUse(event)) {
    		throw new LocationAlreadyInUseException("Location has already been booked for this timeslot.");
    	}
    	
        return eventRepo.save(event);
    }
    
    /**
     * Updates the event
     * 
     * @param event
     * @return the updated event
     */
    public Event update(Event event) {
        return eventRepo.save(event);
    }
    
    /**
     * Allows uses to get the event by using invite code
     * 
     * @param inviteCode
     * @return event under respective invite code
     */
    public Event getEventByInviteCode(String inviteCode) {
    	Optional<Event> opt = eventRepo.findByInviteCode(inviteCode);
    	if(opt.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid invitation code");
    	return opt.get();
    }
    
    /**
     * Returns list of featured public events
     * 
     * @return list of featured public events
     */
    public List<Event> getFeaturedPublicEvents(){
    	Date currentDate = new Date();
    	Timestamp currentTs = new Timestamp(currentDate.getTime());
    	return eventRepo.findTop10ByVisibilityAndStartDatetimeGreaterThanOrderByStartDatetimeAsc("public", currentTs);
    }

    /**
     * Returns list of all events
     * 
     * @return list of all events
     */
    public List<Event> getAllEvents() {
        return eventRepo.findAll();
    }
}
