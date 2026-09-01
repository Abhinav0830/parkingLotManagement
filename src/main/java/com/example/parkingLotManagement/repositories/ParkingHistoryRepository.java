package com.example.parkingLotManagement.repositories;

import com.example.parkingLotManagement.entities.ParkingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingHistoryRepository extends JpaRepository<ParkingHistory, Long> {

    ParkingHistory findByVehicleNumberAndLotAndOutIsNull(String vehicleNumber, int lot);
}
