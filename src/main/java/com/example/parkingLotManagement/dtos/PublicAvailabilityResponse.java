package com.example.parkingLotManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PublicAvailabilityResponse implements AvailabilityResponse {
    private int level;
    private boolean twa;
    private boolean fwa;
}
