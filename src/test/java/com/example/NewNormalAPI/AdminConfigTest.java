package com.example.NewNormalAPI;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.NewNormalAPI.adminconfig.AdminConfig;
import com.example.NewNormalAPI.adminconfig.AdminConfigRepo;
import com.example.NewNormalAPI.adminconfig.AdminConfigService;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AdminConfigTest {
    @Mock
    private AdminConfigRepo adminConfigRepo;

    @InjectMocks
    private AdminConfigService adminConfigService;

    @AfterEach
    void tearDown() {
        // clear the database after each test
        adminConfigRepo.deleteAll();
    }

    @Test
    void saveAdminConfig_newAdminConfig_ReturnSavedAdminConfig() {
        AdminConfig adminConfig = new AdminConfig();
        adminConfig.setCapactiy(100);

        // mock the "save" operation
        when(adminConfigRepo.save(any(AdminConfig.class))).thenReturn(adminConfig);

        // verify
        verify(adminConfigRepo).save(adminConfig);
    }
}