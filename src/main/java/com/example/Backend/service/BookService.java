package com.example.Backend.service;

import com.example.Backend.Repository.AuthorRepository;
import com.example.Backend.Repository.BookRepository;
import com.example.Backend.Repository.TagRepository;
import com.example.Backend.model.*;
import com.example.Backend.s3Connection.AccessType;
import com.example.Backend.s3Connection.S3PreSignedURL;
import com.example.Backend.s3Connection.S3fileSystem;
import com.example.Backend.schema.BookInfo;
import com.example.Backend.utils.ImageConverter;
import com.example.Backend.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ImageConverter imageConverter;

    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private S3fileSystem s3fileSystem;
    @Autowired
    private Utils utils;

    @Autowired
    private S3PreSignedURL s3PreSignedURL;

    public BookInfo saveNewBook(BookInfo bookInfo) throws Exception {
        Book book = createNewBook(bookInfo);
        Author author = authorRepository.findById(bookInfo.getAuthorId()).get();
        author.addBook(book);
        authorRepository.save(author);
        return createBookInfoResponse(
                bookRepository.save(updateLastEditDate(book)));
    }

    public BookInfo updateBookInfo(BookInfo bookInfo) throws Exception {
        Book book = bookRepository.findById(bookInfo.getBookId()).get();
        updateBookData(book, bookInfo);
        return createBookInfoResponse(
                bookRepository.save(updateLastEditDate(book)));
    }

    public BookInfo getBookInfo(UUID bookId) {
        return createBookInfoResponse(
                bookRepository.findById(bookId).get()
        );
    }

    private Book createNewBook(BookInfo bookInfo) throws Exception {
        Book book = new Book(
                bookInfo.getTitle(),
                bookInfo.getPrice(),
                bookInfo.getBookAbstract()
        );
        bookRepository.save(book);
        updateBookData(book, bookInfo);
        return book;
    }
    public String getPreSignedAsString(String bookCoverPath) {
        return s3PreSignedURL.generatePreSignedUrl(
                bookCoverPath,
                60,
                AccessType.READ
        ).toString();
    }

    private void updateBookData(Book book, BookInfo bookInfo) throws Exception {
        for (Field field : bookInfo.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.get(bookInfo) != null) {
                System.out.println(field.getName());
                switch (field.getName()) {
                    case "tags": {
                        setupBookTags(book, bookInfo.getTags());
                        break;
                    }
                    case "publishDate":
                    case "lastEditDate": {
                        utils.getMethodBySignature("set", field, book, LocalDateTime.class)
                                .invoke(book, LocalDateTime.parse(field.get(bookInfo).toString(), Utils.formatter));
                        break;
                    }
                    case "authorId": {
                        book.setAuthor(authorRepository.findById(bookInfo.getAuthorId()).get());
                        break;
                    }
                    case "bookCoverPhotoAsBinaryString": {
                        break;
                    }
                    default: {
                        utils.getMethodBySignature("set", field, book, field.getType())
                                .invoke(book, field.get(bookInfo));
                    }
                }
            }
        }
        setupBookCover(book, bookInfo);
    }

    private BookInfo createBookInfoResponse(Book book) {
        BookInfo response = new BookInfo(
                book.getAuthor().getAuthorId(),
                book.getBookId(),
                book.getTitle(),
                null,
                book.getBookAbstract(),
                book.getTagsNames(),
                book.getBookCover(),
                book.getPublishDateAsString(),
                book.getLastEditDateAsString(),
                book.getPrice(),
                book.calculateAvgRating(),
                book.getChaptersTitles(),
                book.getContributorsEmails());
        return response;
    }

    private Book updateLastEditDate(Book book) {
        book.setLastEditDate(LocalDateTime.parse(LocalDateTime.now().
                format(Utils.formatter), Utils.formatter));
        return book;
    }

    private String setupBookCover(Book book, BookInfo bookInfo) {
        String photoPath = storeCoverPhotoPath(book);
        if (bookInfo.getBookCoverPhotoAsBinaryString() != null) {
            InputStream inputStream = imageConverter.convertImgToFile(
                    bookInfo.getBookCoverPhotoAsBinaryString());
            s3fileSystem.uploadPhoto(photoPath, inputStream);
        }
        return photoPath;
    }

    private String storeCoverPhotoPath(Book book) {
        String photoPath = ("Books/" + book.getBookId().toString() + "/coverPhoto.png");
        s3fileSystem.reserveEmptyPlace(photoPath);
        book.setBookCover(photoPath);
        return photoPath;
    }

    private Book setupBookTags(Book book, List<String> tags) {
        book.clearTags();
        Tag tag = null;
        for (String tagName : tags) {
            if (tagRepository.findByName(tagName).isEmpty()) {
                tag = tagRepository.save(new Tag(tagName));
            } else {
                tag = tagRepository.findByName(tagName).get();
                tag.removeBook(book);
            }
            tag.addBook(book);
        }
        tagRepository.save(tag);
        Book b = bookRepository.save(book);
        Tag newTag = tagRepository.findByName(tag.getTagName()).get();
        System.out.println("____________________________________________");
        for (Tag t : b.getTags()) {
            System.out.println(t.getTagName());
        }
        System.out.println("____________________________________________");
        for (Book bb : newTag.getBookList()) {
            System.out.println(bb.getTitle());
        }
        System.out.println("____________________________________________");
        return b;
    }

}
//    public Book findBookById(UUID id) {
//        return bookRepository.findById(id).orElseThrow();
//    }
//    public Book insertSpecificBook(){
//        Book book = new Book("Mariam's fav book");
//        Student student = new Student("Mariam");
////        Rating rating = new Rating(book, student, 5);
////        Set<Rating> ratingSet = new HashSet<Rating>();
////        ratingSet.add(rating);
////        book.setRatings(ratingSet);
////        book.addRating(rating);
////        Tag tag = new Tag("AI");
////        Set<Book> books = new HashSet<>();
////        books.add(book);
////        tag.setBookList(books);
////        Set<Tag> tags = new HashSet<>();
////        tags.add(tag);
////        book.setTags(tags);
////        book.addTags(tag);
//        return bookRepository.save(book);
//    }
//
//    public List<Tag> getBookTags(UUID id){
//        Book book = bookRepository.findById(id).orElseThrow();
//        return book.getTags();
//    }
//
//    public List getAllBooks(){
//        return bookRepository.findAll();
//    }
//}
    //    public List getAllAuthorBooks(UUID id){
//        Author author = authorRepository.findById(id).orElseThrow();
//        Book newBook = new Book("Tales");
//        author.addBook(newBook);
//        newBook.addAuthor(author);
//        bookRepository.save(newBook);
//        return author.getAuthorBooksList();
//    }
//    public ResponseEntity<?> redirectToPreSignedUrl(SearchInput input){
//        return ResponseEntity.status(302).header(
//                "Location", s3PreSignedURL.generatePreSignedUrl(
//                        ).toString()).body("success");
//    }
