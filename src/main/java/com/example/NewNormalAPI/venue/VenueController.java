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
    private VenueTypeInfoService infoSvc;

    @Autowired
    public VenueController(VenueService venueSvc, VenueTypeInfoService infoSvc) {
        this.venueSvc = venueSvc;
        this.infoSvc = infoSvc;
    }

    // Return all venues
    @GetMapping("/events/venues")
    @ResponseStatus(HttpStatus.OK)
    public VenueCapacityDto getAllVenuesAndCapacity() {

        VenueCapacityDto dto = new VenueCapacityDto();
        dto.setVenues(venueSvc.getAllVenues());
        dto.setInfo(infoSvc.getCurrentCapacity())
        return dto;
    }
}