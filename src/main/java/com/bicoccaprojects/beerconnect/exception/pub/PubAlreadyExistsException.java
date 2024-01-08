package com.bicoccaprojects.beerconnect.exception.pub;

public class PubAlreadyExistsException extends RuntimeException {
    public PubAlreadyExistsException(String message) {
        super(message);
    }
}
