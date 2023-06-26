package com.example.Backend.validation;

import com.example.Backend.validation.json.JsonValidationFailedException;
import com.networknt.schema.ValidationMessage;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
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
    @ExceptionHandler(JsonValidationFailedException.class)
    public ResponseEntity<Map<String, Object>> onJsonValidationFailedException(JsonValidationFailedException ex) {
        List<String> messages = ex.getValidationMessages().stream()
                .map(ValidationMessage::getMessage)
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(Map.of(
                "message", "Json validation failed",
                "details", messages
        ));
    }
        //other exception handlers below

}
