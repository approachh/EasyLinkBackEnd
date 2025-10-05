package com.easylink.easylink.controllers;

import com.easylink.easylink.exceptions.*;
import com.easylink.easylink.vibe_service.infrastructure.exception.OfferUpdateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionalHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {
        String messageKey = ex.getReason() != null ? ex.getReason() : "unknown_error";
        return ResponseEntity.status(ex.getStatusCode()).body(Map.of(
                "message", messageKey,
                "status", ex.getStatusCode().value(),
                "timestamp", LocalDateTime.now(),
                "error", "Request Error"
        ));
    }
    @ExceptionHandler(UserLockedException.class)
    public ResponseEntity<Map<String, Object>> handleUserLocked(UserLockedException ex) {
        return buildErrorResponse(HttpStatus.LOCKED, "Account Locked", ex.getMessage());
    }

    @ExceptionHandler(IncorrectAnswerException.class)
    public ResponseEntity<Map<String, Object>> handleIncorrectAnswer(IncorrectAnswerException ex) {
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

    @ExceptionHandler(IllegalStateException.class)
    private ResponseEntity<String> handleIllegalState(IllegalStateException ex) {
        HttpStatus status = HttpStatus.CONFLICT; // 409

        return ResponseEntity.status(status).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicateAccountException.class)
    private ResponseEntity<String> handleDuplicateAccount(DuplicateAccountException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<String> handleLimit(RuntimeException ex){
        return ResponseEntity.status(429).body(ex.getMessage());
    }

    @ExceptionHandler(OfferLimitExceededException.class)
    public ResponseEntity<String> handleOfferLimit(OfferLimitExceededException ex){
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(ex.getMessage());
    }

    @ExceptionHandler(VibeLimitExceededException.class)
    public ResponseEntity<String> handleVibeLimit(VibeLimitExceededException ex){
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(ex.getMessage());
    }
}
