package com.example.springboot_backend.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {

    private int status;
    private String message;
    private String details;
    private LocalDateTime localDateTime;


    public ErrorResponse(int status, String message, String details, LocalDateTime localDateTime) {
        this.status = status;
        this.message = message;
        this.details = details;
        this.localDateTime = localDateTime;
    }
}
