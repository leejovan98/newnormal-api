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

    public Venue save(Venue venue) {
        return venueRepo.save(venue);
    }

    public Venue update(Venue venue) {
        return venueRepo.save(venue);
    }
    public List<Venue> getAllVenues() {
        return venueRepo.findAll();
    }
}