package com.bicoccaprojects.beerconnect.exception.client;

public class NoClientsFoundException extends RuntimeException{
    public NoClientsFoundException(){
        super("There are 0 clients in the DB");
    }
}
