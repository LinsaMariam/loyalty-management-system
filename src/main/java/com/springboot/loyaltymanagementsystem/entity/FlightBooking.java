package com.springboot.loyaltymanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String membershipNumber;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    private Double originalPrice;
    private Double discountedPrice;
    private Double pointsUsed;
    private Double pointsEarned;
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date bookingDate;
}