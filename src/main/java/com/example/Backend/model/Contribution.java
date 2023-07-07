package com.example.Backend.model;

import com.example.Backend.compositeKeys.ContributionKey;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "contribution")
public class Contribution {
    @Id
    @GeneratedValue
    private UUID contributionId;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ElementCollection
    @CollectionTable(
            name = "contribution_chapters",
            joinColumns = @JoinColumn(name = "contribution_id")
    )
    private List<UUID> ChaptersIds = new ArrayList<>();
    public Contribution() {
    }

    public Contribution(UUID contributionId, Author author, Book book, List<UUID> chaptersIds) {
        this.contributionId = contributionId;
        this.author = author;
        this.book = book;
        ChaptersIds = chaptersIds;
    }

    public UUID getContributionId() {
        return contributionId;
    }

    public void setContributionId(UUID contributionId) {
        this.contributionId = contributionId;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public List<UUID> getChaptersIds() {
        return ChaptersIds;
    }
    public List<String> getChaptersIdsAsStrings() {
        List<String> res = new ArrayList<>();
        for(UUID id : ChaptersIds){
            res.add(id.toString());
        }
        return res;
    }


    public void setChaptersIds(List<UUID> chaptersIds) {
        ChaptersIds = chaptersIds;
    }
}
