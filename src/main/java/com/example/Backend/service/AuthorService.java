package com.example.Backend.service;

import com.example.Backend.Repository.AuthorRepository;
import com.example.Backend.Repository.BookRepository;
import com.example.Backend.jsonConversion.BookHeaderSerializer;
import com.example.Backend.jsonConversion.JsonConverter;
import com.example.Backend.model.Author;
import com.example.Backend.model.Book;
import com.example.Backend.s3Connection.S3fileSystem;
import com.example.Backend.schema.AuthorSignUpForm;
import com.example.Backend.schema.AuthorSignUpResponse;
import com.example.Backend.schema.BookInfo;
import com.example.Backend.schema.SearchInput;
import com.example.Backend.utils.ImageConverter;
import com.example.Backend.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private JsonConverter jsonConverter;
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
    public List<Map<String,Object>> getAuthorBooksHeaders(SearchInput input) throws JsonProcessingException {
        List<Map<String,Object>> authorBooksHeaders = new ArrayList<>();
        ObjectMapper mapper = createCustomMapper();
        for(BookInfo bookInfo: getAuthorBooksHeadersInfo(input)){
            authorBooksHeaders.add(
                    jsonConverter.convertToEntityAttribute(
                            mapper.writeValueAsString(bookInfo)).toMap());
        }
        return authorBooksHeaders;
    }
    private List<BookInfo> getAuthorBooksHeadersInfo(SearchInput input){
        Author author  = authorRepository.findById(
                input.getAuthorId()
        ).get();
        return constructBooksHeadersInfo(author ,input.getTitle());
    }

    private List<BookInfo> constructBooksHeadersInfo(Author author,String title){
        List<BookInfo> bookInfoList = new ArrayList<>();
        List<Book> bookList = (title == null) ?
                author.getAuthorBooksList() :
                bookRepository.findByTitleContainingAndAuthor(
                        title,author);
        for(Book book: bookList){
            BookInfo bookInfo = new BookInfo();
            bookInfo.setBookId(book.getBookId());
            bookInfo.setTitle(book.getTitle());
            bookInfo.setBookCoverPath(book.getBookCover());
            bookInfoList.add(bookInfo);
        }
        return bookInfoList;
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
    private ObjectMapper createCustomMapper(){
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(BookInfo.class, new BookHeaderSerializer());
        mapper.registerModule(module);
        return mapper;
    }
}
