package com.vnote.VNote.exceptions;

public class BusinessAlreadyExists extends RuntimeException {
    public BusinessAlreadyExists(String message) {
        super(message);
    }
}
