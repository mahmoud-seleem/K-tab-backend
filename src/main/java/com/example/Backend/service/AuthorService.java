package com.example.Backend.service;

import com.example.Backend.Repository.AuthorRepository;
import com.example.Backend.model.Author;
import com.example.Backend.s3Connection.S3fileSystem;
import com.example.Backend.schema.AuthorSignUpForm;
import com.example.Backend.schema.AuthorSignUpResponse;
import com.example.Backend.utils.ImageConverter;
import com.example.Backend.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.InputStream;
import java.lang.reflect.Field;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private S3fileSystem s3fileSystem;

    @Autowired
    private ImageConverter imageConverter;

    @Autowired
    private Utils utils;

    public AuthorSignUpResponse saveNewAuthor(AuthorSignUpForm form) throws Exception {
        Author author = createNewAuthor(form);
        return constructResponse(authorRepository.save(author));
    }

    public AuthorSignUpResponse updateAuthorInfo(AuthorSignUpForm form) throws Exception {
        Author author = authorRepository.findById(form.getAuthorId()).get();
        updateAuthorData(form, author);
        return constructResponse(authorRepository.save(author));
    }

    public AuthorSignUpResponse getAuthorInfo(AuthorSignUpForm form){
        return constructResponse(
                authorRepository.findById(
                        form.getAuthorId()
                ).get()
        );
    }
    private String storeProfilePhotoPath(Author author) {
        String photoPath = ("Authors/" + author.getAuthorId().toString() + "/profilePhoto.png");
        s3fileSystem.reserveEmptyPlace(photoPath);
        author.setProfilePhoto(photoPath);
        return photoPath;
    }

    private void setupProfilePhoto(Author author, AuthorSignUpForm form) {
        String photoPath = storeProfilePhotoPath(author);
        if (form.getProfilePhotoAsBinaryString() != null) {
            InputStream inputStream = imageConverter.convertImgToFile(
                    form.getProfilePhotoAsBinaryString());
            s3fileSystem.uploadPhoto(photoPath, inputStream);
        }
    }

    private Author createNewAuthor(AuthorSignUpForm form) throws Exception {
        Author author = authorRepository.save(new Author(
                form.getAuthorName(),
                form.getAuthorEmail(),
                form.getPassword()));
        return updateAuthorData(form, author);
    }

    private Author updateAuthorData(AuthorSignUpForm form, Author author) throws Exception {
        for (Field field : form.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.get(form) != null &&
                    field.getName() !=
                            "profilePhotoAsBinaryString") {
                utils.getMethodBySignature("set", field
                        , author, field.getType()).invoke(author, field.get(form));
            }
        }
        setupProfilePhoto(author,form);
        return author;
    }

    private AuthorSignUpResponse constructResponse(Author author) {
        return new AuthorSignUpResponse(
                author.getAuthorId(),
                author.getAuthorName(),
                author.getAuthorEmail(),
                author.getContact(),
                author.getProfilePhoto()
        );
    }
}
