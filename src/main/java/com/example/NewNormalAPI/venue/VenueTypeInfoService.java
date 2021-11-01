package com.example.NewNormalAPI.venue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VenueTypeInfoService {
    private VenueTypeInfoRepo venueTypeInfoRepo;

    @Autowired
    public VenueTypeInfoService(VenueTypeInfoRepo venueTypeInfoRepo) {
        this.venueTypeInfoRepo = venueTypeInfoRepo;
    }

    public VenueTypeInfo save(VenueTypeInfo venueTypeInfo) {
        return venueTypeInfoRepo.save(venueTypeInfo);
    }

    public VenueTypeInfo update(VenueTypeInfo venueTypeInfo) {
        return venueTypeInfoRepo.save(venueTypeInfo);
    }
}