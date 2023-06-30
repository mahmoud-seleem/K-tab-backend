package com.example.Backend.validation;

import com.example.Backend.Repository.AuthorRepository;
import com.example.Backend.model.Author;
import com.example.Backend.security.AppUser;
import com.example.Backend.validation.json.InvalidParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidationUtils {
    @Autowired
    private AuthorRepository authorRepository;

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
    public  void checkForNullEmptyBlankItems(List<String> fieldNames , List<String> fieldValues) throws InputNotLogicallyValidException {
        checkForNullItems(fieldNames,fieldValues);
        checkForEmptyItems(fieldNames,fieldValues);
        checkForBlankItems(fieldNames,fieldValues);
    }
    public void checkEmailIsNotExisted(String email){

    }
}
