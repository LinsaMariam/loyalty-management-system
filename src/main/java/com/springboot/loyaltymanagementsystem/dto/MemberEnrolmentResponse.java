package com.springboot.loyaltymanagementsystem.dto;

import lombok.Data;

@Data
public class MemberEnrolmentResponse {
    private String membershipNumber;
    private String givenName;
    private String familyName;
    private String emailAddress;
    private String status;
}

