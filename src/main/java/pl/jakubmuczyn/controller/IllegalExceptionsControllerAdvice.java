package pl.jakubmuczyn.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class IllegalExceptionsControllerAdvice {
    
    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<?> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.notFound().build();
    }
    
    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> handleIllegalState(IllegalStateException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
