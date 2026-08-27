package com.example.parkingLotManagement.repositories;

import com.example.parkingLotManagement.entities.ParkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace,Long> {

    ParkingSpace findByLevel(int level);
}
