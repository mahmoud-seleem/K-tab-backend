package com.example.Backend.service;

import com.example.Backend.Repository.AuthorRepository;
import com.example.Backend.Repository.AuthorSettingsRepository;
import com.example.Backend.model.Author;
import com.example.Backend.model.AuthorSettings;
import com.example.Backend.schema.AuthorSettingsForm;
import com.example.Backend.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
public class AuthorSettingsService {

    @Autowired
    private AuthorSettingsRepository authorSettingsRepository;
    @Autowired
    private Utils utils;

    @Autowired
    private AuthorRepository authorRepository;
    public AuthorSettingsForm setAuthorSettingsInfo(AuthorSettingsForm form) throws Exception{
        AuthorSettings settings = createNewAuthorSettings();
        updateAuthorSettings(settings,form);
        connectAuthorAndSettings(settings,form);
        return createResponse(settings);
    }
    public AuthorSettingsForm updateAuthorSettingsInfo(AuthorSettingsForm form) throws Exception{
        AuthorSettings settings = getAuthorSettingsObject(form);
        updateAuthorSettings(settings,form);
        connectAuthorAndSettings(settings,form);
        return createResponse(settings);
    }

    public AuthorSettingsForm getAuthorSettingsInfo(AuthorSettingsForm form) throws Exception {
        AuthorSettings settings = getAuthorSettingsObject(form);
        return createResponse(settings);
    }
    private AuthorSettingsForm createResponse(AuthorSettings settings){
        return new AuthorSettingsForm(
                settings.getAuthor().getAuthorId(),
                settings.getAuthorSettingsId(),
                settings.getBrightnessLevel(),
                settings.getContrastLevel(),
                settings.getFontSize(),
                settings.getFontStyle(),
                settings.getInvertColor(),
                settings.getGrayScale()
        );
    }
    private AuthorSettings createNewAuthorSettings(){
        return authorSettingsRepository.save(new AuthorSettings());
    }
    private AuthorSettings getAuthorSettingsObject(AuthorSettingsForm form){
        return authorRepository.findById(
                form.getAuthorId()).get().getAuthorSettings();
    }
    private void connectAuthorAndSettings(AuthorSettings settings,AuthorSettingsForm form){
        Author author = authorRepository.findById(form.getAuthorId()).get();
        author.setAuthorSettings(settings);
        authorRepository.save(author);
        authorSettingsRepository.save(settings);
    }
    private void updateAuthorSettings(AuthorSettings settings,AuthorSettingsForm form) throws Exception{
        for (Field field : form.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.get(form) != null && field.getName() != "authorId"){
                utils.getMethodBySignature("set", field
                        ,settings, field.getType()).invoke(settings, field.get(form));
            }
        }
    }
}
