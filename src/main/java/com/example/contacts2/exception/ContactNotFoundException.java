package com.example.contacts2.exception;

public class ContactNotFoundException extends RuntimeException{
    public ContactNotFoundException(String message) {
        super(message);
    }
}
