package com.example.Backend.controller;


import com.example.Backend.Repository.*;
import com.example.Backend.model.Author;
import com.example.Backend.model.Book;
import com.example.Backend.model.Student;
import com.example.Backend.model.Tag;
import com.example.Backend.schema.*;
import com.example.Backend.security.JwtService;
import com.example.Backend.service.BookService;
import com.example.Backend.utils.Utils;
import com.example.Backend.validation.InputNotLogicallyValidException;
import com.example.Backend.validation.ValidationUtils;
import com.example.Backend.validation.json.ValidJson;
import com.example.Backend.validation.json.ValidParam;
import jakarta.servlet.http.HttpServletRequest;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.chrono.ChronoLocalDate;
import java.util.*;

@RestController
@CrossOrigin()
@RequestMapping("/book/")
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private AuthorController authorController;

    @Autowired
    private ChapterController chapterController;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private StudentController studentController;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private ContributionController contributionController;
    @Autowired
    private ValidationUtils validationUtils;
    @Autowired
    private PaymentController paymentController;
//    @GetMapping("/{id}")
//    public Book findBookById(@PathVariable UUID id){
//        return bookService.findBookById(id);
//    }

    @PostMapping()
    public BookInfo saveNewBook(HttpServletRequest request, @ValidJson("BookInfo") BookInfo bookInfo) throws Exception {
        bookInfo.setAuthorId(jwtService.getUserId(request));
        return bookService.saveNewBook(bookInfo);
    }

    @PostMapping("/random/")
    public String generateDumyData(HttpServletRequest request) throws Exception {
        UUID authorId = jwtService.getUserId(request);
        Author author = validationUtils.checkAuthorIsExisted(authorId);
        author.setAuthorName("Mahmoud Seleem");
        authorRepository.save(author);
        BookInfo bookInfo = new BookInfo();
        Random random = new Random();
        bookInfo.setTags(Arrays.asList("MATH", "AI"));
        bookInfo.setAuthorId(authorId);
        bookInfo.setBookAbstract("Master the math needed to excel in data science, machine learning. In this book author Thomas Nield guides you through areas like calculus, probability, linear algebra, and statistics and how they apply to techniques like neural networks.");
        for (int i = 0; i < 14; i++) {
            bookInfo.setPrice((double) (random.nextInt(200 - 50 + 1) + 50));
            bookInfo.setBookCoverPhotoAsBinaryString(
                    bookService.downloadAndEncodeImage(Utils.IMAGES[i]));
            bookInfo.setTitle(Utils.TITLES[i]);
            bookService.saveNewBook(bookInfo);
        }

        for (int i = 0; i < 3; i++) {
            AuthorSignUpForm form = new AuthorSignUpForm();
            form.setAuthorEmail(Utils.authorEmails[i]);
            form.setAuthorName(Utils.authorNames[i]);
            form.setPassword(Utils.authorPasswords[i]);
            form.setProfilePhotoAsBinaryString(bookService.downloadAndEncodeImage(
                    Utils.authorPhotos[i]));
            authorController.saveSignUpData(
                    form);
        }
        List<Author> authors = authorRepository.findAll();
        authors.remove(author);
        for (Book book : bookRepository.findAll()) {
            ChapterInfo chapterInfo = new ChapterInfo();
            chapterInfo.setBookId(book.getBookId());
            chapterInfo.setOwnerId(authorId);
            chapterInfo.setTitle("Chapter1");
            ChapterInfo chapterRes = chapterController.saveNewChapter(chapterInfo);
            System.out.println(chapterRes.getChapterId());
        }
        StudentSignUpForm form = new StudentSignUpForm();
        form.setStudentEmail(Utils.studentEmail);
        form.setStudentName(Utils.studentName);
        form.setPassword(Utils.studentPassword);
        form.setProfilePhotoAsBinaryString(
                bookService.downloadAndEncodeImage(
                        Utils.studentImage
                ));
        studentController.saveSignUpData(form);

        for (String tagName : Utils.TagsNames){
            Tag tag = new Tag(tagName);
            tagRepository.save(tag);
        }
        return "DONE";
    }

    @PostMapping("/random-c/")
    public String randomCont(HttpServletRequest request) throws Exception {
        UUID authorId = jwtService.getUserId(request);
        List<Author> authors = authorRepository.findAll();
        Author author = validationUtils.checkAuthorIsExisted(authorId);
        authors.remove(author);
        for (Book book : bookRepository.findAll()) {
            UUID chapterId = bookService.getBookInfo(book.getBookId()).getChapterHeaders().get(0).getChapterId();
            for (Author author1 : authors) {
                ContributionInfo contributionInfo = new ContributionInfo();
                contributionInfo.setOwnerId(authorId);
                contributionInfo.setBookId(book.getBookId());
                contributionInfo.setContributorId(author1.getAuthorId());
                contributionInfo.setChaptersIds(
                        Arrays.asList(chapterId.toString()));
                contributionController.addContributionWithoutRes(contributionInfo);
            }
        }
        Student student = studentRepository.findByName("7amada");
        for (Book book : bookRepository.findAll()){
            paymentController.buyBook(book.getBookId(),student.getStudentId());
        }
        return "DONE";
    }

//    @PostMapping("/random-r/")
//    public String makeRandomRatings() throws InputNotLogicallyValidException {
//        Student student = studentRepository.findByStudentName(Utils.studentName);
//        for (Book book : bookRepository.findAll()){
//            paymentController.buyBook(book.getBookId(),student.getStudentId());
//        }
//        return "DONE";
//    }

    @PutMapping()
    public BookInfo updateBookInfo(HttpServletRequest request, @ValidJson("BookInfo") BookInfo bookInfo) throws Exception {
        bookInfo.setAuthorId(jwtService.getUserId(request));
        return bookService.updateBookInfo(bookInfo);
    }

    @GetMapping()
    public BookInfo getBookInfo(HttpServletRequest request, @ValidParam UUID bookId) throws InputNotLogicallyValidException {
        if (jwtService.getUserType(request).equals("AUTHOR")) {
            return bookService.getBookInfo(bookId);
        } else {
            return bookService.getStudentBookInfo(jwtService.getUserId(request), bookId);
        }

    }

    @PostMapping("rating/")
    public StudentBookInfo addRatingValue(HttpServletRequest request,
                                          @ValidParam UUID bookId,
                                          @ValidParam int rating) throws InputNotLogicallyValidException {
        return bookService.addRatingValue(
                jwtService.getUserId(request),
                bookId, rating
        );
    }

    @GetMapping("/all/")
    public List<UUID> getAllBookIds() {
        return bookService.getAllBookIds();
    }

    @GetMapping("/all-with-tagName/")
    public List<Map<String, Object>> getAllBooksWithTagName(@RequestParam String tagName) {
        return bookService.getAllBookWithTagName(tagName);
    }

    @GetMapping("/all-with-title/")
    public List<Map<String, Object>> getAllBooksWithTitle(@RequestParam String title) {
        return bookService.getAllBookWithTitle(title);
    }

    @GetMapping("/all-with-tagAndTitle/")
    public List<Map<String, Object>> getAllBooksWithTagNameAndTitle(@RequestParam String tagName,
                                                                    @RequestParam String title) {
        return bookService.getAllBookWithTitleAndTag(title, tagName);
    }

    @GetMapping("/all-with-tagOrTitle/")
    public List<Map<String, Object>> getAllBooksWithTagNameOrTitle(@RequestParam String tagName,
                                                                   @RequestParam String title) {
        return bookService.getAllBookWithTitleOrTag(title, tagName);
    }

    @GetMapping("tags/")
    public List<String> getAllBookTags() {
        return bookService.getAllBookTags();
    }
}