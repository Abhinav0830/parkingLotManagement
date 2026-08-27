package com.example.parkingLotManagement.repositories;

import com.example.parkingLotManagement.entities.ParkingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingHistoryRepository extends JpaRepository<ParkingHistory, Long> {

}
