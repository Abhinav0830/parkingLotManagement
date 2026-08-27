package com.example.parkingLotManagement.dtos;

import com.example.parkingLotManagement.enums.VehicleType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LockRequest {

    @Enumerated(EnumType.STRING)
    VehicleType Type;
    private String vehicleNumber;
    private int level;

}
