package com.bicoccaprojects.beerconnect.exception.pub;

public class PubNotFoundException extends RuntimeException{

    public PubNotFoundException(String message){
        super(message);
    }
    public PubNotFoundException(Long id){
        super("There is no pub with "+id.toString()+" as id");
    }
}
