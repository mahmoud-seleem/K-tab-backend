package com.example.Backend.service;

import com.example.Backend.Repository.AuthorRepository;
import com.example.Backend.Repository.AuthorSettingsRepository;
import com.example.Backend.model.Author;
import com.example.Backend.model.AuthorSettings;
import com.example.Backend.model.StudentSettings;
import com.example.Backend.schema.SettingsElement;
import com.example.Backend.schema.SettingsForm;
import com.example.Backend.schema.SettingsResponse;
import com.example.Backend.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AuthorSettingsService {

    @Autowired
    private AuthorSettingsRepository authorSettingsRepository;
    @Autowired
    private Utils utils;

    @Autowired
    private AuthorRepository authorRepository;
    public SettingsResponse setAuthorSettingsInfo(SettingsForm form) throws Exception{
        AuthorSettings settings = createNewAuthorSettings();
        updateAuthorSettings(settings,form);
        connectAuthorAndSettings(settings,form);
        return createResponse(settings);
    }
    public SettingsResponse updateAuthorSettingsInfo(SettingsForm form) throws Exception{
        AuthorSettings settings = getAuthorSettingsObject(form.getAuthorId());
        updateAuthorSettings(settings,form);
//        connectAuthorAndSettings(settings,form);
        return createResponse(
                authorRepository.
                        save(settings.getAuthor())
                        .getAuthorSettings()
        );
    }

    public SettingsResponse getAuthorSettingsInfo(UUID authorId) throws Exception {
        AuthorSettings settings = getAuthorSettingsObject(authorId);
        return createResponse(settings);
    }
    private SettingsResponse createResponse(AuthorSettings settings) throws IllegalAccessException {
        return new SettingsResponse(createSettingsArray(settings));
    }
    private List<SettingsElement> createSettingsArray(AuthorSettings settings) throws IllegalAccessException {
        List<SettingsElement> elements = new ArrayList<>();
        for (Field field : settings.getClass().getDeclaredFields()) {
            if (field.getName() != "author"){
                field.setAccessible(true);
                elements.add(createSettingsElement(settings,field));
            }
        }
        return elements;
    }

    private SettingsElement createSettingsElement(AuthorSettings settings,Field field) throws IllegalAccessException {
        String[] classNames = field.getType().getName().split("\\.");
        return new SettingsElement(
                field.getName(),
                classNames[classNames.length - 1],
                field.get(settings)
        );
    }

    private AuthorSettings createNewAuthorSettings(){
        return authorSettingsRepository.save(new AuthorSettings());
    }
    private AuthorSettings getAuthorSettingsObject(UUID authorId){
        return authorRepository.findById(
                authorId).get().getAuthorSettings();
    }
    private void connectAuthorAndSettings(AuthorSettings settings,SettingsForm form){
        Author author = authorRepository.findById(form.getAuthorId()).get();
        authorSettingsRepository.delete(author.getAuthorSettings());
        author.setAuthorSettings(settings);
        authorRepository.save(author);
        authorSettingsRepository.save(settings);
    }
    private AuthorSettings updateAuthorSettings(AuthorSettings settings, SettingsForm form) throws Exception{
        for (Field field : form.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.get(form) != null && field.getName() != "authorId"){
                utils.getMethodBySignature("set", field
                        ,settings, field.getType()).invoke(settings, field.get(form));
            }
        }
        return authorSettingsRepository.save(settings);
    }
}
