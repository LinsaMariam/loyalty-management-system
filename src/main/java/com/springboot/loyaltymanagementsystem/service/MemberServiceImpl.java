package com.springboot.loyaltymanagementsystem.service;

import com.springboot.loyaltymanagementsystem.dto.LoginRequest;
import com.springboot.loyaltymanagementsystem.dto.MemberEnrolmentRequest;
import com.springboot.loyaltymanagementsystem.dto.MemberEnrolmentResponse;
import com.springboot.loyaltymanagementsystem.entity.MemberPersonalDetails;

public interface MemberServiceImpl {
    MemberEnrolmentResponse enrolMember(MemberEnrolmentRequest request);
    MemberPersonalDetails login(LoginRequest request);
    MemberPersonalDetails getMemberPortal(String id);
}
