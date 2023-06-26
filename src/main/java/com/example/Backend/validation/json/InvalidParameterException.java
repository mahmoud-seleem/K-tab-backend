package com.example.Backend.validation.json;

public class InvalidParameterException extends Exception{
    private String field;
    private String rejectedValue;
    private String message;

    public InvalidParameterException(String field, String rejectedValue, String message) {
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }

    public InvalidParameterException(String message, String field, String rejectedValue, String message1) {
        super(message);
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message1;
    }

    public InvalidParameterException(String message, Throwable cause, String field, String rejectedValue, String message1) {
        super(message, cause);
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message1;
    }

    public InvalidParameterException(Throwable cause, String field, String rejectedValue, String message) {
        super(cause);
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }

    public InvalidParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String field, String rejectedValue, String message1) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message1;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getRejectedValue() {
        return rejectedValue;
    }

    public void setRejectedValue(String rejectedValue) {
        this.rejectedValue = rejectedValue;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
