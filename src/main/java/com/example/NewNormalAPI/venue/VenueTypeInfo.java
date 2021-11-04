package com.example.NewNormalAPI.venue;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class VenueTypeInfo {
    @Id
    @GeneratedValue
    @OneToMany(mappedBy = "type", orphanRemoval = true, cascade = CascadeType.ALL)
    private String venue_name;
    
    private int capacity;
}
