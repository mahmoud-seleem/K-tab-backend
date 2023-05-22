package com.example.Backend.controller;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.example.Backend.Repository.*;
import com.example.Backend.jsonConversion.JsonToMapConverter;
import com.example.Backend.model.*;
import com.example.Backend.schema.BookInfo;
import com.example.Backend.schema.ChapterInfo;
import com.example.Backend.security.JwtService;
import com.example.Backend.service.ChapterService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Instant;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/chapter/")
public class ChapterController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private StudentCommentRepository studentCommentRepository;
    @Autowired
    private AuthorCommentRepository authorCommentRepository;
    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private InteractionRepository interactionRepository;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private ChapterService chapterService;
    @PostMapping()
    public ChapterInfo saveNewChapter(HttpServletRequest request, @RequestBody ChapterInfo chapterInfo){
        chapterInfo.setOwnerId(jwtService.getUserId(request));
        ChapterInfo response = new ChapterInfo();
        try {
            response = chapterService.saveNewChapter(chapterInfo);
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }
    @PutMapping()
    public ChapterInfo updateChapterInfo(HttpServletRequest request,@RequestBody ChapterInfo chapterInfo) throws Exception {
        chapterInfo.setOwnerId(jwtService.getUserId(request));
        ChapterInfo  response = new ChapterInfo();
        try {
            response = chapterService.updateChapterInfo(chapterInfo);
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    @GetMapping()
    public ChapterInfo getChapterInfo(@RequestParam UUID chapterId){
        return chapterService.getChapterInfo(chapterId);
}
}
//
//    @Value("${ACCESS_KEY}")
//    private  String ACCESS_KEY;
//
//    @Value("${SECRET_KEY}")
//    private  String SECRET_KEY;
//
//    @Value("${REGION}")
//    private  String REGION;
//
//    @Value("${BUCKET_NAME}")
//    private  String BUCKET_NAME;
//    @GetMapping("/newchapter")
//    public Chapter creatNewChapter(){
//        Book book = bookRepository.findByTitle("JAVA");
//        Chapter chapter = new Chapter("chapter one");
//        book.addChapter(chapter);
//        return  chapterRepository.save(chapter);
//    }
//    @GetMapping("/newbook")
//    public Book creatNewBook(){
//        //Chapter chapter = new Chapter("chapter one");
//        Book book = new Book("JAVA");
//        return  bookRepository.save(book);
//    }
//    @GetMapping("/newstudent")
//    public Student creatNewStudent(){
//        Student student = new Student("mahmoud");
//        return (studentRepository.save(student));
//    }
//
//    @GetMapping("/newauthor")
//    public Author creatNewAuthor(){
//        Author author = new Author("mohamed");
//        return (authorRepository.save(author));
//    }
//    @GetMapping("/getallstudents")
//    public List<Student> getAllStudents(){
//        return studentRepository.findAll();
//    }
//
//    @GetMapping("/getallauthors")
//    public List<Author> getAllAuthors(){
//        return authorRepository.findAll();
//    }
//
//    @GetMapping("/getallchapters")
//    public List<Chapter> getAllChapters() {return chapterRepository.findAll();}
//
//    @GetMapping("/getallbooks")
//    public List<Book> getAllBooks(){return bookRepository.findAll();}
//
//    @GetMapping("/getbookchapters")
//    public List<Chapter> getBookChapters(){
//        return bookRepository.findByTitle("JAVA").getChapters();
//    }
//    @GetMapping("/getchapterbook")
//    public Book getChapterBook(){
//        return chapterRepository.findByTitle("chapter one").getBook();
//    }
//    @GetMapping("/newauthorcomment/{id}")
//    public AuthorComment createNewAuthorComment(@PathVariable UUID id){
//        AuthorComment authorComment = new AuthorComment("blablabla");
//        chapterRepository.findById(id).get().addAuthorComment(authorComment);
//        authorCommentRepository.save(authorComment);
//        return authorComment;
//    }
//    @GetMapping("/newstudentcomment/{id}")
//    public StudentComment createNewStudentComment(@PathVariable UUID id){
//        StudentComment studentComment = new StudentComment("blablablablabla");
//        chapterRepository.findById(id).get().addStudentComment(studentComment);
//        studentCommentRepository.save(studentComment);
//        //studentRepository.findBy()
//        return studentComment;
//    }
//    public URL generatePreSignedForReadOnly(AmazonS3 amazonS3,String fileName){
//        java.util.Date expiration = new java.util.Date();
//        long expTimeMillis = Instant.now().toEpochMilli();
//        expTimeMillis += 1000 * 0.5 * 60;
//        expiration.setTime(expTimeMillis);
//        GeneratePresignedUrlRequest generatePresignedUrlRequest =
//                new GeneratePresignedUrlRequest(BUCKET_NAME, fileName)
//                        .withMethod(HttpMethod.GET)
//                        .withExpiration(expiration);
//        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
//        return url;
//    }
//    @GetMapping("/s3")
//    public String s3() throws URISyntaxException {
//        AWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY,SECRET_KEY);
//        AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard().withRegion(REGION).withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
//        //amazonS3.putObject("content-pwd-aat","test/img.png",new File("C:\\Users\\Mahmoud Seleem\\OneDrive\\Pictures\\killua_zoldyck___hunter_x_hunter_by_klydetheslayer_d8ggzlo.png"));
////        amazonS3.deleteObjects(  )
//        return generatePreSignedForWrite(amazonS3,"test/temp.png").toString();
//    }
//
//    public URL generatePreSignedForWrite(AmazonS3 amazonS3,String fileName){
//        java.util.Date expiration = new java.util.Date();
//        long expTimeMillis = Instant.now().toEpochMilli();
//        expTimeMillis += 1000 * 2 * 60;
//        expiration.setTime(expTimeMillis);
//        GeneratePresignedUrlRequest generatePresignedUrlRequest =
//                new GeneratePresignedUrlRequest(BUCKET_NAME, fileName)
//                        .withMethod(HttpMethod.PUT)
//                        .withExpiration(expiration);
//        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
//        return url;
//    }
