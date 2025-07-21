package com.easylink.easylink.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class IncorrectAnswerException extends RuntimeException{

    public IncorrectAnswerException(String message){
        super(message);
    }
}
