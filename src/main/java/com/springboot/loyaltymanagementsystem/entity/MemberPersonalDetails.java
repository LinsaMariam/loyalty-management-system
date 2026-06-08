package com.springboot.loyaltymanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "member_personal_details")
public class MemberPersonalDetails {

    @Id
    @Column(unique = true, nullable = false)
    private String membershipNumber;

    @Column(name = "given_name")
    private String givenName;
    private String familyName;
    private String gender;
    private LocalDate dateOfBirth;
    private String countryOfResidence;
    private String nationality;
    private String companyCode;
    private String preferredLanguage;
    private String preferredAddress;

    @Column(nullable = false)
    private String password;

    private String loyaltyTier;
    private float rewardPoints;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "membershipNumber", referencedColumnName = "membershipNumber")
    private MemberContactDetails contactDetails;
}
