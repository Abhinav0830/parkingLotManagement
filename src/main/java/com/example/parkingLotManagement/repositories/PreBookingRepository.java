package com.example.parkingLotManagement.repositories;

import com.example.parkingLotManagement.entities.PreBooking;
import com.example.parkingLotManagement.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PreBookingRepository extends JpaRepository<PreBooking, Long> {
    List<PreBooking> findByLevelAndLotAndStatusAndStartTimeLessThanAndEndTimeGreaterThan(int level, int lot, BookingStatus bookingStatus, LocalDateTime endTime, LocalDateTime startTime);

    List<PreBooking> findByVehicleNumberAndStatus(String vehicleNumber, BookingStatus bookingStatus);
}
