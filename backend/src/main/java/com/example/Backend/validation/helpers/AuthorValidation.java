package com.example.Backend.validation.helpers;


import com.example.Backend.model.Author;
import com.example.Backend.schema.AuthorSignUpForm;
import com.example.Backend.utils.Utils;
import com.example.Backend.validation.InputNotLogicallyValidException;
import com.example.Backend.validation.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthenticatedReactiveAuthorizationManager;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

@Component
public class AuthorValidation {
    @Autowired
    private ValidationUtils validationUtils;

    @Autowired
    private Utils utils;
    public void validateRequiredData(AuthorSignUpForm form) throws InputNotLogicallyValidException {
        ArrayList<String> fieldNames = new ArrayList<>(Arrays.asList(
                "AuthorName","AuthorEmail","Password"));
        ArrayList<String> fieldValues = new ArrayList<>(Arrays.asList(
                        form.getAuthorName(),
                        form.getAuthorEmail(),
                        form.getPassword()
                ));
        validationUtils.checkForNullEmptyBlankItems(fieldNames,fieldValues);
        validationUtils.checkForPasswordLength("Password",form.getPassword());
        validationUtils.checkAuthorEmailIsNotExisted(form.getAuthorEmail());
    }
    public void validateSignUpOptionalData(AuthorSignUpForm form) throws InputNotLogicallyValidException {
        validationUtils.checkForValidContact(form.getContact());
        validationUtils.checkForValidBinaryPhoto("profilePhotoAsBinaryString",
                form.getProfilePhotoAsBinaryString());
    }
    public Author validateAuthorUpdateData(AuthorSignUpForm form) throws InputNotLogicallyValidException, IllegalAccessException {
        Author author = validationUtils.checkAuthorIsExisted(form.getAuthorId());
        enforceConstantEmail(form);
        form.setAuthorEmail(null);
        validationUtils.checkForValidAuthorName(form.getAuthorName());
        validationUtils.checkForValidPassword(form.getPassword());
        validationUtils.checkForValidContact(form.getContact());
        validationUtils.checkForValidBinaryPhoto(
                "profilePhotoAsBinaryString",
                form.getProfilePhotoAsBinaryString());
        return author;
    }
    private void enforceConstantEmail(AuthorSignUpForm form) throws InputNotLogicallyValidException {
        if (form.getAuthorEmail() != null){
            throw new InputNotLogicallyValidException(
                    "authorEmail",
                    "Email can't be adjusted once it's created !");
        }
    }
}
