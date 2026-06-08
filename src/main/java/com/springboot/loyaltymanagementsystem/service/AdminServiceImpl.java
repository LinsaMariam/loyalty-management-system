package com.springboot.loyaltymanagementsystem.service;

import com.springboot.loyaltymanagementsystem.entity.MemberPersonalDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AdminServiceImpl {
    Page<MemberPersonalDetails> getMembers(Pageable pageable);
    MemberPersonalDetails getMemberByMembershipNumber(String membershipNumber);
    MemberPersonalDetails getMemberByEmailAddress(String emailAddress);
    Page<MemberPersonalDetails> getMembersWithFilter(
            String membershipNumber,
            String givenName,
            String familyName,
            String countryOfResidence,
            String companyCode,
            Pageable pageable
    );
}

