package com.example.parkingLotManagement.controllers;

import com.example.parkingLotManagement.dtos.PublicAvailabilityResponse;
import com.example.parkingLotManagement.service.ParkingService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/parkingLotManager")
public class ParkingController {

    ParkingService parkingService;
    public ParkingController(ParkingService parkingService){
        this.parkingService = parkingService;
    }

    @GetMapping("/availability")
    public ResponseEntity<List<PublicAvailabilityResponse>> availability(){
         return ResponseEntity.ok(parkingService.getAvailability());
    }



}
