package com.example.Backend.validation.json;

import com.networknt.schema.ValidationMessage;

import java.util.Set;

public class JsonValidationFailedException extends RuntimeException {
    private String field;
    private String message;

    public JsonValidationFailedException(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public JsonValidationFailedException(String message, String field, String message1) {
        super(message);
        this.field = field;
        this.message = message1;
    }

    public JsonValidationFailedException(String message, Throwable cause, String field, String message1) {
        super(message, cause);
        this.field = field;
        this.message = message1;
    }

    public JsonValidationFailedException(Throwable cause, String field, String rejectedValue, String message) {
        super(cause);
        this.field = field;
        this.message = message;
    }

    public JsonValidationFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String field, String rejectedValue, String message1) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.field = field;
        this.message = message1;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }


    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
