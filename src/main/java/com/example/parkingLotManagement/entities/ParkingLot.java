package com.example.parkingLotManagement.entities;


import com.example.parkingLotManagement.enums.VehicleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ParkingLot")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean available = true;
    private int level;
    private int lot;
    @Enumerated(EnumType.STRING)
    private VehicleType type;
}
