package com.example.Backend.model;

import com.example.Backend.compositeKeys.ContributionKey;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "contribution")
public class Contribution {
    @EmbeddedId
    ContributionKey contributionId;

    @ManyToOne
    @MapsId("authorId")
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne
    @MapsId("chapterId")
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "book_id")
    private UUID bookId;
    public Contribution() {
    }

    public Contribution(ContributionKey contributionId, Author author, Chapter chapter, LocalDateTime updateDate) {
        this.contributionId = contributionId;
        this.author = author;
        this.chapter = chapter;
        this.updateDate = updateDate;
    }

    public ContributionKey getContributionId() {
        return contributionId;
    }

    public void setContributionId(ContributionKey contributionId) {
        this.contributionId = contributionId;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public UUID getBookId() {
        return bookId;
    }

    public void setBookId(UUID bookId) {
        this.bookId = bookId;
    }
}
