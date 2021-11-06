package com.example.NewNormalAPI.venue;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class VenueTypeInfo {
    @Id
    private String venueType;

    @OneToMany(mappedBy = "venueTypeInfo", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Venue> venues;

    private int capacity;
}
