package com.example.parkingLotManagement.service;


import com.example.parkingLotManagement.dtos.PreBookingRequest;
import com.example.parkingLotManagement.entities.ParkingLot;
import com.example.parkingLotManagement.entities.ParkingSpace;
import com.example.parkingLotManagement.entities.PreBooking;
import com.example.parkingLotManagement.enums.BookingStatus;
import com.example.parkingLotManagement.exceptions.ResourceNotFoundException;
import com.example.parkingLotManagement.repositories.ParkingLotRepository;
import com.example.parkingLotManagement.repositories.ParkingSpaceRepository;
import com.example.parkingLotManagement.repositories.PreBookingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class BookingService {
    private final PreBookingRepository preBookingRepository;
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingSpaceRepository parkingSpaceRepository;
    public BookingService(PreBookingRepository preBookingRepository,
                        ParkingLotRepository parkingLotRepository,
                        ParkingSpaceRepository parkingSpaceRepository){
        this.parkingLotRepository = parkingLotRepository;
        this.parkingSpaceRepository= parkingSpaceRepository;
        this.preBookingRepository = preBookingRepository;
    }

    public PreBooking createBooking(PreBookingRequest request){
        if(request.getEndTime().isBefore(request.getStartTime())){
            throw new IllegalArgumentException("Start time must be before end time");
        }
        List<ParkingLot> availableLots =
                parkingLotRepository.findByLevelAndTypeAndAvailableTrue(request.getLevel(), request.getVehicleType());
        if(availableLots.isEmpty()){
            throw new ResourceNotFoundException(
                    "No parkinglot available on level " + request.getLevel() + " for vehicle type "
                    + request.getVehicleType()
            );
        }

        Random random = new Random();
        while (!availableLots.isEmpty()) {

            int index = random.nextInt(availableLots.size());

            ParkingLot lot = availableLots.get(index);

            List<PreBooking> overlappingBookings =
                    preBookingRepository
                            .findByLevelAndLotAndStatusAndStartTimeLessThanAndEndTimeGreaterThan(
                                    request.getLevel(),
                                    lot.getLot(),
                                    BookingStatus.RESERVED,
                                    request.getEndTime(),
                                    request.getStartTime()
                            );

            if (overlappingBookings.isEmpty()) {

                PreBooking booking = new PreBooking();

                booking.setLevel(request.getLevel());
                booking.setLot(lot.getLot());
                booking.setVehicleNumber(request.getVehicleNmber());
                booking.setType(request.getVehicleType());
                booking.setStartTime(request.getStartTime());
                booking.setEndTime(request.getEndTime());
                booking.setStatus(BookingStatus.RESERVED);

                return preBookingRepository.save(booking);
            }

            availableLots.remove(index);
        }

        throw new ResourceNotFoundException(
                "No parking lot available for the requested time slot"
        );
    }
    public PreBooking cancelBooking(Long id) {

        PreBooking booking = preBookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Booking with id " + id + " not found"));

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new IllegalArgumentException("Booking with id " + id + " is already cancelled");
        }

        if (LocalDateTime.now().isAfter(booking.getStartTime())) {
            throw new IllegalArgumentException("Booking cannot be cancelled after the start time");
        }

        booking.setStatus(BookingStatus.CANCELLED);

        return preBookingRepository.save(booking);
    }

}

