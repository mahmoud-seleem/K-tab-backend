package com.example.Backend.controller;

import com.example.Backend.Repository.*;
import com.example.Backend.schema.*;
import com.example.Backend.security.JwtService;
import com.example.Backend.service.ChapterService;
import com.example.Backend.service.CommentService;
import com.example.Backend.validation.InputNotLogicallyValidException;
import com.example.Backend.validation.json.ValidJson;
import com.example.Backend.validation.json.ValidParam;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private ChapterRepository chapterRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private InteractionRepository interactionRepository;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private CommentService commentService;
    @Autowired
    private ChapterService chapterService;

    @PostMapping()
    public ChapterInfo saveNewChapter(HttpServletRequest request,
                                      @ValidJson("ChapterInfo") ChapterInfo chapterInfo) throws Exception {
        chapterInfo.setOwnerId(jwtService.getUserId(request));
        return chapterService.saveNewChapter(chapterInfo);
    }

    @PutMapping()
    public ChapterInfo updateChapterInfo(HttpServletRequest request, @ValidJson("ChapterInfo") ChapterInfo chapterInfo) throws Exception {
        chapterInfo.setOwnerId(jwtService.getUserId(request));
        return chapterService.updateChapterInfo(chapterInfo);
    }

    @GetMapping()
    public ChapterInfo getChapterInfo(@ValidParam UUID chapterId) throws InputNotLogicallyValidException {
        return chapterService.getChapterInfo(chapterId);
    }

    @PutMapping("chapter-orders/")
    public BookInfo updateChaptersOrders(HttpServletRequest request,@ValidJson("ChapterOrder") ChapterOrders chapterOrders) throws InputNotLogicallyValidException {
        return chapterService.updateChaptersOrder(
                jwtService.getUserId(request),
                chapterOrders.getBookId(),
                chapterOrders.getChapterOrders());
    }

    @PostMapping("comment/")
    public CommentInfo addNewComment(HttpServletRequest request
            , @ValidJson("CommentInfo") CommentInfo commentInfo) throws InputNotLogicallyValidException {
        if (jwtService.getUserType(request).equals("STUDENT")) {
            commentInfo.setCommenterType("STUDENT");
            commentInfo.setStudentId(
                    jwtService.getUserId(request));
        } else {
            commentInfo.setCommenterType("AUTHOR");
            commentInfo.setAuthorId(
                    jwtService.getUserId(request));
        }
        return commentService.addComment(commentInfo);
    }

    @GetMapping("comment/")
    public CommentInfo getCommentInfo(@ValidParam UUID commentId) throws InputNotLogicallyValidException {
        return commentService.getComment(commentId);
    }

    @DeleteMapping("comment/")
    public void deleteComment(HttpServletRequest request, @ValidParam UUID commentId) throws InputNotLogicallyValidException {
        commentService.deleteComment(
                jwtService.getUserId(request),
                commentId);
    }

    @PutMapping("comment/")
    public CommentInfo updateCommentInfo(HttpServletRequest request
            , @ValidJson("CommentInfo") CommentInfo commentInfo) throws InputNotLogicallyValidException {
        return commentService.updateComment(
                jwtService.getUserId(request),
                commentInfo);
    }

    @GetMapping("all-comments/")
    public List<CommentInfo> getAllChapterComments(@ValidParam UUID chapterId) throws InputNotLogicallyValidException {
        return commentService.getAllChapterComments(chapterId);
    }

    @GetMapping("/comment-page/")
    public CommentPage getPage(@ValidParam String operation,
                               @ValidParam String next,
                               @ValidParam String prev,
                               @ValidParam UUID chapterId,
                               @ValidParam int limit) throws InputNotLogicallyValidException {
        return commentService.getCommentPage(
                operation,
                next,
                prev,
                chapterId,
                limit);
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
