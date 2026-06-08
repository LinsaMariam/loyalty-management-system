package com.springboot.loyaltymanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberPortal {
    private String membershipNumber;
    private String givenName;
    private String tier;
    private float rewardPoints;
//    private String email;

    // Add more if needed
}
