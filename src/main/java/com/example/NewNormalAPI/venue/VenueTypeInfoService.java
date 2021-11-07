package com.example.NewNormalAPI.venue;

import java.util.List;

import com.example.NewNormalAPI.adminconfig.AdminConfig;
import com.example.NewNormalAPI.adminconfig.AdminConfigService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VenueTypeInfoService {
    private VenueTypeInfoRepo venueTypeInfoRepo;
    private AdminConfigService adminConfigSvc;

    @Autowired
    public VenueTypeInfoService(VenueTypeInfoRepo venueTypeInfoRepo, AdminConfigService adminConfigSvc) {
        this.venueTypeInfoRepo = venueTypeInfoRepo;
        this.adminConfigSvc = adminConfigSvc;
    }

    /**
     * Saves information of venue type
     * 
     * @param venueTypeInfo
     * @return saved venue type information
     */
    public VenueTypeInfo save(VenueTypeInfo venueTypeInfo) {
        return venueTypeInfoRepo.save(venueTypeInfo);
    }

    /**
     * Updates information of venue type
     * 
     * @param venueTypeInfo
     * @return updated venue type information
     */
    public VenueTypeInfo update(VenueTypeInfo venueTypeInfo) {
        return venueTypeInfoRepo.save(venueTypeInfo);
    }

    /**
     * Sets the capacity of the room after the max capacity multiplier set by admin

     * @return list of current capacity for various venue types
     */
    public List<VenueTypeInfo> getCurrentCapacity() {
        List<VenueTypeInfo> actualInfo = venueTypeInfoRepo.findAll();
        List<VenueTypeInfo> infoAfterRestriction = List.copyOf(actualInfo);

        double maxCapacity = Double.parseDouble(adminConfigSvc.getMaxCapacity());

        
        infoAfterRestriction.forEach(info ->{
        	info.setCapacity((int)(info.getCapacity() * maxCapacity));
        });

        return infoAfterRestriction;
    }
}