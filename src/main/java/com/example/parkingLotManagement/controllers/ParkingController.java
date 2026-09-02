package com.example.parkingLotManagement.controllers;

import com.example.parkingLotManagement.dtos.*;
import com.example.parkingLotManagement.service.ParkingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Controller
@RequestMapping("/api/parking")
public class ParkingController {

    ParkingService parkingService;
    public ParkingController(ParkingService parkingService){
        this.parkingService = parkingService;
    }

    @GetMapping("/availability")
    public ResponseEntity<List<AvailabilityResponse>> availability(Authentication auth){
         return ResponseEntity.ok(parkingService.getAvailability(auth));
    }

    @PostMapping("/lock")
    public ResponseEntity<LockResponse> lockLot(@RequestBody LockRequest lockRequest){
        if(parkingService.lockLot(lockRequest) == null){
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(parkingService.lockLot(lockRequest));
    }
    @PostMapping("/unlock")
    public ResponseEntity<UnlockResponse> unlockLot(@RequestBody UnlockRequest unlockRequest) {
        return ResponseEntity.ok(parkingService.unlockLot(unlockRequest));
    }

}
