package com.example.auth_service.exception;

import com.example.auth_service.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleException(RuntimeException ex, HttpServletRequest request) {
       ErrorResponse errorResponse = ErrorResponse.builder()
               .timestamp(LocalDateTime.now())
               .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
               .message(ex.getMessage())
               .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
               .path(request.getRequestURI())
               .build();


        return ResponseEntity.ok(errorResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex , HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.ok(errorResponse);
    }
}
