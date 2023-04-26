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

import java.io.InputStream;
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
    public BookInfo saveNewBook(BookInfo bookInfo){
        Book book = createNewBook(bookInfo);
        bookRepository.save(book);
        String photoPath = ("Books/"+book.getBookId().toString()+"/coverPhoto.png");
        book.setBookCover(photoPath);
        InputStream inputStream = imageConverter.convertImgToFile(
                bookInfo.getBookCoverPhotoAsBinaryString());
        s3fileSystem.uploadPhoto(photoPath,inputStream);
        setupBookTags(book,bookInfo.getTags());
        bookInfo.setBookId(book.getBookId());
        bookInfo.setBookCoverPath(photoPath);
        return bookInfo;
    }
    private void setupBookTags(Book book,List<String> tags){
        for(String tagName : tags){
            Tag tag;
            if(tagRepository.findByName(tagName).isEmpty()){
                tag =new Tag(tagName);
            }else {
                tag = tagRepository.findByName(tagName).get();
            }
            tag.addBook(book);
            tagRepository.save(tag);
        }
    }
    private Book createNewBook(BookInfo bookInfo){
        return bookRepository.save(new Book(
                bookInfo.getBookTitle(),
                bookInfo.getPrice(),
                bookInfo.getBookAbstract()
        ));
    }
    public Book findBookById(UUID id){
        return bookRepository.findById(id).orElseThrow();
    }

    public Book insertBook(Book book){
        return bookRepository.save(book);
    }

    public Book updateBookInfo(Book book){
        Book currentBook = bookRepository.findById(book.getBookId()).orElseThrow();
        currentBook.setTitle(book.getTitle());
        currentBook.setBookAbstract(book.getBookAbstract());
        return bookRepository.save(currentBook);
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
