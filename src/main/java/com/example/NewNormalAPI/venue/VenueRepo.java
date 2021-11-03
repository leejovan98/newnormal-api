package com.example.NewNormalAPI.venue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueRepo extends JpaRepository<Venue, Long> {
    // List<Venue> findByBuildingAndLevelAndRoomNumber(String building, int level, int roomNumbers)
}