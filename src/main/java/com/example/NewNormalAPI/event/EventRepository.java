package com.example.NewNormalAPI.event;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
	// define a derived query to find event by username
	// Optional<Event> findByOrganizerId(String username);
	List<Event> findByLocationAndDatetime(String location, Date datetime);

	Optional<Event> findByInviteCode(String inviteCode);
	
	List<Event> findTop10ByVisibilityOrderByDatetimeAsc(String visibility);

	List<Event> findAll();
}