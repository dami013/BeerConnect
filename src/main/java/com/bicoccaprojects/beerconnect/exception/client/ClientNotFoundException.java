package com.bicoccaprojects.beerconnect.exception.client;

public class ClientNotFoundException extends RuntimeException{

    public ClientNotFoundException(Long id){
        super("There is no client with "+id.toString()+" as id");
    }
}
