package com.example.Backend.validation.helpers;


import com.example.Backend.schema.AuthorSignUpForm;
import com.example.Backend.validation.InputNotLogicallyValidException;
import com.example.Backend.validation.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class AuthorValidation {
    @Autowired
    private ValidationUtils validationUtils;
    public <T> void checkForReqDataIsNull(List<String> fieldNames , List<T> fieldValues) throws InputNotLogicallyValidException {

    }

    public void ValidateRequiredData(AuthorSignUpForm form) throws InputNotLogicallyValidException {
        ArrayList<String> fieldNames = new ArrayList<>(Arrays.asList(
                "AuthorName","AuthorEmail","Password"));
        ArrayList<String> fieldValues = new ArrayList<>(Arrays.asList(
                        form.getAuthorName(),
                        form.getAuthorEmail(),
                        form.getPassword()
                ));
        checkForReqDataIsNull(fieldNames,fieldValues);
    }

}
