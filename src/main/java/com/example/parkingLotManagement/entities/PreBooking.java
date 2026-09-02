package com.example.parkingLotManagement.entities;

import com.example.parkingLotManagement.enums.BookingStatus;
import com.example.parkingLotManagement.enums.VehicleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "preBookings")
public class PreBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int level;
    private int lot;
    private String vehicleNumber;

    @Enumerated(EnumType.STRING)
    private VehicleType type;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}
