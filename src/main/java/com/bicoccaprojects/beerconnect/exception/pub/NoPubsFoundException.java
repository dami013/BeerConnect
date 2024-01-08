package com.bicoccaprojects.beerconnect.exception.pub;

public class NoPubsFoundException extends RuntimeException{
    public NoPubsFoundException(){
        super("There are 0 pubs in the DB");
    }
}
