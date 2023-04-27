package com.example.Backend.service;

import com.example.Backend.Repository.AuthorRepository;
import com.example.Backend.Repository.BookRepository;
import com.example.Backend.Repository.TagRepository;
import com.example.Backend.model.*;
import com.example.Backend.s3Connection.S3fileSystem;
import com.example.Backend.schema.BookInfo;
import com.example.Backend.utils.ImageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public BookInfo saveNewBook(BookInfo bookInfo){
        Book book = createNewBook(bookInfo);
        Author author = authorRepository.findById(bookInfo.getAuthorId()).get();
        bookRepository.save(book);
        if(bookInfo.getBookCoverPhotoAsBinaryString() != null){
            String photoPath = setupBookCover(book,bookInfo);
            bookInfo.setBookCoverPath(photoPath);
        }
        setupBookTags(book,bookInfo.getTags());
        author.addAuthorBook(book);
        authorRepository.save(author);
        bookInfo.setBookId(book.getBookId());

        bookInfo.setLastEditDate(updateLastEditDate(book).
                getLastEditDate().format(formatter));
        bookInfo.setAvgRate(book.calculateAvgRating());
        bookInfo.setChaptersTitles(book.getChaptersTitles());
        return bookInfo;
    }
    public BookInfo updateBookInfo(BookInfo bookInfo) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Book book = bookRepository.findById(bookInfo.getBookId()).get();
        for (Field field : bookInfo.getClass().getDeclaredFields()){
            field.setAccessible(true);
            if(field.get(bookInfo) != null ){
                System.out.println(field.getName());
                if (field.getName() == "bookCoverPhotoAsBinaryString"){
                    setupBookCover(book,bookInfo);
                }else if(field.getName() == "tags"){
                    setupBookTags(book,bookInfo.getTags());
                }else if (field.getName() == "publishDate" || field.getName() == "lastEditDate"){
                    getMethodBySignature("set",field,book,LocalDateTime.class)
                            .invoke(book,LocalDateTime.parse(field.get(bookInfo).toString() , formatter));
                }
                else {
                    getMethodBySignature("set",field,book,field.getType())
                            .invoke(book,field.get(bookInfo));
                }
            }
        }
        bookRepository.save(updateLastEditDate(book));
        return createBookInfoResponse(bookInfo);
    }
    private BookInfo createBookInfoResponse(BookInfo bookInfo){
        Book book = bookRepository.findById(bookInfo.getBookId()).get();
        BookInfo response =  new BookInfo(
                book.getAuthor().getAuthorId(),
                book.getBookId(),
                book.getTitle(),
                null,
                book.getBookAbstract(),
                book.getTagsNames(),
                book.getBookCover(),
                book.getPublishDate().format(formatter),
                book.getLastEditDate().format(formatter),
                book.getPrice(),
                book.calculateAvgRating(),
                book.getChaptersTitles()
        );
        return response;
    }
    public Book updateLastEditDate(Book book){
        book.setLastEditDate(LocalDateTime.parse(LocalDateTime.now().
                format(formatter) , formatter));
        return book;
    }
    private String setupBookCover(Book book,BookInfo bookInfo){
        String photoPath = ("Books/"+book.getBookId().toString()+"/coverPhoto.png");
        book.setBookCover(photoPath);
        InputStream inputStream = imageConverter.convertImgToFile(
                bookInfo.getBookCoverPhotoAsBinaryString());
        return s3fileSystem.uploadPhoto(photoPath,inputStream);
    }
    private Book setupBookTags(Book book,List<String> tags){
        book.clearTags();
        Tag tag = null;
        for(String tagName : tags){
            if(tagRepository.findByName(tagName).isEmpty()){
                tag =tagRepository.save(new Tag(tagName));
            }else {
                tag = tagRepository.findByName(tagName).get();
                tag.removeBook(book);
            }
            tag.addBook(book);
        }
        tagRepository.save(tag);
        Book b = bookRepository.save(book);
        Tag newTag = tagRepository.findByName(tag.getTagName()).get();
        System.out.println("____________________________________________");
        for(Tag t: b.getTags()){
            System.out.println(t.getTagName());
        }
        System.out.println("____________________________________________");
        for (Book bb : newTag.getBookList()){
            System.out.println(bb.getTitle());
        }
        System.out.println("____________________________________________");
        return b;
    }
    private Book createNewBook(BookInfo bookInfo){
        Book book = new Book(
                bookInfo.getTitle(),
                bookInfo.getPrice(),
                bookInfo.getBookAbstract()
        );
        if (bookInfo.getPublishDate() != null){
            book.setPublishDate(LocalDateTime.parse(bookInfo.getPublishDate(),formatter));
        }
        return bookRepository.save(book);
    }
    public Book findBookById(UUID id){
        return bookRepository.findById(id).orElseThrow();
    }


    private Method getMethodBySignature(String prefix,Field field,Object callerObject,Class<?>... parametersTypes) throws NoSuchMethodException {
        String methodName = prefix +
                field.getName().substring(0,1).toUpperCase() +
                field.getName().substring(1);
        return  callerObject.getClass().getMethod(methodName,parametersTypes);
    }
    public Book insertSpecificBook(){
        Book book = new Book("Mariam's fav book");
        Student student = new Student("Mariam");
//        Rating rating = new Rating(book, student, 5);
//        Set<Rating> ratingSet = new HashSet<Rating>();
//        ratingSet.add(rating);
//        book.setRatings(ratingSet);
//        book.addRating(rating);
//        Tag tag = new Tag("AI");
//        Set<Book> books = new HashSet<>();
//        books.add(book);
//        tag.setBookList(books);
//        Set<Tag> tags = new HashSet<>();
//        tags.add(tag);
//        book.setTags(tags);
//        book.addTags(tag);
        return bookRepository.save(book);
    }

    public List<Tag> getBookTags(UUID id){
        Book book = bookRepository.findById(id).orElseThrow();
        return book.getTags();
    }

    public List getAllAuthorBooks(UUID id){
        Author author = authorRepository.findById(id).orElseThrow();
        Book newBook = new Book("Tales");
        author.addAuthorBook(newBook);
        newBook.addAuthor(author);
        bookRepository.save(newBook);
        return author.getAuthorBooksList();
    }

    public List getAllBooks(){
        return bookRepository.findAll();
    }
}
