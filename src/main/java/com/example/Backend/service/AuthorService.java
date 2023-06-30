package com.example.Backend.service;

import com.example.Backend.Repository.AuthorRepository;
import com.example.Backend.Repository.AuthorSettingsRepository;
import com.example.Backend.Repository.BookRepository;
import com.example.Backend.jsonConversion.BookHeaderSerializer;
import com.example.Backend.jsonConversion.JsonConverter;
import com.example.Backend.model.Author;
import com.example.Backend.model.AuthorSettings;
import com.example.Backend.model.Book;
import com.example.Backend.s3Connection.S3fileSystem;
import com.example.Backend.schema.AuthorSignUpForm;
import com.example.Backend.schema.AuthorSignUpResponse;
import com.example.Backend.schema.BookInfo;
import com.example.Backend.schema.SearchInput;
import com.example.Backend.security.AppUserDetails;
import com.example.Backend.security.AppUserDetailsService;
import com.example.Backend.security.JwtService;
import com.example.Backend.utils.ImageConverter;
import com.example.Backend.utils.Utils;
import com.example.Backend.validation.InputNotLogicallyValidException;
import com.example.Backend.validation.ValidationUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

@Service
public class AuthorService {
    @Autowired
    private ValidationUtils validationUtils;
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorSettingsRepository authorSettingsRepository;
    @Autowired
    private S3fileSystem s3fileSystem;

    @Autowired
    private ImageConverter imageConverter;

    @Autowired
    private Utils utils;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private JsonConverter jsonConverter;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AppUserDetailsService appUserDetailsService;

    public AuthorSignUpResponse saveNewAuthor(AuthorSignUpForm form) throws Exception {
        Author author = createNewAuthor(form);
        String jwtToken = authenticateNewAuthor(form);
        return constructResponse(author, jwtToken);
    }

    private String authenticateNewAuthor(AuthorSignUpForm form) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        form.getAuthorEmail(),
                        form.getPassword()
                )
        );
        return jwtService.generateJwtToken(authentication);
    }

    public AuthorSignUpResponse updateAuthorInfo(AuthorSignUpForm form) throws Exception {
        Author author = authorRepository.findById(form.getAuthorId()).get();
        updateAuthorData(form, author);
        return constructResponse(authorRepository.save(author));
    }

    public AuthorSignUpResponse getAuthorInfo(UUID authorId) {
        return constructResponse(
                authorRepository
                        .findById(authorId)
                        .get()
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
        Author author = new Author(
                form.getAuthorName(), // required
                form.getAuthorEmail(), // required
                passwordEncoder.encode(form.getPassword())); // required
        return updateAuthorData(form,
                createDefaultSettings(author));
    }

    private Author createDefaultSettings(Author author) {
        AuthorSettings authorSettings = new AuthorSettings();
        authorSettingsRepository.save(authorSettings);
        author.setAuthorSettings(authorSettings);
        return authorRepository.save(author);
    }

    private Author updateAuthorData(AuthorSignUpForm form, Author author) throws Exception {
        List<Field> fields = new ArrayList<>();
        fields = utils.getAllFields(fields, AuthorSignUpForm.class);
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.get(form) != null &&
                    field.getName() !=
                            "profilePhotoAsBinaryString" &&
                    field.getName() != "password") {
                utils.getMethodBySignature("set", field
                        , author, field.getType()).invoke(author, field.get(form));
            }
        }
        updatePassword(form, author);
        setupProfilePhoto(author, form);
        return authorRepository.save(author);
    }

    public List<Map<String, Object>> getAuthorBooksHeaders(UUID authorId) throws JsonProcessingException {
        List<Map<String, Object>> authorBooksHeaders = new ArrayList<>();
        ObjectMapper mapper = createCustomMapper();
        for (BookInfo bookInfo : getAuthorBooksHeadersInfo(authorId)) {
            authorBooksHeaders.add(
                    jsonConverter.convertToEntityAttribute(
                            mapper.writeValueAsString(bookInfo)).toMap());
        }
        return authorBooksHeaders;
    }

    public List<BookInfo> getAuthorBooksHeaders2(UUID authorId) throws JsonProcessingException {
        return new ArrayList<>(getAuthorBooksHeadersInfo(authorId));
    }

    private List<BookInfo> getAuthorBooksHeadersInfo(UUID authorId) {
        Author author = authorRepository.findById(authorId).get();
        return constructBooksHeadersInfo(author);
    }

    private List<BookInfo> constructBooksHeadersInfo(Author author) {
        List<BookInfo> bookInfoList = new ArrayList<>();
        List<Book> bookList = author.getAuthorBooksList();
        bookList.addAll(author.getContributionsBooks());
        for (Book book : bookList) {
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
                author.getProfilePhoto(),
                author.getAuthorSettings().getAuthorSettingsId(),
                null
        );
    }

    private AuthorSignUpResponse constructResponse(Author author, String jwtToken) {
        return new AuthorSignUpResponse(
                author.getAuthorId(),
                author.getAuthorName(),
                author.getAuthorEmail(),
                author.getContact(),
                author.getProfilePhoto(),
                author.getAuthorSettings().getAuthorSettingsId(),
                jwtToken
        );
    }

    private ObjectMapper createCustomMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(BookInfo.class, new BookHeaderSerializer());
        mapper.registerModule(module);
        return mapper;
    }

    private void updatePassword(AuthorSignUpForm form, Author author) {
        if (form.getPassword() != null) {
            author.setPassword(
                    passwordEncoder
                            .encode(form.getPassword()));
        }
    }

    private void ValidateAuthorRequiredData(AuthorSignUpForm form) throws InputNotLogicallyValidException {

    }
}
