package com.springboot.loyaltymanagementsystem.dto;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightBookingResponse {
    private Long bookingId;
    private String membershipNumber;
    private String flightNumber;
    private Double originalPrice;
    private Double discountedPrice;
    private Float pointsUsed;
    private Float pointsEarned;
    private String status;
}
