package com.example.parkingLotManagement.dtos;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PublicAvailabilityResponse implements AvailabilityResponse {
    private int level;
    private boolean twa;
    private boolean fwa;
}
