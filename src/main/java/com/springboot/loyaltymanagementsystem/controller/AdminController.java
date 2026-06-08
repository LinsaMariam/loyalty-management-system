package com.springboot.loyaltymanagementsystem.controller;

import com.springboot.loyaltymanagementsystem.entity.MemberPersonalDetails;
import com.springboot.loyaltymanagementsystem.exception.MemberNotFoundException;
import com.springboot.loyaltymanagementsystem.repository.MemberPersonalDetailsRepository;
import com.springboot.loyaltymanagementsystem.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private MemberPersonalDetailsRepository personalRepo;

    @GetMapping("/all")
    public Page<MemberPersonalDetails> getAllMembers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        log.info("Fetching all members, page: {}, size: {}", page, size);
        Page<MemberPersonalDetails> members = adminService.getMembers(PageRequest.of(page, size));
        log.debug("Retrieved {} members for page {}", members.getTotalElements(), page);
        return members;
    }

    @GetMapping
    public Page<MemberPersonalDetails> getMembers(
            @RequestParam(required = false) String membershipNumber,
            @RequestParam(required = false) String givenName,
            @RequestParam(required = false) String familyName,
            @RequestParam(required = false) String countryOfResidence,
            @RequestParam(required = false) String companyCode,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        log.info("Fetching members with filters - membershipNumber: {}, givenName: {}, familyName: {}, countryOfResidence: {}, companyCode: {}, page: {}, size: {}",
                membershipNumber, givenName, familyName, countryOfResidence, companyCode, page, size);

        Pageable pageable = PageRequest.of(page, size);
        Page<MemberPersonalDetails> members = adminService.getMembersWithFilter(
                membershipNumber,
                givenName,
                familyName,
                countryOfResidence,
                companyCode,
                pageable
        );

        log.debug("Retrieved {} members with applied filters for page {}", members.getTotalElements(), page);
        return members;
    }

    @GetMapping("/id/{memberShipNumber}")
    public ResponseEntity<MemberPersonalDetails> getMemberByMembershipNumber(@PathVariable String memberShipNumber) {
        log.info("Fetching member by membership number: {}", memberShipNumber);
        MemberPersonalDetails member = adminService.getMemberByMembershipNumber(memberShipNumber);
        if (member != null) {
            log.debug("Found member with membership number: {}", memberShipNumber);
            return ResponseEntity.status(HttpStatus.OK).body(member);
        } else {
            log.warn("No member found with membership number: {}", memberShipNumber);
            throw new MemberNotFoundException("No member found with memberShipNumber:" + memberShipNumber);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<MemberPersonalDetails> getMemberByEmail(@PathVariable String email) {
        log.info("Fetching member by email: {}", email);
        MemberPersonalDetails member = adminService.getMemberByEmailAddress(email);
        if (member != null) {
            log.debug("Found member with email: {}", email);
            return ResponseEntity.status(HttpStatus.OK).body(member);
        } else {
            log.warn("No member found with email: {}", email);
            throw new MemberNotFoundException("No member found with email id:" + email);
        }
    }
}