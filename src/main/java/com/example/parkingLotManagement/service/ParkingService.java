package com.example.parkingLotManagement.service;

import com.example.parkingLotManagement.dtos.*;
import com.example.parkingLotManagement.entities.ParkingHistory;
import com.example.parkingLotManagement.entities.ParkingLot;
import com.example.parkingLotManagement.entities.ParkingSpace;
import com.example.parkingLotManagement.enums.VehicleType;
import com.example.parkingLotManagement.repositories.ParkingHistoryRepository;
import com.example.parkingLotManagement.repositories.ParkingLotRepository;
import com.example.parkingLotManagement.repositories.ParkingSpaceRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParkingService {

    private final ParkingSpaceRepository parkingSpaceRepository;
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingHistoryRepository parkingHistoryRepository;

    public ParkingService(ParkingSpaceRepository parkingSpaceRepository, ParkingLotRepository parkingLotRepository,ParkingHistoryRepository parkingHistoryRepository){
        this.parkingSpaceRepository = parkingSpaceRepository;
        this.parkingLotRepository = parkingLotRepository;
        this.parkingHistoryRepository = parkingHistoryRepository;
    }

    public List<PublicAvailabilityResponse> getAvailability(){
        List<ParkingSpace> spaces = parkingSpaceRepository.findAll();
        List<PublicAvailabilityResponse> availability = new ArrayList<>();
        for(ParkingSpace s : spaces){
            PublicAvailabilityResponse response = new PublicAvailabilityResponse(s.getLevel(),s.getTwa()>0,s.getFwa()>0);
            availability.add(response);
        }
        return availability;
    }

    @Transactional
    public LockResponse lockLot(LockRequest lockRequest){
        List<ParkingLot> avail = parkingLotRepository.findBylevelAndTypeAndAvailableTrue(lockRequest.getLevel(),lockRequest.getType());
        if(avail.isEmpty()){
            return null;
        }
        Random rand = new Random();
        int ind = rand.nextInt(avail.size());
        ParkingLot selected = avail.get(ind);
        selected.setAvailable(false);
        parkingLotRepository.save(selected);

        ParkingSpace space = parkingSpaceRepository.findByLevel(lockRequest.getLevel());
        VehicleType type = lockRequest.getType();
        if(type == VehicleType.FW){
            space.setFwa(space.getFwa()-1);

        }
        else{
            space.setTwa(space.getTwa()-1);
        }
        parkingSpaceRepository.save(space);

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

        return response;


    }

    @Transactional
    public UnlockResponse unlockLot(UnlockRequest unlockRequest){
        LocalDateTime out = LocalDateTime.now();
        ParkingHistory history = parkingHistoryRepository.findByVehicleNumberAndLotAndOutIsNull(unlockRequest.getVehicleNumber(),unlockRequest.getLot());
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

        return response;

    }

    private double calculateFee(LocalDateTime in, LocalDateTime out) {
        return 0;
    }

}
