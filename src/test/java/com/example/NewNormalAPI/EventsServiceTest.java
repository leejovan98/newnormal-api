package com.example.NewNormalAPI;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

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
    void locationAlreadyInUse_NotInUse_ReturnFalse() {
        // arrange ***
        Event e = new Event();
        Date date = new Date();
        String location = "SCIS B1-1";
        e.setStartDatetime(date);
        e.setLocation(location);

        List<Event> emptyList = new ArrayList<>();
        // Stubbing
        when(events.findByLocationAndStartDatetime(any(String.class), any(Date.class))).thenReturn(emptyList);

        // act ***
        Boolean testResult = eventsService.locationAlreadyInUse(e);

        // assert ***
        verify(events).findByLocationAndStartDatetime(location, date);
        assertFalse(testResult);
    }

    @Test
    void locationAlreadyInUse_InUse_ReturnTrue() {
        // arrange ***
        Date date = new Date();
        String location = "SCIS B1-1";

        Event e1 = new Event();
        e1.setStartDatetime(date);
        e1.setLocation(location);

        Event e2 = new Event();
        e2.setStartDatetime(date);
        e2.setLocation(location);

        List<Event> myList = new ArrayList<>();
        myList.add(e1);
        // Stubbing
        when(events.findByLocationAndStartDatetime(any(String.class), any(Date.class))).thenReturn(myList);

        // act ***
        Boolean testResult = eventsService.locationAlreadyInUse(e2);

        // assert ***
        verify(events).findByLocationAndStartDatetime(location, date);
        assertTrue(testResult);
    }

    @Test
    void getEventByInviteCode_NotFound_ThrowHTTP404() {
        // arrange
        Optional<Event> emptyOptional = Optional.empty();
        String inviteCode = "12345";

        // stubbing
        when(events.findByInviteCode(any(String.class))).thenReturn(emptyOptional);

        // act
        ResponseStatusException thrown = assertThrows(ResponseStatusException.class,
                () -> eventsService.getEventByInviteCode(inviteCode));
        // assert
        verify(events).findByInviteCode(inviteCode);
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());
    }

    @Test
    void getEventByInviteCode_Found_ReturnEvent() {
        // arrange
        Event event = new Event();
        event.setTitle("The test works");
        Optional<Event> optional = Optional.of(event);
        String inviteCode = "12345";

        // stubbing
        when(events.findByInviteCode(any(String.class))).thenReturn(optional);

        // act
        Event testResult = eventsService.getEventByInviteCode(inviteCode);
        // assert
        verify(events).findByInviteCode(inviteCode);
        assertEquals("The test works", testResult.getTitle());
    }
}