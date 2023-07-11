package com.example.Backend.service;

import com.example.Backend.Repository.StudentRepository;
import com.example.Backend.Repository.StudentSettingsRepository;
import com.example.Backend.model.Author;
import com.example.Backend.model.AuthorSettings;
import com.example.Backend.model.StudentSettings;
import com.example.Backend.schema.SettingsElement;
import com.example.Backend.schema.SettingsResponse;
import com.example.Backend.schema.SettingsForm;
import com.example.Backend.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class StudentSettingsService {

    @Autowired
    private StudentSettingsRepository studentSettingsRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private Utils utils;
    public SettingsResponse updateStudentSettingsInfo(SettingsForm form) throws Exception{
        StudentSettings settings = getStudentSettingsObject(form.getStudentId());
        updateStudentSettings(settings,form);
//        connectAuthorAndSettings(settings,form);
        return createResponse(
                studentRepository.
                        save(settings.getStudent())
                        .getStudentSettings()
        );
    }

    public SettingsResponse getStudentSettingsInfo(UUID studentId) throws Exception {
        StudentSettings settings = getStudentSettingsObject(studentId);
        return createResponse(settings);
    }
    private StudentSettings getStudentSettingsObject(UUID studentId){
        return studentRepository.findById(
                studentId).get().getStudentSettings();
    }

    private StudentSettings updateStudentSettings(StudentSettings settings,SettingsForm form) throws Exception{
        for (Field field : form.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.get(form) != null && field.getName() != "studentId"){
                utils.getMethodBySignature("set", field
                        ,settings, field.getType()).invoke(settings, field.get(form));
            }
        }
        return studentSettingsRepository.save(settings);
    }

    private SettingsResponse createResponse(StudentSettings settings) throws IllegalAccessException {
        return new SettingsResponse(createSettingsArray(settings));
    }
    private List<SettingsElement> createSettingsArray(StudentSettings settings) throws IllegalAccessException {
        List<SettingsElement> elements = new ArrayList<>();
        for (Field field : settings.getClass().getDeclaredFields()) {
            if (field.getName() != "student") {
                field.setAccessible(true);
                elements.add(createSettingsElement(settings,field));
            }
        }
        return elements;
    }

    private SettingsElement createSettingsElement(StudentSettings settings,Field field) throws IllegalAccessException {
        String[] classNames = field.getType().getName().split("\\.");
        return new SettingsElement(
                field.getName(),
                classNames[classNames.length - 1],
                field.get(settings)
        );
    }



}

    //    private AuthorSettings createNewAuthorSettings(){
//        return authorSettingsRepository.save(new AuthorSettings());
//    }
//    private void connectAuthorAndSettings(AuthorSettings settings,AuthorSettingsForm form){
//        Author author = authorRepository.findById(form.getAuthorId()).get();
//        authorSettingsRepository.delete(author.getAuthorSettings());
//        author.setAuthorSettings(settings);
//        authorRepository.save(author);
//        authorSettingsRepository.save(settings);
//    }
