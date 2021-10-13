package com.example.NewNormalAPI;

import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

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
}