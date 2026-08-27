package com.example.parkingLotManagement.service;

import com.example.parkingLotManagement.dtos.PublicAvailabilityResponse;
import com.example.parkingLotManagement.entities.ParkingSpace;
import com.example.parkingLotManagement.repositories.ParkingSpaceRepository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

public class ParkingService {

    private final ParkingSpaceRepository parkingSpaceRepository;

    public ParkingService(ParkingSpaceRepository parkingSpaceRepository){
        this.parkingSpaceRepository = parkingSpaceRepository;
    }

    public List<PublicAvailabilityResponse> getAvailability(){
        List<ParkingSpace> spaces = parkingSpaceRepository.findAll();
        List<PublicAvailabilityResponse> availability = new ArrayList<>();
        for(ParkingSpace s : spaces){
            PublicAvailabilityResponse response = new PublicAvailabilityResponse(s.getLevel(),s.getTwa()>0,s.getFwa()>0);
            availability.add(response);
        }
        return availability;
    }
}
