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
    public VenueTypeInfoService(VenueTypeInfoRepo venueTypeInfoRepo) {
        this.venueTypeInfoRepo = venueTypeInfoRepo;
    }

    public VenueTypeInfo save(VenueTypeInfo venueTypeInfo) {
        return venueTypeInfoRepo.save(venueTypeInfo);
    }

    public VenueTypeInfo update(VenueTypeInfo venueTypeInfo) {
        return venueTypeInfoRepo.save(venueTypeInfo);
    }

    // sets the capacity of the room after the max capacity multiplier set by admin
    public List<VenueTypeInfo> getCurrentCapacity() {
        List<VenueTypeInfo> actualInfo = venueTypeInfoRepo.findAll();
        List<VenueTypeInfo> infoAfterRestriction = List.copyOf(actualInfo);

        double maxCapacity = Double.parseDouble(adminConfigSvc.getMaxCapacity());

        int counter = 0;
        for (VenueTypeInfo venueTypeInfo : infoAfterRestriction) {
            int newCapacity = (int) (venueTypeInfo.getCapacity() * maxCapacity);
            venueTypeInfo.setCapacity(newCapacity);
            infoAfterRestriction.set(counter++, venueTypeInfo);
        }

        return infoAfterRestriction;
    }
}