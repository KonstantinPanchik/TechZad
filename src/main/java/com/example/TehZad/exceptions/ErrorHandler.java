package com.example.TehZad.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleNotFound(final NotFoundException e) {
        log.error(e.getMessage());
        ResponseEntity notFound = ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        return notFound;
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleNotFound(final AccessDeniedException e) {
        log.error(e.getMessage());
        ResponseEntity notFound = ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        return notFound;
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleNotFound(final Throwable e) {
        log.error(e.getMessage());
        ResponseEntity notFound = ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        return notFound;
    }

}
