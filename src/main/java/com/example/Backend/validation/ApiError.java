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
    private String rejectedValue;
    private String message;
    private String debugMessage;
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
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ApiError(HttpStatus status, String field, String rejectedValue, String message,Throwable ex) {
        this.status = status;
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
        this.timestamp = LocalDateTime.now().format(Utils.formatter);
        this.debugMessage = ex.getLocalizedMessage();
    }
    public ApiError(HttpStatus status, String field, String rejectedValue, String message) {
        this.status = status;
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
        this.timestamp = LocalDateTime.now().format(Utils.formatter);
        this.debugMessage = null;
    }
}
