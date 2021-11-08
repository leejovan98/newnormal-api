package com.example.NewNormalAPI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.NewNormalAPI.adminconfig.AdminConfig;
import com.example.NewNormalAPI.adminconfig.AdminConfigRepo;
import com.example.NewNormalAPI.adminconfig.AdminConfigService;
import com.example.NewNormalAPI.event.Event;
import com.example.NewNormalAPI.event.EventRepository;
import com.example.NewNormalAPI.event.EventsService;
import com.example.NewNormalAPI.venue.Venue;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
public class AdminConfigTest {
    
    @Mock
    private AdminConfigRepo adminConfigs;

    @InjectMocks
    private AdminConfigService adminConfigService;

    @Mock
    private EventRepository events;

    @InjectMocks
    private EventsService eventsService;

    @AfterEach
    void tearDown() {
        // clear the database after each test
        adminConfigs.deleteAll();
    }

    @Test
    void updateAdminConfig_newValue_ReturnSavedAdminConfig() {
        // arrange
        AdminConfig adminConfig = new AdminConfig();
        adminConfig.setProperty("CAPACITY");
        adminConfig.setValue("100");

        // mock the "save" operation - stubbing
        when(adminConfigs.save(any(AdminConfig.class))).thenReturn(adminConfig);

        // act
        AdminConfig testResult = adminConfigService.save(adminConfig);

        // assert
        verify(adminConfigs).save(adminConfig);
        assertEquals(adminConfig, testResult);
    }

    @Test
    void saveEvent_AllowAdjacentBookingsFalse_ThrowAdjacentBookingException() {
        // arrange
        AdminConfig adminConfig = new AdminConfig();
        adminConfig.setProperty("ALLOW_ADJACENT_BOOKINGS");
        adminConfig.setValue("N");

        Venue newVenue = new Venue();
        newVenue.setBuilding("SCIS");
        newVenue.setType("SR");
        newVenue.setLevel(2);
        newVenue.setRoomNumber(2);

        Event newEvent = new Event();
        newEvent.setVenue(newVenue);

        // stubbing
        when(events.save(any(Event.class))).thenReturn(newEvent);

        // act
        ResponseStatusException thrown = assertThrows(ResponseStatusException.class,
                () -> eventsService.save(newEvent));

        // assert
        verify(events).save(newEvent);
        assertEquals(HttpStatus.FORBIDDEN, thrown.getStatus());
    }
}