package com.springboot.loyaltymanagementsystem.repository;

import com.springboot.loyaltymanagementsystem.entity.Flight;
import com.springboot.loyaltymanagementsystem.entity.FlightBooking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightBookingRepository extends JpaRepository<FlightBooking, String> {
}
