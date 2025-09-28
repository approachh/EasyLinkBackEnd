package com.easylink.easylink.exceptions;

public class VibeLimitExceededException extends RuntimeException{
    public VibeLimitExceededException(String message){
        super(message);
    }
}
