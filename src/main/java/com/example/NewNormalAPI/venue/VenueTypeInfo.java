package com.example.NewNormalAPI.venue;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class VenueTypeInfo {
    @Id
    @GeneratedValue
    private String venue_name;
    
    private int capacity;
}
