package com.example.Backend.validation;

import lombok.Data;
import org.springframework.stereotype.Component;


@Component
public class InputNotLogicallyValidException extends Exception{
    private String field;
    private String message;

    public InputNotLogicallyValidException(){

    }

    public InputNotLogicallyValidException(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public InputNotLogicallyValidException(String message, String field, String message1) {
        super(message);
        this.field = field;
        this.message = message1;
    }

    public InputNotLogicallyValidException(String message, Throwable cause, String field, String message1) {
        super(message, cause);
        this.field = field;
        this.message = message1;
    }

    public InputNotLogicallyValidException(Throwable cause, String field, String message) {
        super(cause);
        this.field = field;
        this.message = message;
    }

    public InputNotLogicallyValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String field, String message1) {
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
