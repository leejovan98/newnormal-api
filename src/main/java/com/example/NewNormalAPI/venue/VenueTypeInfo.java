package com.example.NewNormalAPI.venue;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class VenueTypeInfo {

    @Id
    @OneToMany(mappedBy = "venueType", orphanRemoval = true, cascade = CascadeType.ALL)
    private String venueType;

    private int capacity;
}
