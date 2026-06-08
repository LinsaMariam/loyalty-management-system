package com.springboot.loyaltymanagementsystem.exception;


public class DuplicateMemberException extends RuntimeException {
    public DuplicateMemberException(String message) {
        super(message);
    }
}

