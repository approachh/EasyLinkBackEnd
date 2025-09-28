package com.easylink.easylink.exceptions;


public class OfferLimitExceededException extends RuntimeException{
    public OfferLimitExceededException(String message){
        super(message);
    }
}
