package com.springboot.loyaltymanagementsystem.repository;

import com.springboot.loyaltymanagementsystem.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, String> {
    Optional<Flight> findByFlightNumber(String flightNumber);

}
