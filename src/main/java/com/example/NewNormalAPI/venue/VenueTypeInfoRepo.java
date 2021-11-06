package com.example.NewNormalAPI.venue;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueTypeInfoRepo extends JpaRepository<VenueTypeInfo, String> {
    List<VenueTypeInfo> findAll();
}