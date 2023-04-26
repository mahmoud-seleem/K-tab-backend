package com.example.Backend.service;

import com.example.Backend.Repository.AuthorRepository;
import com.example.Backend.model.Author;
import com.example.Backend.s3Connection.S3fileSystem;
import com.example.Backend.schema.AuthorSignUpForm;
import com.example.Backend.schema.AuthorSignUpResponse;
import com.example.Backend.utils.ImageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.InputStream;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private S3fileSystem s3fileSystem;

    @Autowired
    private ImageConverter imageConverter;

    public AuthorSignUpResponse saveNewAuthor(AuthorSignUpForm form){
        Author author = createNewAuthor(form);
        authorRepository.save(author);
        String photoPath = ("Authors/"+author.getAuthorId().toString()+"/profilePhoto.png");
        author.setProfilePhoto(photoPath);
        InputStream inputStream = imageConverter.convertImgToFile(
                form.getProfilePhotoAsBinaryString());
        s3fileSystem.uploadPhoto(photoPath,inputStream);
        authorRepository.save(author);
        return constructResponse(author);
    }

    private Author createNewAuthor(AuthorSignUpForm form){
        return new Author(
                form.getAuthorName(),
                form.getAuthorEmail(),
                form.getPassword(),
                form.getContact()
        );
    }
    private AuthorSignUpResponse constructResponse(Author author){
        return new AuthorSignUpResponse(
                author.getAuthorId(),
                author.getAuthorName(),
                author.getAuthorEmail(),
                author.getContact(),
                author.getProfilePhoto()
        );
    }
}
