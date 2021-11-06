package com.example.NewNormalAPI.adminconfig;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AdminConfigService {
    private AdminConfigRepo adminConfigRepo;

    @Autowired
    public AdminConfigService(AdminConfigRepo adminConfigRepo) {
        this.adminConfigRepo = adminConfigRepo;
    }

    /**
     * Saves admin configurations
     * 
     * @param adminConfig
     * @return saved admin configurations
     */
    public AdminConfig save(AdminConfig adminConfig) {
        return adminConfigRepo.save(adminConfig);
    }

    /**
     * Updates admin configurations
     * 
     * @param adminConfig
     * @throws PropertyDoesNotExistException
     */
    public void update(AdminConfig adminConfig) throws PropertyDoesNotExistException {
        AdminConfig otherAdminConfig = adminConfigRepo.findByProperty(adminConfig.getProperty());
        if (otherAdminConfig == null) {
            throw new PropertyDoesNotExistException();
        } else if(isValid(adminConfig)) {
        	otherAdminConfig.setValue(adminConfig.getValue());
        	adminConfigRepo.save(otherAdminConfig);
        } else {
        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Value is unexpected / out of bounds");
        }
    }
    
    private boolean isValid(AdminConfig adminConfig) {
    	String val = adminConfig.getValue();
    	
		switch(adminConfig.getProperty()) {
			case "allow adjacent booking":
				return(val.equalsIgnoreCase("Y") || val.equalsIgnoreCase("N"));
			case "max capacity":
				Double parsed = Double.parseDouble(val);
				return (parsed > 0.0 && parsed <= 1.0);
			default:
				return false;
		}
	}

    /**
     * Deletes admin configurations
     * 
     * @param adminConfig
     * @return deleted admin configuration
     */
    public AdminConfig delete(AdminConfig adminConfig) {
        AdminConfig deletedAdminConfig = adminConfig;
        adminConfigRepo.delete(adminConfig);
        return deletedAdminConfig;
    }

    /**
     * Gets all configurations for admin
     * 
     * @return list of admin configurations
     */
    public List<AdminConfig> getAllAdminConfig() {
        return adminConfigRepo.findAll();
    }

    public String getMaxCapacity() {
        return adminConfigRepo.findByProperty("max capacity").getValue();
    }

}