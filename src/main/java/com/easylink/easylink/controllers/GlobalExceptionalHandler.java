package com.easylink.easylink.controllers;

import com.easylink.easylink.exceptions.IncorrectAnswerException;
import com.easylink.easylink.exceptions.UserLockedException;
import com.easylink.easylink.vibe_service.infrastructure.exception.OfferUpdateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionalHandler {

    @ExceptionHandler(UserLockedException.class)
    public ResponseEntity<Map<String, Object>> handleUserLocked(UserLockedException ex) {
        return buildErrorResponse(HttpStatus.LOCKED, "Account Locked", ex.getMessage());
    }

    @ExceptionHandler(IncorrectAnswerException.class)
    public ResponseEntity<Map<String, Object>> handleIncorrectAnswer(IncorrectAnswerException ex) {
        System.out.println("IncorrectAnswerException caught by GlobalExceptionHandler!");
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Incorrect answer(s)", ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAllOtherErrors(Exception ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Error", ex.getMessage());
    }

    @ExceptionHandler(OfferUpdateException.class)
    public ResponseEntity<Map<String,Object>>handleOfferException(OfferUpdateException ex){
        return buildErrorResponse(HttpStatus.BAD_REQUEST,"Offer update error",ex.getMessage());
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String error, String message) {
        return ResponseEntity.status(status).body(Map.of(
                "timestamp", LocalDateTime.now(),
                "status", status.value(),
                "error", error,
                "message", message
        ));
    }
}
