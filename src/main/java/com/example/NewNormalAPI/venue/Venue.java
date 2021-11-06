package com.example.NewNormalAPI.venue;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
public class Venue {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY) // used to prevent infinite references from occurring with
                                                        // lombok
    private Long id;

    private String building;

    @ManyToOne
    @JoinColumn(name = "venues", nullable = true)
    private VenueTypeInfo venueTypeInfo;

    private int level;

    private int roomNumbers;
}
