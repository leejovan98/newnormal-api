package com.example.NewNormalAPI.venue;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VenueService {
    private VenueRepo venueRepo;

    @Autowired
    public VenueService(VenueRepo venueRepo) {
        this.venueRepo = venueRepo;
    }

    /**
     * Saves venue
     * 
     * @param venue
     * @return saved venue
     */
    public Venue save(Venue venue) {
        return venueRepo.save(venue);
    }

    /**
     * Updates venue 
     * 
     * @param venue
     * @return updated venue
     */
    public Venue update(Venue venue) {
        return venueRepo.save(venue);
    }

    /**
     * Gets all venues
     * 
     * @return list of all the venues
     */
    public List<Venue> getAllVenues() {
        return venueRepo.findAll();
    }
}