package com.example.Backend.controller;

import com.example.Backend.Repository.*;
import com.example.Backend.schema.*;
import com.example.Backend.security.JwtService;
import com.example.Backend.service.ChapterService;
import com.example.Backend.service.CommentService;
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
                                      @ValidJson("ChapterInfo") ChapterInfo chapterInfo) {
        chapterInfo.setOwnerId(jwtService.getUserId(request));
        ChapterInfo response = new ChapterInfo();
        try {
            response = chapterService.saveNewChapter(chapterInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @PutMapping()
    public ChapterInfo updateChapterInfo(HttpServletRequest request, @ValidJson("ChapterInfo") ChapterInfo chapterInfo) throws Exception {
        chapterInfo.setOwnerId(jwtService.getUserId(request));
        ChapterInfo response = new ChapterInfo();
        try {
            response = chapterService.updateChapterInfo(chapterInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @GetMapping()
    public ChapterInfo getChapterInfo(@ValidParam UUID chapterId) {
        return chapterService.getChapterInfo(chapterId);
    }


    @PostMapping("comment/")
    public CommentInfo addNewComment(HttpServletRequest request
            , @ValidJson("CommentInfo") CommentInfo commentInfo) {
        if (jwtService.getUserType(request).equals("STUDENT")) {
            commentInfo.setCommenterType("STUDENT");
            commentInfo.setStudentId(
                    jwtService.getUserId(request));
        } else {
            commentInfo.setCommenterType("ADMIN");
            commentInfo.setAuthorId(
                    jwtService.getUserId(request));
        }
        return commentService.addComment(commentInfo);
    }

    @GetMapping("comment/")
    public CommentInfo getCommentInfo(@ValidParam UUID commentId) {
        return commentService.getComment(commentId);
    }

    @DeleteMapping("comment/")
    public void deleteComment(HttpServletRequest request, @RequestParam UUID commentId) {
        commentService.deleteComment(
                jwtService.getUserId(request),
                commentId);
    }

    @PutMapping("comment/")
    public CommentInfo updateCommentInfo(HttpServletRequest request
            , @ValidJson("CommentInfo") CommentInfo commentInfo) {
        return commentService.updateComment(
                jwtService.getUserId(request),
                commentInfo);
    }

    @GetMapping("all-comments/")
    public List<CommentInfo> getAllChapterComments(@ValidParam UUID chapterId) {
        return commentService.getAllChapterComments(chapterId);
    }

    @GetMapping("/page/")
    public CommentPage getPage(@RequestBody Map<String, Object> body) {
        if (((String) body.get("op")).equals("next")) {
            return commentService.getNextPage(
                    UUID.fromString((String) body.get("chapterId")),
                    (String) body.get("next"),
                    (String) body.get("prev"),
                    (int) body.get("limit"));
        } else {
            return commentService.getPrevPage(
                    UUID.fromString((String) body.get("chapterId")),
                    (String) body.get("next"),
                    (String) body.get("prev"),
                    (int) body.get("limit"));
        }
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
