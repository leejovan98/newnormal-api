package com.example.NewNormalAPI.event;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
	// define a derived query to find event by username
	// Optional<Event> findByOrganizerId(String username);
//	List<Event> findByVenueIdAndStartDatetime(Long venueId, Date startDatetime);
	
	
	// refined the method to take into account start and end times
	List<Event> findByVenueIdAndStartDatetimeLessThanAndStartDatetimeGreaterThan(Long venueId, Date endTime, Date startTime);
	List<Event> findByVenueIdAndStopDatetimeGreaterThanAndStopDatetimeLessThan(Long venueId, Date startTime, Date endTime);

	Optional<Event> findByInviteCode(String inviteCode);
	
	List<Event> findTop10ByVisibilityAndStartDatetimeGreaterThanOrderByStartDatetimeAsc(String visibility, Timestamp startDatetime);

	List<Event> findAll();
}