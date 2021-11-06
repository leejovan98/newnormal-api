package com.example.NewNormalAPI.venue;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VenueController {

    private VenueService venueSvc;

    @Autowired
    public VenueController(VenueService venueSvc) {
        this.venueSvc = venueSvc;
    }

    // Return all venues
    @GetMapping("/venues")
    @ResponseStatus(HttpStatus.OK)
    public List<Venue> getAllVenues() {
        return venueSvc.getAllVenues();
    }
}