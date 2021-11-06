package com.example.NewNormalAPI.adminconfig;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminConfigService {
    private AdminConfigRepo adminConfigRepo;

    @Autowired
    public AdminConfigService(AdminConfigRepo adminConfigRepo) {
        this.adminConfigRepo = adminConfigRepo;
    }

    public AdminConfig save(AdminConfig adminConfig) {
        return adminConfigRepo.save(adminConfig);
    }

    public AdminConfig update(AdminConfig adminConfig) throws PropertyDoesNotExistException {
        AdminConfig otherAdminConfig = adminConfigRepo.findByProperty(adminConfig.getProperty());
        if (otherAdminConfig == null) {
            throw new PropertyDoesNotExistException();
        } else {
            return adminConfigRepo.save(adminConfig);
        }
    }

    public AdminConfig delete(AdminConfig adminConfig) {
        AdminConfig deletedAdminConfiq = adminConfig;
        adminConfigRepo.delete(adminConfig);
        return deletedAdminConfiq;
    }

    public List<AdminConfig> getAllAdminConfig() {
        return adminConfigRepo.findAll();
    }

}