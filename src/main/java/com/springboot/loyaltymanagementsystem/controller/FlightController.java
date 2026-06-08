package com.springboot.loyaltymanagementsystem.controller;

import com.springboot.loyaltymanagementsystem.entity.Flight;
import com.springboot.loyaltymanagementsystem.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @PostMapping
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) {
        Flight savedFlight = flightService.saveFlight(flight);
        return ResponseEntity.ok(savedFlight);
    }


    @GetMapping
    public ResponseEntity<List<Flight>> getAllFlights() {
        List<Flight> flights = flightService.getAllFlights();
        return ResponseEntity.ok(flights);
    }
}