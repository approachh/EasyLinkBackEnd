package com.easylink.easylink.exceptions;

public class RateLimitExceededException extends RuntimeException{
    public RateLimitExceededException(String message){
        super(message);
    }
}
