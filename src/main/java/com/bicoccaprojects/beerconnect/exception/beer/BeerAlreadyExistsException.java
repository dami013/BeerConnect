package com.bicoccaprojects.beerconnect.exception.beer;

public class BeerAlreadyExistsException extends RuntimeException {
    public BeerAlreadyExistsException(String message) {
        super(message);
    }
}
