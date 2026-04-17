package com.example;

public class InvalidEmployeeDataException extends RuntimeException {
    public InvalidEmployeeDataException(String message) {
        super(message);
    }
}