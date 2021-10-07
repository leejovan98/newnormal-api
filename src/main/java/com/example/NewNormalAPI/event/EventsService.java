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

    public boolean locationAlreadyInUse(Event event) {
        List<Event> myList = eRepo.findByLocationAndDatetime(event.getLocation(), event.getDatetime());
        return !myList.isEmpty();
    }

    public Event save(Event event) throws LocationAlreadyInUseException {
    	if(locationAlreadyInUse(event)) {
    		throw new LocationAlreadyInUseException("Location has already been booked for this timeslot.");
    	}
        return eRepo.save(event);
    }
    
    public Event update(Event event) {
        return eRepo.save(event);
    }
    
    public Event getEventByInviteCode(String inviteCode) {
    	Optional<Event> opt = eRepo.findByInviteCode(inviteCode);
    	if(opt.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid invitation code");
    	return opt.get();
    }
}
