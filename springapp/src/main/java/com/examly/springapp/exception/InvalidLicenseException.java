package com.examly.springapp.exception;

public class InvalidLicenseException extends RuntimeException {

    public InvalidLicenseException(String message) {
        super(message);
    }
}
