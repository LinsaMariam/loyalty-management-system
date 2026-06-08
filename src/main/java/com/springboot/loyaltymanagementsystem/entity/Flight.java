package com.springboot.loyaltymanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String flightNumber;
    private String origin;
    private String destination;
    private String departureTime;
    private Double price;
    private Integer availableSeats;
}