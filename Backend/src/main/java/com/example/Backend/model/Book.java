package com.example.Backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "book")

@JsonIgnoreProperties({"hibernateLazyInitializer","handler","bookRating"})
public class Book {
    @Id
    @GeneratedValue
    @Column(name = "book_id", nullable = false)
    private UUID id;

    @Column(name = "book_title", nullable = false)
    private String title;

    @Column(name = "book_price")
    private float price;

    @Column(name = "abstract")
    private String bookAbstract;

//    @OneToMany
//    @JoinColumn(name = "ratings_id")
//    private Rating ratings;

    public Book() {
    }

    public Book(String title, float price, String bookAbstract, Rating ratings) {
        this.title = title;
        this.price = price;
        this.bookAbstract = bookAbstract;
//        this.ratings = ratings;
    }

//
//    public float getBookRating(){
////        this.getRatings()
//    }


    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getBookAbstract() {
        return bookAbstract;
    }

    public void setBookAbstract(String bookAbstract) {
        this.bookAbstract = bookAbstract;
    }

//    public Rating getRatings() {
//        return ratings;
//    }
//
//    public void setRatings(Rating ratings) {
//        this.ratings = ratings;
//    }

    public void setId(UUID id) {
        this.id = id;
    }
}
