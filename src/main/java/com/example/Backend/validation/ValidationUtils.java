package com.example.Backend.validation;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidationUtils {

    public <T> void checkForNull(String fieldName , T fieldValue) throws InputNotLogicallyValidException {
        if (fieldValue == null){
            throw new InputNotLogicallyValidException(
                    fieldName,
                    fieldName + " can't be NULL !");
        }
    }

    public <T> void checkForNullItems(List<String> fieldNames , List<T> fieldValues) throws InputNotLogicallyValidException {
        for (int i = 0; i<fieldNames.size();i++){
            String name = fieldNames.get(i);
            if (fieldValues.get(i) == null){
                throw new InputNotLogicallyValidException(
                        name,
                        name + " can't be NULL !");
            }
        }
    }
    public void checkForBlankItems(List<String> fieldNames , List<String> fieldValues) throws InputNotLogicallyValidException {
        for (int i = 0; i<fieldNames.size();i++){
            String name = fieldNames.get(i);
            if (fieldValues.get(i).replace(" ","").length() == 0){
                throw new InputNotLogicallyValidException(
                        name,
                        name + " can't be BLANK \" \" !");
            }
        }
    }

    public void checkForEmptyItems(List<String> fieldNames , List<String> fieldValues) throws InputNotLogicallyValidException {
        for (int i = 0; i<fieldNames.size();i++){
            String name = fieldNames.get(i);
            if (fieldValues.get(i).length() == 0){
                throw new InputNotLogicallyValidException(
                        name,
                        name + " can't be EMPTY \"\" !");
            }
        }
    }

    public void checkForEmptyString(String fieldName , String fieldValue) throws InputNotLogicallyValidException {
        if (fieldValue.equals("")){
            throw new InputNotLogicallyValidException(
                    fieldName,
                    fieldName + " can't be EMPTY \"\" !");
        }
    }
    public void checkForBlankString(String fieldName , String fieldValue) throws InputNotLogicallyValidException {
        if (fieldValue.replace(" ","").length() == 0){
            throw new InputNotLogicallyValidException(
                    fieldName,
                    fieldName + " can't be BLANK \" \" !");
        }
    }
    public void checkForPasswordLength(String fieldName , String fieldValue) throws InputNotLogicallyValidException {
        if (fieldValue.length() < 8){
            throw new InputNotLogicallyValidException(
                    fieldName,
                    fieldName + " can't be Less than 8 characters !");
        }
    }

}
