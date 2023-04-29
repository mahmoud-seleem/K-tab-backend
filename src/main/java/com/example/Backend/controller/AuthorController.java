package com.example.Backend.controller;

import com.example.Backend.Repository.AuthorCommentRepository;
import com.example.Backend.Repository.AuthorNotificationRepository;
import com.example.Backend.Repository.AuthorRepository;
import com.example.Backend.Repository.AuthorSettingsRepository;
import com.example.Backend.model.Author;
import com.example.Backend.model.AuthorComment;
import com.example.Backend.model.AuthorSettings;
import com.example.Backend.model.Book;
import com.example.Backend.s3Connection.S3fileSystem;
import com.example.Backend.schema.AuthorSignUpForm;
import com.example.Backend.schema.AuthorSignUpResponse;
import com.example.Backend.schema.SearchInput;
import com.example.Backend.service.AuthorService;
import jakarta.persistence.PersistenceUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AuthorCommentRepository authorCommentRepository;

    @Autowired
    private AuthorSettingsRepository authorSettingsRepository;

    @Autowired
    private AuthorNotificationRepository authorNotificationRepository;
    @Autowired
    private S3fileSystem s3fileSystem;

    @Autowired
    private AuthorService authorService;
    @GetMapping("/new")
    public Author saveNewAuthor(){
        Author author = new Author("mahmoud");
        Author a = authorRepository.save(author);
        return a;
    }
    @GetMapping("/getauthor/{id}")
    public Author getAuthor(@PathVariable UUID id){
        return authorRepository.findById(id).get();
    }

    @GetMapping("/newcomment/{id}")
    public AuthorComment saveNewAuthorComment(@PathVariable UUID id){
        AuthorComment authorComment = new AuthorComment("blablablablablabalbala");
        Author a = authorRepository.findById(id).get();
        a.addAuthorComment(authorComment);
        AuthorComment ac = authorCommentRepository.save(authorComment);
        return ac;
    }

    @GetMapping("/get/all")
    public List<Author> getAllAuthors(){
        return authorRepository.findAll();
    }

    @CrossOrigin
    @PostMapping
    public Author addNewAuthor(@RequestBody Author a){
        return authorRepository.save(a);
    }
    @GetMapping("/getcomments/{id}")
    public List<AuthorComment> getAllAuthorComments(@PathVariable UUID id){
        Author a = authorRepository.findById(id).get();
        return a.getAuthorCommentList();
    }
    @GetMapping("/getcommentauthor/{id}")
    public Author getCommentAuthor(@PathVariable UUID id){
        AuthorComment authorComment = authorCommentRepository.findById(id).get();
        return authorComment.getAuthor();
    }
    @GetMapping("/newsettings/{id}")
    public AuthorSettings newAuthorSettings(@PathVariable UUID id){
        Author author = authorRepository.findById(id).get();
        AuthorSettings authorSettings = new AuthorSettings();
        author.setAuthorSettings(authorSettings);
        return authorSettingsRepository.save(authorSettings);
    }
    @GetMapping("/getauthorsettings/{id}")
    public AuthorSettings getAuthorSettings(@PathVariable UUID id){
        Author a = authorRepository.findById(id).get();
        return a.getAuthorSettings();
    }
    @GetMapping("/getsettingsauthor/{id}")
    public Author getSettingsAuthor(@PathVariable UUID id){
        AuthorSettings a = authorSettingsRepository.findById(id).get();
        return a.getAuthor();
    }

    @PostMapping("/newauthor/")
    public Author createNewAuthor(@RequestBody Author author){
        return authorRepository.save(author);
    }

    @PostMapping("/encodeimg")
    public String encodeImg(@RequestParam MultipartFile image) throws IOException {
        //File file = new File("D:\\SBME_4\\Graduation_Project\\Platform_Backend\\profile_picture.jpg");
            // Reading a Image file from file system
            //FileInputStream imageInFile = (FileInputStream) image.getInputStream();
            //byte imageData[] = new byte[(int) ((File)image).length()];
            byte imageData[] = image.getBytes();
            //imageInFile.read(imageData);
            // Converting Image byte array into Base64 String
            return Base64.getEncoder().encodeToString(imageData);
    }
    @PostMapping("/signup/")
    public AuthorSignUpResponse saveSignUpData(@RequestBody AuthorSignUpForm authorSignUpForm) {
        AuthorSignUpResponse response = new AuthorSignUpResponse();
        try {
            response =  authorService.saveNewAuthor(authorSignUpForm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @PutMapping
    public AuthorSignUpResponse updateAuthorProfileInfo(@RequestBody AuthorSignUpForm authorSignUpForm) {
        AuthorSignUpResponse response = new AuthorSignUpResponse();
        try {
            response =  authorService.updateAuthorInfo(authorSignUpForm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    @GetMapping
    public AuthorSignUpResponse getAuthorInfo(@RequestBody AuthorSignUpForm form){
        return authorService.getAuthorInfo(form);
    }
    @GetMapping("books/headers/")
    public List<Map<String,Object>> getAuthorBooksHeaders(@RequestBody SearchInput input){
        List<Map<String,Object>> response = null;
        try {
            response = authorService.getAuthorBooksHeaders(input);
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }
//    @PostMapping("/img2/")
//    public String saveSignUpData2(@RequestBody AuthorSignUpForm authorSignUpForm) throws IOException {
//        File file = authorSignUpForm.convertImgToFile2();
//        System.out.println("hello");
//        return s3fileSystem.uploadProfilePhoto2("authorId",file);
//    }
}
