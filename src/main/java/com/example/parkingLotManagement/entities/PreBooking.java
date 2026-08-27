package com.example.parkingLotManagement.entities;

import com.example.parkingLotManagement.enums.BookingStatus;
import com.example.parkingLotManagement.enums.VehicleType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public class PreBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int level;
    private String lot;
    private String vehicleNumber;

    @Enumerated(EnumType.STRING)
    private VehicleType type;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}
