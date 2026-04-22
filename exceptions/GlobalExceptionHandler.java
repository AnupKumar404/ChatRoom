package com.chatapp.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<?> notFoundExceptionHandler(ResourceNotFoundException exception,
                                                      HttpServletRequest req){
        ExceptionResponseDto dto = new ExceptionResponseDto(HttpStatus.NOT_FOUND.value(),
                "Not Found", exception.getMessage(), req.getRequestURI());

        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({DuplicateDataException.class})
    public ResponseEntity<?> duplicateDataException(DuplicateDataException exception,
                                                      HttpServletRequest req){
        ExceptionResponseDto dto = new ExceptionResponseDto(HttpStatus.CONFLICT.value(),
                "Conflict", exception.getMessage(), req.getRequestURI());

        return new ResponseEntity<>(dto, HttpStatus.CONFLICT);
    }
}
