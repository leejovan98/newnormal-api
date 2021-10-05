package com.example.EventsService.service;

import com.example.EventsService.repository.EventRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventsService {

    private EventRepository eRepo;
    
    @Autowired
    public EventsService(EventRepository eRepo) {
        this.eRepo = eRepo;
    }

    public boolean LocationAlreadyInUse(String location, String datetime) {
        List<Event> myList = eRepo.findByLocationAndDatetime(location, datetime);
        
        return !myList.isEmpty();
    }

    public Event save(Event event) {
        return eRepo.save(event);
    }
}
