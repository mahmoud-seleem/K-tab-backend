package com.example.Backend.model;

import com.example.Backend.service.RatingService;
import jakarta.persistence.*;
import org.springframework.lang.NonNull;

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

//    public RatingKey getId() {
//        return id;
//    }
//
//    public void setId(RatingKey id) {
//        this.id = id;
//    }


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
}
