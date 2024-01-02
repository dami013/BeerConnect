package com.bicoccaprojects.beerconnect.exception.beer;

public class BeerNotFoundException extends RuntimeException{

    public BeerNotFoundException(String message){
        super(message);
    }

    public BeerNotFoundException(Long id){
        super("There is no beer with "+id.toString()+" id");
    }
}
