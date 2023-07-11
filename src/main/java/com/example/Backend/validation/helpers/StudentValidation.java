package com.example.Backend.validation.helpers;

import com.example.Backend.model.Author;
import com.example.Backend.model.Student;
import com.example.Backend.schema.AuthorSignUpForm;
import com.example.Backend.schema.DisabilityHeader;
import com.example.Backend.schema.StudentSignUpForm;
import com.example.Backend.validation.InputNotLogicallyValidException;
import com.example.Backend.validation.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class StudentValidation {
    @Autowired
    private ValidationUtils validationUtils;

    public void validateRequiredData(StudentSignUpForm form) throws InputNotLogicallyValidException {
        ArrayList<String> fieldNames = new ArrayList<>(Arrays.asList(
                "StudentName","StudentEmail","Password"));
        ArrayList<String> fieldValues = new ArrayList<>(Arrays.asList(
                form.getStudentName(),
                form.getStudentEmail(),
                form.getPassword()
        ));
        validationUtils.checkForNullEmptyBlankItems(fieldNames,fieldValues);
        validationUtils.checkForPasswordLength("Password",form.getPassword());
        validationUtils.checkStudentEmailIsNotExisted(form.getStudentEmail());
    }
    public void validateSignUpOptionalData(StudentSignUpForm form) throws InputNotLogicallyValidException {
        validationUtils.checkForValidContact(form.getContact());
        validationUtils.checkForEmptyAndBlankString("educationLevel",form.getEducationLevel());
        validateDisabilities(form);
        validationUtils.checkForValidBinaryPhoto("profilePhotoAsBinaryString",
                form.getProfilePhotoAsBinaryString());
    }
    public Student validateStudentUpdateData(StudentSignUpForm form) throws InputNotLogicallyValidException, IllegalAccessException {
        Student student = validationUtils.checkStudentIsExisted(form.getStudentId());
        enforceConstantEmail(form);
        form.setStudentEmail(null);
        validationUtils.checkForValidAuthorName(form.getStudentName());
        validationUtils.checkForValidPassword(form.getPassword());
        validateSignUpOptionalData(form);
        return student;
    }
    private void enforceConstantEmail(StudentSignUpForm form) throws InputNotLogicallyValidException {
        if (form.getStudentEmail() != null){
            throw new InputNotLogicallyValidException(
                    "studentEmail",
                    "Email can't be adjusted once it's created !");
        }
    }
    public void validateDisabilities(StudentSignUpForm form) throws InputNotLogicallyValidException {
        if (form.getDisabilities() != null){
            validationUtils.checkForEmptyList("disabilities",form.getDisabilities());
            validationUtils.checkForListSize("disabilities",form.getDisabilities(),3);
            List<String> disabilitiesNames = new ArrayList<>();
            for (DisabilityHeader header: form.getDisabilities()){
                validationUtils.checkForValidDisabilityHeader(header);
                disabilitiesNames.add(header.getName());
            }
            validationUtils.checkForDuplicationInList("disabilities",disabilitiesNames);
        }
    }
    public void validateStudentSignUpForm(StudentSignUpForm form) throws InputNotLogicallyValidException {
        validateRequiredData(form);
        validateSignUpOptionalData(form);
    }
    public Student validateStudentUpdateForm(StudentSignUpForm form) throws InputNotLogicallyValidException, IllegalAccessException {
        return validateStudentUpdateData(form);
    }
    public void validateStudentHomeInputs(UUID next,
                                          UUID prev,
                                          int limit,
                                          String title,
                                          String tagName,
                                          String operation,
                                          String filter) throws InputNotLogicallyValidException {
        validationUtils.checkForValidOperationName(operation);
        validationUtils.checkForValidFilterName(filter);
        validationUtils.checkForEmptyAndBlankString("tag",tagName);
        validationUtils.checkForEmptyAndBlankString("title",title);
        validationUtils.checkForPositiveQuantity("limit",limit);
        if (next != null){
            validationUtils.checkBookIsExisted(next);
        }
        if (prev != null){
            validationUtils.checkBookIsExisted(prev);
        }
        if (next != null && prev != null){
            validationUtils.validateNextIsGreaterThanPrev(next,prev);
        }
    }
}
