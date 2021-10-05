package com.example.EventsService.repository;

import java.util.Optional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    // define a derived query to find event by username
    Optional<Event> findByUsername(String username);
    List<Event> findByLocationAndDatetime(String location, String datetime);
}