package com.springboot.loyaltymanagementsystem.service;

import com.springboot.loyaltymanagementsystem.entity.MemberPersonalDetails;
import com.springboot.loyaltymanagementsystem.repository.MemberPersonalDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AdminService implements AdminServiceImpl {

    @Autowired
    private MemberPersonalDetailsRepository personalRepo;
    @Autowired
    private MembershipStockService membershipStockService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Page<MemberPersonalDetails> getMembers(Pageable pageable) {
        return personalRepo.findAll(pageable);
    }

    public MemberPersonalDetails getMemberByMembershipNumber(String memberShipNumber) {
        return personalRepo.findByMembershipNumber(memberShipNumber);
    }

    public MemberPersonalDetails getMemberByEmailAddress(String emailAddress) {
        return personalRepo.findByContactDetailsEmailAddress(emailAddress);
    }

    public Page<MemberPersonalDetails> getMembersWithFilter(
            String membershipNumber,
            String givenName,
            String familyName,
            String countryOfResidence,
            String companyCode,
            Pageable pageable) {

        Specification<MemberPersonalDetails> spec = (root, query, cb) -> {
            var predicates = cb.conjunction();

            if (membershipNumber != null && !membershipNumber.isBlank()) {
                predicates = cb.and(predicates,
                        cb.equal(root.get("membershipNumber"), membershipNumber));
            }

            if (givenName != null && !givenName.isBlank()) {
                predicates = cb.and(predicates,
                        cb.like(cb.lower(root.get("givenName")), "%" + givenName.toLowerCase() + "%"));
            }

            if (familyName != null && !familyName.isBlank()) {
                predicates = cb.and(predicates,
                        cb.like(cb.lower(root.get("familyName")), "%" + familyName.toLowerCase() + "%"));
            }

            if (countryOfResidence != null && !countryOfResidence.isBlank()) {
                predicates = cb.and(predicates,
                        cb.equal(root.get("countryOfResidence"), countryOfResidence));
            }

            if (companyCode != null && !companyCode.isBlank()) {
                predicates = cb.and(predicates,
                        cb.equal(root.get("companyCode"), companyCode));
            }

            return predicates;
        };

        return personalRepo.findAll(spec, pageable);
    }

}



