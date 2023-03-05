package com.example.Backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "book")

@JsonIgnoreProperties({"hibernateLazyInitializer","handler","bookRating"})
public class Book {
    @Id
    @GeneratedValue
    @Column(name = "book_id", nullable = false)
    private UUID bookId;

    @Column(name = "book_title", nullable = false)
    private String title;

    @Column(name = "book_price")
    private float price;

    @Column(name = "abstract")
    private String bookAbstract;

//    @ManyToMany
//    @JoinTable(name = "rating",
//            joinColumns = @JoinColumn(name = "book_id")
//            , inverseJoinColumns = @JoinColumn(name = "student_id"))
//    // TODO: 05-Mar-23 convert it to  hash set
//    private List<Rating> ratings = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "book")
    Set<Rating> ratings;

    public Book() {
    }

    public Book(UUID bookId, String title, float price, String bookAbstract, Set<Rating> ratings) {
        this.bookId = bookId;
        this.title = title;
        this.price = price;
        this.bookAbstract = bookAbstract;
        this.ratings = ratings;
    }

    public Book(String title) {
        this.title = title;
    }

    public void setBookId(UUID bookId) {
        this.bookId = bookId;
    }

    public UUID getBookId() {
        return bookId;
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

    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }
}
