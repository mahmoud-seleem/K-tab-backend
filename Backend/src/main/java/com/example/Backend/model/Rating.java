package com.example.Backend.model;

import com.example.Backend.service.RatingService;
import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Entity
public class Rating {

//    @EmbeddedId
//    RatingKey id;
    @Id
    @GeneratedValue
    UUID ratingId;


    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    Book book;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    Student student;

    @Column(name = "rating_value", nullable = false)
    private int ratingValue;

    public Rating() {
    }

    public Rating(Book book, Student student, int ratingValue) {
        this.book = book;
        this.student = student;
        this.ratingValue = ratingValue;
    }



    public UUID getRatingId() {
        return ratingId;
    }

    public void setRatingId(UUID ratingId) {
        this.ratingId = ratingId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(int ratingValue) {
        this.ratingValue = ratingValue;
    }



    public void addBookRating(Book book){
        this.setBook(book);
        Set currentBookRatingsSet = book.getRatings();
        currentBookRatingsSet.add(this);
        book.setRatings(currentBookRatingsSet);
    }

    public void removeBookRating(Book book){
        this.setBook(null);
        Set currentBookRatingsSet = book.getRatings();
        currentBookRatingsSet.remove(this);
        book.setRatings(currentBookRatingsSet);
    }

    public void addStudentRating(Student student){
        this.setStudent(student);
        Set currentStudentRatingsSet = student.getRatings();
        currentStudentRatingsSet.add(this);
        book.setRatings(currentStudentRatingsSet);

    }
}
