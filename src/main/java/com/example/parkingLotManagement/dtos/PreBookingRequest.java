package com.example.parkingLotManagement.dtos;

import com.example.parkingLotManagement.enums.VehicleType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PreBookingRequest {
    int level;
    String vehicleNmber;
    VehicleType vehicleType;
    LocalDateTime startTime;
    LocalDateTime endTime;
}
