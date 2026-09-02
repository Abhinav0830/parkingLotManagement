package com.example.parkingLotManagement.service;

import com.example.parkingLotManagement.dtos.*;
import com.example.parkingLotManagement.entities.ParkingHistory;
import com.example.parkingLotManagement.entities.ParkingLot;
import com.example.parkingLotManagement.entities.ParkingSpace;
import com.example.parkingLotManagement.entities.PreBooking;
import com.example.parkingLotManagement.enums.BookingStatus;
import com.example.parkingLotManagement.enums.VehicleType;
import com.example.parkingLotManagement.exceptions.ResourceNotFoundException;
import com.example.parkingLotManagement.repositories.ParkingHistoryRepository;
import com.example.parkingLotManagement.repositories.ParkingLotRepository;
import com.example.parkingLotManagement.repositories.ParkingSpaceRepository;
import com.example.parkingLotManagement.repositories.PreBookingRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ParkingService {

    private final ParkingSpaceRepository parkingSpaceRepository;
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingHistoryRepository parkingHistoryRepository;
    private final PreBookingRepository preBookingRepository;

    public ParkingService(PreBookingRepository preBookingRepository,ParkingSpaceRepository parkingSpaceRepository, ParkingLotRepository parkingLotRepository,ParkingHistoryRepository parkingHistoryRepository){
        this.parkingSpaceRepository = parkingSpaceRepository;
        this.parkingLotRepository = parkingLotRepository;
        this.parkingHistoryRepository = parkingHistoryRepository;
        this.preBookingRepository = preBookingRepository;
    }
    @Transactional
    public List<AvailabilityResponse> getAvailability(Authentication auth){
        List<ParkingSpace> spaces = parkingSpaceRepository.findAll();
        List<AvailabilityResponse> availability = new ArrayList<>();
        if(auth.getAuthorities().iterator().next().getAuthority().equals("ROLE_USER")){
            for(ParkingSpace s : spaces){
                AvailabilityResponse response = new PublicAvailabilityResponse(s.getLevel(),s.getTwa()>0,s.getFwa()>0);
                availability.add(response);
            }
        }
        else if(auth.getAuthorities().iterator().next().getAuthority().equals("ROLE_ADMIN")){

            for(ParkingSpace s : spaces){
                AvailabilityResponse response = new AdminAvailabilityResponse(s.getLevel(),s.getTwa(),s.getFwa());
                availability.add(response);
            }
        }
        return availability;
    }

    @Transactional
    public LockResponse lockLot(LockRequest lockRequest){
        ParkingLot selected = null;
        List<PreBooking> bookings =
                preBookingRepository.findByVehicleNumberAndStatus(lockRequest.getVehicleNumber(), BookingStatus.RESERVED);

        LocalDateTime now = LocalDateTime.now();

        for (PreBooking booking : bookings) {
            if (booking.getLevel() == lockRequest.getLevel()
                    && booking.getType() == lockRequest.getType()
                    && !now.isBefore(booking.getStartTime())
                    && now.isBefore(booking.getEndTime())) {
                selected = parkingLotRepository.findByLevelAndLot(booking.getLevel(), booking.getLot());
                break;
            }
        }
        if (selected == null) {
            List<ParkingLot> avail =
                    parkingLotRepository.findByLevelAndTypeAndAvailableTrue(lockRequest.getLevel(), lockRequest.getType());
            if (avail.isEmpty()) {
                throw new ResourceNotFoundException(
                        "No available lot found for level "
                                + lockRequest.getLevel()
                                + " and vehicle type "
                                + lockRequest.getType()
                );
            }
            Random rand = new Random();
            int ind = rand.nextInt(avail.size());
            selected = avail.get(ind);
        }

        if (!selected.isAvailable()) {
            throw new IllegalArgumentException("Parking lot " + selected.getLot() + " is currently occupied");
        }
        selected.setAvailable(false);
        parkingLotRepository.save(selected);

        ParkingSpace space = parkingSpaceRepository.findByLevel(lockRequest.getLevel());
        VehicleType type = lockRequest.getType();
        if(type == VehicleType.FW && space.getFwa()>0){
            space.setFwa(space.getFwa()-1);
        }
        else if(type == VehicleType.TW && space.getTwa()>0){
            space.setTwa(space.getTwa()-1);
        }


        LocalDateTime in = LocalDateTime.now();

        ParkingHistory parkingHistory = new ParkingHistory();
        parkingHistory.setLevel(lockRequest.getLevel());
        parkingHistory.setVehicleType(lockRequest.getType());
        parkingHistory.setLot(selected.getLot());
        parkingHistory.setIn(in);
        parkingHistory.setVehicleNumber(lockRequest.getVehicleNumber());
        parkingHistoryRepository.save(parkingHistory);

        LockResponse response = new LockResponse();
        response.setIn(in);
        response.setLevel(lockRequest.getLevel());
        response.setType(lockRequest.getType());
        response.setLotNumber(selected.getLot());
        response.setVehicleNumber(lockRequest.getVehicleNumber());
        response.setId(selected.getId());

        return response;


    }

    @Transactional
    public UnlockResponse unlockLot(UnlockRequest unlockRequest){
        LocalDateTime out = LocalDateTime.now();
        ParkingHistory history = parkingHistoryRepository.findByVehicleNumberAndLotAndOutIsNull(unlockRequest.getVehicleNumber(),unlockRequest.getLot())
                .orElseThrow(() -> new ResourceNotFoundException("No active parking record for vehiccle " + unlockRequest.getVehicleNumber()+ " in lot " + unlockRequest.getLot()));
        history.setOut(out);

        double fee = calculateFee(history.getIn(),out);

        parkingHistoryRepository.save(history);
        ParkingLot parkingLot = parkingLotRepository.findByLevelAndLot(history.getLevel(),history.getLot());
        parkingLot.setAvailable(true);
        parkingLotRepository.save(parkingLot);

        ParkingSpace space = parkingSpaceRepository.findByLevel(history.getLevel());
        VehicleType type = history.getVehicleType();
        if(type == VehicleType.FW){
            space.setFwa(space.getFwa()+1);

        }
        else{
            space.setTwa(space.getTwa()+1);
        }
        parkingSpaceRepository.save(space);

        UnlockResponse response = new UnlockResponse();
        response.setFee(fee);
        response.setIn(history.getIn());
        response.setLotNumber(history.getLot());
        response.setOut(history.getOut());
        response.setVehicleNumber(unlockRequest.getVehicleNumber());
        response.setId(history.getId());

        return response;

    }

    private double calculateFee(LocalDateTime in, LocalDateTime out) {
        return 0;
    }

}
