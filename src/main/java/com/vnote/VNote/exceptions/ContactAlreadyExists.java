package com.vnote.VNote.exceptions;

public class ContactAlreadyExists extends RuntimeException {
    public ContactAlreadyExists(String message) {
        super(message);
    }
}
