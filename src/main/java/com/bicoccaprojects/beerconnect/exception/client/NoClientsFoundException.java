package com.bicoccaprojects.beerconnect.exception.client;

public class NoClientsFoundException extends RuntimeException{
    public NoClientsFoundException(String message){
        super(message);
    }
}
