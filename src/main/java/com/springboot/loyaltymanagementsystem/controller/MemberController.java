package com.springboot.loyaltymanagementsystem.controller;

import com.springboot.loyaltymanagementsystem.dto.LoginRequest;
import com.springboot.loyaltymanagementsystem.dto.MemberEnrolmentRequest;
import com.springboot.loyaltymanagementsystem.dto.MemberEnrolmentResponse;
import com.springboot.loyaltymanagementsystem.exception.InvalidCredentialsException;
import com.springboot.loyaltymanagementsystem.exception.MemberNotFoundException;
import com.springboot.loyaltymanagementsystem.service.MemberService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/enrol")
    public ResponseEntity<?> createMember(@Valid @RequestBody MemberEnrolmentRequest request) {
        log.info("Attempting to enroll new member with email: {}", request.getEmailAddress());
        try {
            MemberEnrolmentResponse enrolledMember = memberService.enrolMember(request);
            if (enrolledMember == null) {
                log.error("Failed to enroll member with email: {}", request.getEmailAddress());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to enrol member.");
            }
            log.info("Successfully enrolled member with membership number: {}", enrolledMember.getMembershipNumber());
            return ResponseEntity.status(HttpStatus.CREATED).body(enrolledMember);
        } catch (Exception ex) {
            log.error("Error enrolling member with email: {}. Error: {}", request.getEmailAddress(), ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while enrolling the member: " + ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        log.info("Processing login request for email: {}", loginRequest.getEmailAddress());
        try {
            Object loginResponse = memberService.login(loginRequest);
            log.debug("Successful login for email: {}", loginRequest.getEmailAddress());
            return ResponseEntity.ok(loginResponse);
        } catch (Exception ex) {
            log.error("Login failed for email: {}. Error: {}", loginRequest.getEmailAddress(), ex.getMessage(), ex);
            throw new InvalidCredentialsException("Invalid credentials provided");
        }
    }

    @GetMapping("/portal/{id}")
    public ResponseEntity<?> getMemberPortal(@PathVariable String id) {
        log.info("Fetching member portal data for ID: {}", id);
        try {
            Object portalData = memberService.getMemberPortal(id);
            log.debug("Successfully retrieved portal data for member ID: {}", id);
            return ResponseEntity.ok(portalData);
        } catch (Exception ex) {
            log.error("Failed to fetch portal data for member ID: {}. Error: {}", id, ex.getMessage(), ex);
            throw new MemberNotFoundException("Member Data Not Found");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMember(@PathVariable String id, @RequestBody MemberEnrolmentRequest request) {
        log.info("Attempting to update member with ID: {}", id);
        try {
            MemberEnrolmentResponse updated = memberService.updateMember(id, request);
            log.info("Successfully updated member with ID: {}", id);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            log.error("Failed to update member with ID: {}. Error: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update member: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable String id) {
        log.info("Attempting to delete member with ID: {}", id);
        try {
            memberService.deleteMember(id);
            log.info("Successfully deleted member with ID: {}", id);
            return ResponseEntity.ok("Member deleted successfully.");
        } catch (Exception e) {
            log.error("Failed to delete member with ID: {}. Error: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete member: " + e.getMessage());
        }
    }
}