package com.springboot.loyaltymanagementsystem.controller;

import com.springboot.loyaltymanagementsystem.dto.FlightBookingRequest;
import com.springboot.loyaltymanagementsystem.dto.FlightBookingResponse;
import com.springboot.loyaltymanagementsystem.service.FlightBookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class FlightBookingController {

    private final FlightBookingService flightBookingService;

    public FlightBookingController(FlightBookingService flightBookingService) {
        this.flightBookingService = flightBookingService;
    }

    @PostMapping("/book-flight")
    public ResponseEntity<?> bookFlight(@RequestBody FlightBookingRequest request) {
        try {
            FlightBookingResponse bookingResponse = flightBookingService.bookFlight(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(bookingResponse);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("An error occurred while booking the flight: " + ex.getMessage());
        }
    }
}