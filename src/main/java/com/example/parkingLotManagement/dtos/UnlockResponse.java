package com.example.parkingLotManagement.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class UnlockResponse {

    private String vehicleNumber;

    private int lotNumber;
    private long id;
    private LocalDateTime in;
    private LocalDateTime out;
    private double fee;
}
