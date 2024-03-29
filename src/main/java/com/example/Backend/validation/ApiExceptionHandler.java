package com.example.Backend.validation;

import com.example.Backend.validation.json.InvalidParameterException;
import com.example.Backend.validation.json.JsonValidationFailedException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.xml.bind.ValidationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.swing.*;
import java.util.Map;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ApiExceptionHandler{

        @ExceptionHandler(EntityNotFoundException.class)
        protected ResponseEntity<Object> handleHttpMessageNotReadable(
                HttpMessageNotReadableException ex
                , HttpHeaders headers
                , HttpStatus status
                , WebRequest request) {
            String error = "Entity not found in the database";
            return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
        }

        private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Object> handleBeanValidation(MethodArgumentNotValidException e){
            ApiError error = new ApiError(
                    HttpStatus.BAD_REQUEST,
                    e.getBindingResult().getFieldErrors()
                            .stream()
                            .map(x -> x.getDefaultMessage())
                            .collect(Collectors.toList())
                            .get(0),
                    e);
            return buildResponseEntity(error);
        }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleBeanValidation2(ConstraintViolationException e){
        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST,
                e.getConstraintViolations().iterator().next().getMessage(),
                e);
        return buildResponseEntity(error);
    }
    @ExceptionHandler(JsonValidationFailedException.class)
    public ResponseEntity<Object> onJsonValidationFailedException(JsonValidationFailedException ex) {
        return buildResponseEntity(
                new ApiError(
                        HttpStatus.BAD_REQUEST,
                        ex.getField(),
                        ex.getMessage()
                ));
    }
    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<Object> handleInvalidParamException(InvalidParameterException ex) {
        return buildResponseEntity(
                new ApiError(
                        HttpStatus.BAD_REQUEST,
                        ex.getField(),
                        ex.getMessage()
                ));
    }
    @ExceptionHandler(InputNotLogicallyValidException.class)
    public ResponseEntity<Object> handleInvalidInputException(InputNotLogicallyValidException ex) {
        return buildResponseEntity(
                new ApiError(
                        HttpStatus.BAD_REQUEST,
                        ex.getField(),
                        ex.getMessage()
                ));
    }
}
