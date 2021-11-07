package com.example.NewNormalAPI.venue;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.example.NewNormalAPI.event.Event;

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

    @OneToMany(mappedBy = "venue", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Event> events;

    @ManyToOne
    @JoinColumn(name = "venue_type", nullable = false)
    private VenueTypeInfo venueTypeInfo;

    private int level;

    private int roomNumber;

    @Override
    public String toString() {
        return "Venue: " + building + "-" + level + "-" + roomNumber;
    }
}
