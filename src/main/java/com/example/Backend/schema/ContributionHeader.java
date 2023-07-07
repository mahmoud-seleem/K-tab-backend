package com.example.Backend.schema;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContributionHeader {
    private String ContributorId;
    private List<String> chapterIds;

    public ContributionHeader() {
    }

    public ContributionHeader(String contributorId, List<String> chapterIds) {
        ContributorId = contributorId;
        this.chapterIds = chapterIds;
    }

    public String getContributorId() {
        return ContributorId;
    }

    public void setContributorId(String contributorId) {
        ContributorId = contributorId;
    }

    public List<String> getChapterIds() {
        return chapterIds;
    }

    public void setChapterIds(List<String> chapterIds) {
        this.chapterIds = chapterIds;
    }
}
