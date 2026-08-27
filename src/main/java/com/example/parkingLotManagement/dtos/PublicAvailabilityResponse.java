package com.example.parkingLotManagement.dtos;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PublicAvailabilityResponse {
    private int level;
    private boolean twa;
    private boolean fwa;
}
