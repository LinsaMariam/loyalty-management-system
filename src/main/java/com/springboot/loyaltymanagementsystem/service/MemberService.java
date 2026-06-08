package com.springboot.loyaltymanagementsystem.service;

import com.springboot.loyaltymanagementsystem.config.LoyaltyConstantsConfig;
import com.springboot.loyaltymanagementsystem.dto.LoginRequest;
import com.springboot.loyaltymanagementsystem.dto.MemberEnrolmentRequest;
import com.springboot.loyaltymanagementsystem.dto.MemberEnrolmentResponse;
import com.springboot.loyaltymanagementsystem.entity.MemberContactDetails;
import com.springboot.loyaltymanagementsystem.entity.MemberPersonalDetails;
import com.springboot.loyaltymanagementsystem.exception.DuplicateMemberException;
import com.springboot.loyaltymanagementsystem.exception.InvalidCredentialsException;
import com.springboot.loyaltymanagementsystem.exception.MemberNotFoundException;
import com.springboot.loyaltymanagementsystem.repository.MemberPersonalDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService implements MemberServiceImpl {

    @Autowired
    private MemberPersonalDetailsRepository memberRepo;
    @Autowired
    private MembershipStockService membershipStockService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private LoyaltyConstantsConfig constants;

    public MemberService(MemberPersonalDetailsRepository memberRepo, BCryptPasswordEncoder passwordEncoder) {
        this.memberRepo = memberRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public MemberEnrolmentResponse enrolMember(MemberEnrolmentRequest request) {

        // Check for duplicate by email
        if (memberRepo.existsByContactDetailsEmailAddress(request.getEmailAddress())) {
            throw new DuplicateMemberException("A member with the email '" + request.getEmailAddress() + "' already exists.");
        }

        // Map request to personal details entity
        MemberPersonalDetails personalDetails = new MemberPersonalDetails();
        personalDetails.setGivenName(request.getGivenName());
        personalDetails.setFamilyName(request.getFamilyName());
        personalDetails.setGender(request.getGender());
        personalDetails.setDateOfBirth(request.getDateOfBirth());
        personalDetails.setCountryOfResidence(request.getCountryOfResidence());
        personalDetails.setNationality(request.getNationality());
        personalDetails.setCompanyCode(request.getCompanyCode());
        personalDetails.setPreferredLanguage(request.getPreferredLanguage());
        personalDetails.setPreferredAddress(request.getPreferredAddress());

        // 1. Encode the password
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        // 2. Save it in the entity
        personalDetails.setPassword(hashedPassword);

        // Generate unique membership number
        String membershipNumber = membershipStockService.generateMembershipNumber();
        personalDetails.setMembershipNumber(membershipNumber);

        personalDetails.setLoyaltyTier(constants.getINITIAL_TIER());
        personalDetails.setRewardPoints(constants.getBONUS_POINT());


        // Map contact details
        MemberContactDetails contactDetails = new MemberContactDetails();
        contactDetails.setMembershipNumber(membershipNumber);

        //  Set Permanent Address
        contactDetails.setPermanentAddress(request.getPermanentAddress());
        contactDetails.setPermanentCity(request.getPermanentCity());
        contactDetails.setPermanentState(request.getPermanentState());
        contactDetails.setPermanentCountry(request.getPermanentCountry());
        contactDetails.setPermanentZipCode(request.getPermanentZipCode());

        //  Set Present Address (same or different)
        if (request.isSameAsPermanentAddress()) {
            contactDetails.setPresentAddress(request.getPermanentAddress());
            contactDetails.setPresentCity(request.getPermanentCity());
            contactDetails.setPresentState(request.getPermanentState());
            contactDetails.setPresentCountry(request.getPermanentCountry());
            contactDetails.setPresentZipCode(request.getPermanentZipCode());
        } else {
            contactDetails.setPresentAddress(request.getPresentAddress());
            contactDetails.setPresentCity(request.getPresentCity());
            contactDetails.setPresentState(request.getPresentState());
            contactDetails.setPresentCountry(request.getPresentCountry());
            contactDetails.setPresentZipCode(request.getPresentZipCode());
        }


        String email = request.getEmailAddress();
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        if (email == null || !email.matches(regex)) {
            throw new IllegalArgumentException("Invalid email format: " + email);
        }

        contactDetails.setEmailAddress(email);

        contactDetails.setPhoneNumber(request.getPhoneNumber());
        contactDetails.setMobileNumber(request.getMobileNumber());

        // Link both sides
        personalDetails.setContactDetails(contactDetails);

        // Save both (cascade)
        MemberPersonalDetails saved = memberRepo.save(personalDetails);

        // Build response
        MemberEnrolmentResponse response = new MemberEnrolmentResponse();
        response.setMembershipNumber(saved.getMembershipNumber());
        response.setGivenName(saved.getGivenName());
        response.setFamilyName(saved.getFamilyName());
        response.setEmailAddress(saved.getContactDetails().getEmailAddress());
        response.setStatus("Enrolled successfully");

        return response;
    }

    public MemberPersonalDetails login(LoginRequest request) {

            MemberPersonalDetails member = memberRepo.findByContactDetailsEmailAddress(request.getEmailAddress());
        if (member == null || !passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        return member;
    }

    public MemberPersonalDetails getMemberPortal(String id) {
        MemberPersonalDetails member = memberRepo.findByMembershipNumber(id);

        if (member == null) {
            throw new MemberNotFoundException("Member not found");
        }

        return member;
    }

    public MemberEnrolmentResponse updateMember(String id, MemberEnrolmentRequest request) {
        MemberPersonalDetails existing = memberRepo.findByMembershipNumber(id);

        if (existing == null) {
            throw new MemberNotFoundException("Member not found");
        }

        // Update basic details
        existing.setGivenName(request.getGivenName());
        existing.setFamilyName(request.getFamilyName());
        existing.setDateOfBirth(request.getDateOfBirth());
        existing.setGender(request.getGender());
        existing.setNationality(request.getNationality());
        existing.setCountryOfResidence(request.getCountryOfResidence());
        existing.setPreferredLanguage(request.getPreferredLanguage());
        existing.setPreferredAddress(request.getPreferredAddress());

        if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
            existing.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        // Update contact details
        MemberContactDetails contact = existing.getContactDetails();
        contact.setEmailAddress(request.getEmailAddress());
        contact.setPhoneNumber(request.getPhoneNumber());
        contact.setMobileNumber(request.getMobileNumber());

        // Update address logic (same as enrolment)
        contact.setPermanentAddress(request.getPermanentAddress());
        contact.setPermanentCity(request.getPermanentCity());
        contact.setPermanentState(request.getPermanentState());
        contact.setPermanentCountry(request.getPermanentCountry());
        contact.setPermanentZipCode(request.getPermanentZipCode());

        if (request.isSameAsPermanentAddress()) {
            contact.setPresentAddress(request.getPermanentAddress());
            contact.setPresentCity(request.getPermanentCity());
            contact.setPresentState(request.getPermanentState());
            contact.setPresentCountry(request.getPermanentCountry());
            contact.setPresentZipCode(request.getPermanentZipCode());
        } else {
            contact.setPresentAddress(request.getPresentAddress());
            contact.setPresentCity(request.getPresentCity());
            contact.setPresentState(request.getPresentState());
            contact.setPresentCountry(request.getPresentCountry());
            contact.setPresentZipCode(request.getPresentZipCode());
        }

        // Save changes
        MemberPersonalDetails saved = memberRepo.save(existing);

        MemberEnrolmentResponse response = new MemberEnrolmentResponse();
        response.setMembershipNumber(saved.getMembershipNumber());
        response.setGivenName(saved.getGivenName());
        response.setFamilyName(saved.getFamilyName());
        response.setEmailAddress(saved.getContactDetails().getEmailAddress());
        response.setStatus("Updated successfully");

        return response;
    }

    public void deleteMember(String id) {
        MemberPersonalDetails member = memberRepo.findByMembershipNumber(id);

        if (member == null) {
            throw new MemberNotFoundException("Member not found");
        }

        memberRepo.delete(member);
    }




}
