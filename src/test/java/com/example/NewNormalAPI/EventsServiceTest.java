package com.example.NewNormalAPI;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.NewNormalAPI.event.Event;
import com.example.NewNormalAPI.event.EventRepository;
import com.example.NewNormalAPI.event.EventsService;

@ExtendWith(MockitoExtension.class)
public class EventsServiceTest {
    
    @Mock
    private EventRepository events;

    @InjectMocks
    private EventsService eventsService;
    
    @AfterEach
    void tearDown() {
        // clear the database after each test
        events.deleteAll();
    }
    
    @Test
    void LocationAlreadyInUse_NotInUse_ReturnFalse(){
        // arrange ***
    	Event e = new Event();
    	Date date = new Date();
    	String location = "SCIS B1-1";
        e.setDatetime(date);
        e.setLocation(location);
       
        List<Event> emptyList = new ArrayList<>();
        // Stubbing
        when(events.findByLocationAndDatetime(any(String.class), any(Date.class))).thenReturn(emptyList);

        // act ***
        Boolean testResult = eventsService.locationAlreadyInUse(e);
        
        // assert ***
        verify(events).findByLocationAndDatetime(location, date);
        assertFalse(testResult);
    }

    @Test
    void LocationAlreadyInUse_InUse_ReturnTrue(){
        // arrange ***
    	Event e1 = new Event();
    	Date date = new Date();
    	String location = "SCIS B1-1";
        e1.setDatetime(date);
        e1.setLocation(location);

        Event e2 = new Event();
        e2.setDatetime(date);
        e2.setLocation(location);
       
        List<Event> myList = new ArrayList<>();
        myList.add(e1);
        // Stubbing
        when(events.findByLocationAndDatetime(any(String.class), any(Date.class))).thenReturn(myList);

        // act ***
        Boolean testResult = eventsService.locationAlreadyInUse(e2);
        
        // assert ***
        verify(events).findByLocationAndDatetime(location, date);
        assertTrue(testResult);
    }
}