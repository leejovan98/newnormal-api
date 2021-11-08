package com.example.NewNormalAPI;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.NewNormalAPI.adminconfig.AdminConfig;
import com.example.NewNormalAPI.adminconfig.AdminConfigRepo;
import com.example.NewNormalAPI.adminconfig.AdminConfigService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AdminConfigTest {
    
    @Mock
    private AdminConfigRepo adminConfigs;

    @InjectMocks
    private AdminConfigService adminConfigService;

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
}