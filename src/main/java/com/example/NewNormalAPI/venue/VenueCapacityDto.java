package com.example.NewNormalAPI.venue;

import java.util.List;

import lombok.Data;

@Data
public class VenueCapacityDto {
    private List<Venue> venues;

    private List<VenueTypeInfo>  info;
    
}
