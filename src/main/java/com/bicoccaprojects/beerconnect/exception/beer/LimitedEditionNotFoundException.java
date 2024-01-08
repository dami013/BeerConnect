package com.bicoccaprojects.beerconnect.exception.beer;

public class LimitedEditionNotFoundException extends RuntimeException{

    public LimitedEditionNotFoundException(String message){
        super(message);
    }

    public LimitedEditionNotFoundException(Long id){
        super("There is no limited beer with "+id.toString()+" as id");
    }
}
