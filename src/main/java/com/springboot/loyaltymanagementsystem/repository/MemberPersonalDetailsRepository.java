package com.springboot.loyaltymanagementsystem.repository;


import com.springboot.loyaltymanagementsystem.entity.MemberPersonalDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberPersonalDetailsRepository
        extends JpaRepository<MemberPersonalDetails, Long>,
        JpaSpecificationExecutor<MemberPersonalDetails> {

    boolean existsByContactDetailsEmailAddress(String emailAddress);
    MemberPersonalDetails findByMembershipNumber(String membershipNumber);
    MemberPersonalDetails findByContactDetailsEmailAddress(String emailAddress);
    @Query("SELECT MAX(m.membershipNumber) FROM MemberPersonalDetails m WHERE m.membershipNumber LIKE 'EM%'")
    String findMaxMembershipNumber();

//    @Query("SELECT m FROM MemberPersonalDetails m WHERE " +
//            "(:membershipNumber IS NULL OR m.membershipNumber = :membershipNumber) AND " +
//            "(:givenName IS NULL OR LOWER(m.givenName) LIKE LOWER(CONCAT('%', :givenName, '%'))) AND " +
//            "(:familyName IS NULL OR LOWER(m.familyName) LIKE LOWER(CONCAT('%', :familyName, '%'))) AND " +
//            "(:countryOfResidence IS NULL OR m.countryOfResidence = :countryOfResidence) AND " +
//            "(:companyCode IS NULL OR m.companyCode = :companyCode)")
//    Page<MemberPersonalDetails> findMembersWithFilters(
//            @Param("membershipNumber") String membershipNumber,
//            @Param("givenName") String givenName,
//            @Param("familyName") String familyName,
//            @Param("countryOfResidence") String countryOfResidence,
//            @Param("companyCode") String companyCode,
//            Pageable pageable);

//    @Query("SELECT m FROM MemberPersonalDetails m WHERE " +
//            "(:membershipNumber IS NULL OR m.membershipNumber = :membershipNumber) AND " +
//            "(:givenName IS NULL OR LOWER(m.givenName) LIKE LOWER(CONCAT('%', :givenName, '%'))) AND " +
//            "(:familyName IS NULL OR LOWER(m.familyName) LIKE LOWER(CONCAT('%', :familyName, '%'))) AND " +
//            "(:countryOfResidence IS NULL OR m.countryOfResidence = :countryOfResidence) AND " +
//            "(:companyCode IS NULL OR m.companyCode = :companyCode)")
//    Page<MemberPersonalDetails> findMembersWithFilters(
//            @Param("membershipNumber") String membershipNumber,
//            @Param("givenName") String givenName,
//            @Param("familyName") String familyName,
//            @Param("countryOfResidence") String countryOfResidence,
//            @Param("companyCode") String companyCode,
//            Pageable pageable);
@Query("SELECT m FROM MemberPersonalDetails m WHERE " +
        "(:membershipNumber IS NULL OR m.membershipNumber = :membershipNumber) AND " +
        "(:givenName IS NULL OR LOWER(m.givenName) LIKE LOWER(CONCAT('%', :givenName, '%'))) AND " +
        "(:familyName IS NULL OR LOWER(m.familyName) LIKE LOWER(CONCAT('%', :familyName, '%'))) AND " +
        "(:countryOfResidence IS NULL OR m.countryOfResidence = :countryOfResidence) AND " +
        "(:companyCode IS NULL OR m.companyCode = :companyCode)")
Page<MemberPersonalDetails> findMembersWithFilters(
        @Param("membershipNumber") String membershipNumber,
        @Param("givenName") String givenName,
        @Param("familyName") String familyName,
        @Param("countryOfResidence") String countryOfResidence,
        @Param("companyCode") String companyCode,
        Pageable pageable);



}

