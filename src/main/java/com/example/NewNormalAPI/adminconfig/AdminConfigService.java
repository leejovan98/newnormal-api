package com.example.NewNormalAPI.adminconfig;

import org.springframework.beans.factory.annotation.Autowired;

public class AdminConfigService {
    private AdminConfigRepo adminConfigRepo;

    @Autowired
    public AdminConfigService(AdminConfigRepo adminConfigRepo) {
        this.adminConfigRepo = adminConfigRepo;
    }

    public AdminConfig save(AdminConfig adminConfig) {
        return adminConfigRepo.save(adminConfig);
    }

    public AdminConfig update(AdminConfig adminConfig) {
        return adminConfigRepo.save(adminConfig);
    }
}