package com.bicoccaprojects.beerconnect.exception.beer;

public class NoBeersFoundException extends RuntimeException{
    public NoBeersFoundException(){
        super("There are 0 beers in the DB");
    }
}
