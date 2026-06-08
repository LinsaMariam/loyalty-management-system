package com.springboot.loyaltymanagementsystem.dto;

import com.springboot.loyaltymanagementsystem.entity.MemberContactDetails;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberEnrolmentRequest {

    // Personal details
    @NotBlank(message = "Given name is required")
    private String givenName;

    @NotBlank(message = "Family name is required")
    private String familyName;

    private String gender;

    @NotNull(message = "Date of birth is required")
    @PastOrPresent(message = "Date of birth must be in the past or present")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Country of residence is required")
    private String countryOfResidence;

    private String nationality;

    private String companyCode;

    private String preferredLanguage;

    private String preferredAddress;

    private MemberContactDetails contactDetails;

    // Permanent Address
    private String permanentAddress;

    private String permanentCity;

    private String permanentState;

    private String permanentCountry;

    private String permanentZipCode;

    // Present Address
    @NotBlank(message = "Present address is required")
    private String presentAddress;

    @NotBlank(message = "Present city is required ")
    private String presentCity;

    private String presentState;

    @NotBlank(message = "Present country is required ")
    private String presentCountry;

    private String presentZipCode;

    @NotBlank(message = "Email address is required")
    @Email(message = "Email address must be valid")
    private String emailAddress;

    private String phoneNumber;

    private String mobileNumber;

    private boolean sameAsPermanentAddress;

    @NotBlank(message = "Password is required")
    private String password;
}
//package com.springboot.loyaltymanagementsystem.dto;
//
//import com.springboot.loyaltymanagementsystem.entity.MemberContactDetails;
//import lombok.Data;
//
//import java.time.LocalDate;
//import java.util.Date;
//
//@Data
//public class MemberEnrolmentRequest {
//
//    // Personal details
//    private String givenName;
//    private String familyName;
//    private String gender;
//    private LocalDate dateOfBirth;
//    private String countryOfResidence;
//    private String nationality;
//    private String companyCode;
//    private String preferredLanguage;
//    private String preferredAddress;
//    private MemberContactDetails contactDetails;
//
//    //  Permanent Address
//    private String permanentAddress;
//    private String permanentCity;
//    private String permanentState;
//    private String permanentCountry;
//    private String permanentZipCode;
//
//    //  Present Address
//    private String presentAddress;
//    private String presentCity;
//    private String presentState;
//    private String presentCountry;
//    private String presentZipCode;
//
//    private String emailAddress;
//    private String phoneNumber;
//    private String mobileNumber;
//
//    private boolean sameAsPermanentAddress;
//    private String password;
//}
//
