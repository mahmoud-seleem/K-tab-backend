package com.example.Backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "book")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","ratings","chapters","tags","paymentList"})
public class Book {
    @Id
    @GeneratedValue
    @Column(name = "book_id", nullable = false)
    private UUID bookId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "price")
    private float price;

    @Column(name = "book_abstract")
    private String bookAbstract;


    @OneToMany(mappedBy = "book")
    private List<Rating> ratings = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToMany
    @JoinTable(name = "book_tags"
            ,joinColumns = @JoinColumn(name = "book_id")
            ,inverseJoinColumns = @JoinColumn(name = "tag_id") )
    private List<Tag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<Chapter> chapters = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<Payment> paymentList = new ArrayList<>();

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    public Book() {
    }

    public Book(UUID bookId, String title, float price, String bookAbstract, List<Rating> ratings) {
        this.bookId = bookId;
        this.title = title;
        this.price = price;
        this.bookAbstract = bookAbstract;
        this.ratings = ratings;
        this.chapters = new ArrayList<>();
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

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public void addPayment(Payment payment){
        this.getPaymentList().add(payment);
        payment.setBook(this);
    }
    public void removePayment(Payment payment){
        this.getPaymentList().remove(payment);
        payment.setBook(null);
    }
    public void addChapter(Chapter chapter){
        this.chapters.add(chapter);
        chapter.setBook(this);
    }
    public void removeChapter(Chapter chapter){
        this.chapters.remove(chapter);
        chapter.setBook(null);
    }
    public void addRating(Rating rating){
        this.getRatings().add(rating);
        rating.setBook(this);
    }
    public void removeRating(Rating rating){
        this.getRatings().remove(rating);
        rating.setBook(null);
    }

    public void addTag(Tag tag){
        this.getTags().add(tag);
    }
    public void removeTag(Tag tag){
        this.getTags().remove(tag);
    }

    public void addAuthor(Author author){
        this.setAuthor(author);
        author.getAuthorBooksList().add(this);
    }

    public void removeAuthor(Author author){
        this.setAuthor(null);
        author.getAuthorBooksList().remove(this);
    }
}