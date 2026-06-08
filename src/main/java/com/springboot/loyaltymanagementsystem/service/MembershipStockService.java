package com.springboot.loyaltymanagementsystem.service;

import com.springboot.loyaltymanagementsystem.repository.MemberPersonalDetailsRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class MembershipStockService {

    private final MemberPersonalDetailsRepository memberRepo;
    private final AtomicLong counter = new AtomicLong();

    public MembershipStockService(MemberPersonalDetailsRepository memberRepo) {
        this.memberRepo = memberRepo;
    }

    @PostConstruct
    public void init() {
        // Find max numeric part of membershipNumber in DB
        String maxMembershipNumber = memberRepo.findMaxMembershipNumber();

        long startValue = 100000L; // default start if none in DB
        if (maxMembershipNumber != null && maxMembershipNumber.startsWith("EM")) {
            try {
                startValue = Long.parseLong(maxMembershipNumber.substring(2)) + 1;
            } catch (NumberFormatException e) {
                // fallback to default startValue
            }
        }
        counter.set(startValue);
    }

    public String generateMembershipNumber() {
        return "EM" + counter.getAndIncrement();
    }
}
