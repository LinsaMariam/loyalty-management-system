package com.springboot.loyaltymanagementsystem.service;

import com.springboot.loyaltymanagementsystem.entity.Flight;
import com.springboot.loyaltymanagementsystem.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {
    @Autowired
    private FlightRepository flightRepository;

    public Flight getFlightByFlightNumber(String flightNumber) {
        return flightRepository.findByFlightNumber(flightNumber)
                .orElseThrow(() -> new RuntimeException("Flight not found with flight number: " + flightNumber));
    }

    public Flight saveFlight(Flight flight) {
        return flightRepository.save(flight);
    }


    public Flight getFlightById(String flightId) {
        return flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found with ID: " + flightId));
    }

    public void updateFlightSeats(String flightId, int seats) {
        Flight flight = getFlightById(flightId);
        flight.setAvailableSeats(flight.getAvailableSeats() - seats);
        flightRepository.save(flight);
    }

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }
}