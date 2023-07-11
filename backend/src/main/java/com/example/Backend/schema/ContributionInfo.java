package com.example.Backend.schema;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
@Component
public class ContributionInfo {

    private UUID ownerId;
    private UUID bookId;
    private String contributorEmail;
    private List<Integer> chaptersOrders;

    public ContributionInfo() {
    }

    public ContributionInfo(UUID ownerId, UUID bookId, String contributorEmail, List<Integer> chaptersOrders) {
        this.ownerId = ownerId;
        this.bookId = bookId;
        this.contributorEmail = contributorEmail;
        this.chaptersOrders = chaptersOrders;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public UUID getBookId() {
        return bookId;
    }

    public void setBookId(UUID bookId) {
        this.bookId = bookId;
    }

    public String getContributorEmail() {
        return contributorEmail;
    }

    public void setContributorEmail(String contributorEmail) {
        this.contributorEmail = contributorEmail;
    }

    public List<Integer> getChaptersOrders() {
        return chaptersOrders;
    }

    public void setChaptersOrders(List<Integer> chaptersOrders) {
        this.chaptersOrders = chaptersOrders;
    }
}
