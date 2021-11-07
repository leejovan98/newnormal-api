package com.example.NewNormalAPI.venue;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueRepo extends JpaRepository<Venue, Long> {
    List<Venue> findAll();
    
    Venue findByBuildingAndLevelAndRoomNumber(String building, int level, int roomNumber);
}