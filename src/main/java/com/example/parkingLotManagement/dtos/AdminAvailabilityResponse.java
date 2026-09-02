package com.example.parkingLotManagement.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AdminAvailabilityResponse implements AvailabilityResponse{
    private int level;
    private int twa;
    private int fwa;
}
