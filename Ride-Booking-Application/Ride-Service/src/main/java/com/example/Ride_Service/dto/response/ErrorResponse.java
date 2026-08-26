package com.example.Ride_Service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ErrorResponse {
    private String message;
    private HttpStatus status;
    private String path;
    private String error;
    private LocalDateTime timestamp;
}
