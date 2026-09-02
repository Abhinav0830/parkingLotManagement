package com.example.parkingLotManagement.controllers;
import com.example.parkingLotManagement.dtos.PreBookingRequest;
import com.example.parkingLotManagement.entities.PreBooking;
import com.example.parkingLotManagement.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/create")
    public ResponseEntity<PreBooking> createBooking(@RequestBody PreBookingRequest request) {
        return ResponseEntity.ok(bookingService.createBooking(request));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<PreBooking> cancelBooking(@PathVariable Long id) {

        return ResponseEntity.ok(bookingService.cancelBooking(id));
    }
}