package com.example.Backend.validation.json;

public class InvalidParameterException extends Exception{
    private String field;
    private String message;

    public InvalidParameterException(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public InvalidParameterException(String message, String field, String message1) {
        super(message);
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
