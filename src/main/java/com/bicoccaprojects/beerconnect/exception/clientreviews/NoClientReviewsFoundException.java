package com.bicoccaprojects.beerconnect.exception.clientreviews;

public class NoClientReviewsFoundException extends RuntimeException{
    public NoClientReviewsFoundException(){
        super("There are 0 client reviews in the DB");
    }
}
