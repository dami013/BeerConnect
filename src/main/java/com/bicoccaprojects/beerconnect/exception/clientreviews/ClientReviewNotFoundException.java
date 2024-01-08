package com.bicoccaprojects.beerconnect.exception.clientreviews;

public class ClientReviewNotFoundException extends RuntimeException{

    public ClientReviewNotFoundException(String message){
        super(message);
    }

    public ClientReviewNotFoundException(Long id){
        super("There is no client review with "+id.toString()+" as id");
    }

}
