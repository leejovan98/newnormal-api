package com.example.NewNormalAPI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.NewNormalAPI.event.EventRepository;
import com.example.NewNormalAPI.event.EventsService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EventsServiceTest {
    
    @Mock
    private EventRepository events;

    @InjectMocks
    private EventsService eventsService;
    
    
    @Test
    void LocationAlreadyInUse_NotInUse_ReturnFalse(){
        // arrange ***
        String location = "SomeLocation";
        String dateTime = "SomeDateTime";
        List<Event> emptyList = new List<>();
        when(events.findByLocationAndDatetime((any(String.class), any(String.class)).thenReturn(emptyList));

        // act ***
        Boolean testResult = eventsService.LocationAlreadyInUse(location, dateTime);
        
        // assert ***
        verify(events).findByLocationAndDatetime(location, dateTime);
        assertFalse(testResult);
    }

}