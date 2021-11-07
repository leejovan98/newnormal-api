package com.example.NewNormalAPI.event;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EventsService {

    private EventRepository eRepo;
    
    @Autowired
    public EventsService(EventRepository eRepo) {
        this.eRepo = eRepo;
    }

    /**
     * Determines whether location is already in use or not
     * 
     * @param event
     * @return true if 
     */
    public boolean locationAlreadyInUse(Event event) {
        List<Event> myList = eRepo.findByVenueIdAndStartDatetime(event.getVenue().getId(), event.getStartDatetime());
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
    	if(locationAlreadyInUse(event)) {
    		throw new LocationAlreadyInUseException("Location has already been booked for this timeslot.");
    	}
        return eRepo.save(event);
    }
    
    /**
     * Updates the event
     * 
     * @param event
     * @return the updated event
     */
    public Event update(Event event) {
        return eRepo.save(event);
    }
    
    /**
     * Allows uses to get the event by using invite code
     * 
     * @param inviteCode
     * @return event under respective invite code
     */
    public Event getEventByInviteCode(String inviteCode) {
    	Optional<Event> opt = eRepo.findByInviteCode(inviteCode);
    	if(opt.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid invitation code");
    	return opt.get();
    }
    
    /**
     * Returns list of featured public events
     * 
     * @return list of featured public events
     */
    public List<Event> getFeaturedPublicEvents(){
    	return eRepo.findTop10ByVisibilityOrderByStartDatetimeAsc("public");
    }

    /**
     * Returns list of all events
     * 
     * @return list of all events
     */
    public List<Event> getAllEvents() {
        return eRepo.findAll();
    }
}
