package com.example.Backend.validation.json;

public class JsonSchemaValidationException extends Exception {
    public JsonSchemaValidationException() {
    }

    public JsonSchemaValidationException(String message) {
        super(message);
    }

    public JsonSchemaValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonSchemaValidationException(Throwable cause) {
        super(cause);
    }

    public JsonSchemaValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
