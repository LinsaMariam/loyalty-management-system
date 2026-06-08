package com.springboot.loyaltymanagementsystem.dto;

import lombok.Data;

@Data
public class FlightBookingRequest {
    private String membershipNumber;
    private String flightId;
    private boolean usePoints;
}