package com.example.parkingLotManagement.dtos;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @Column(unique = true,nullable = false)
    private String name;
    private String pass;

}
