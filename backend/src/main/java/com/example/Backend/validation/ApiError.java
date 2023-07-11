package com.example.Backend.validation;

import com.example.Backend.utils.Utils;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
    private HttpStatus status;
    private String timestamp;
    private String field;
    private String message;
    private ApiError() {
        timestamp = LocalDateTime.now().format(Utils.formatter);
    }

    public ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    public ApiError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
    }

    public ApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
    }

    public ApiError(HttpStatus status, String field, String message,Throwable ex) {
        this.status = status;
        this.field = field;
        this.message = message;
        this.timestamp = LocalDateTime.now().format(Utils.formatter);
    }
    public ApiError(HttpStatus status, String field, String message) {
        this.status = status;
        this.field = field;
        this.message = message;
        this.timestamp = LocalDateTime.now().format(Utils.formatter);
    }
}
