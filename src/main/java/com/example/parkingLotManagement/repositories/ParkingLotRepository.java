package com.example.parkingLotManagement.repositories;

import com.example.parkingLotManagement.entities.ParkingLot;
import com.example.parkingLotManagement.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot,Long> {

    List<ParkingLot> findByLevelAndTypeAndAvailableTrue(int level, VehicleType type);

    ParkingLot findByLevelAndLot(int level, int lot);
}
