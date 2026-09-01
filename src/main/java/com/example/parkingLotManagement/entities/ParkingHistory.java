package com.example.parkingLotManagement.entities;

import com.example.parkingLotManagement.enums.VehicleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Parking_history")

public class ParkingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int level;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
    private String vehicleNumber;
    private int lot;
    @Column(name = "in_time")
    private LocalDateTime in;
    @Column(name = "out_time")
    private LocalDateTime out;
    private double fee;

}
