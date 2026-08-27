package com.example.parkingLotManagement.repositories;

import com.example.parkingLotManagement.entities.ParkingLot;
import com.example.parkingLotManagement.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingLotRepository extends JpaRepository<ParkingLot,Long> {

    List<ParkingLot> findBylevelAndTypeAndAvailableTrue(int level, VehicleType type);

    ParkingLot findByLevelAndLot(int level, int lot);
}
