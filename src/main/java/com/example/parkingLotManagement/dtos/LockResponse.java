package com.example.parkingLotManagement.dtos;

import com.example.parkingLotManagement.enums.VehicleType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LockResponse {
    @Enumerated(EnumType.STRING)
    private VehicleType type;
    private String vehicleNumber;
    private int level;
    private int lotNumber;
    private LocalDateTime in;
    private long id;
}
