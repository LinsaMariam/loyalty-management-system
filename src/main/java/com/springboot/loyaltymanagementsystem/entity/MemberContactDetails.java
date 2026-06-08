package com.springboot.loyaltymanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberContactDetails {

    @Id
    @Column(unique = true, nullable = false)
    private String membershipNumber;

    private String permanentAddress;
    private String permanentCity;
    private String permanentState;
    private String permanentCountry;
    private String permanentZipCode;

    private String presentAddress;
    private String presentCity;
    private String presentState;
    private String presentCountry;
    private String presentZipCode;

    private String emailAddress;
    private String phoneNumber;
    private String mobileNumber;
}
