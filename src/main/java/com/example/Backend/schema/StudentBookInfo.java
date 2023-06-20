package com.example.Backend.schema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.stereotype.Component;
import org.w3c.dom.stylesheets.LinkStyle;
import software.amazon.awssdk.regions.servicemetadata.LicenseManagerServiceMetadata;

import java.util.List;
import java.util.UUID;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"bookCoverPhotoAsBinaryString"}, allowGetters = false, allowSetters = true)
public class StudentBookInfo extends BookInfo{
    private boolean isBought;
    private String recentOpenedDate;
    private Integer ratingValue;

    public StudentBookInfo() {
    }

    public StudentBookInfo(List<ChapterHeader> chapterHeaders, boolean isBought, String recentOpenedDate, Integer ratingValue) {
        this.isBought = isBought;
        this.recentOpenedDate = recentOpenedDate;
        this.ratingValue = ratingValue;
    }

    public StudentBookInfo(UUID authorId
            ,UUID bookId
            , String title
            , String bookCoverPhotoAsBinaryString
            , String bookAbstract
            , List<String> tags
            , String bookCoverPath
            , String publishDate
            , String lastEditDate
            , Double price
            , Double avgRate
            , List<ChapterHeader> chapterHeaders
            , List<String> contributors
            ,boolean isBought
            , String recentOpenedDate
            , Integer ratingValue) {
        super(authorId, bookId, title, bookCoverPhotoAsBinaryString, bookAbstract, tags, bookCoverPath, publishDate, lastEditDate, price, avgRate, chapterHeaders, contributors);
        this.isBought = isBought;
        this.recentOpenedDate = recentOpenedDate;
        this.ratingValue = ratingValue;
    }

    public StudentBookInfo(UUID authorId, String title, String bookCoverPhotoAsBinaryString, String bookAbstract, List<String> tags, String bookCoverPath, String publishDate, String lastEditDate, Double price, Double avgRate, List<ChapterHeader> chapterHeaders , List<String> contributors, boolean isBought, String recentOpenedDate, Integer ratingValue) {
        super(authorId, title, bookCoverPhotoAsBinaryString, bookAbstract, tags, bookCoverPath, publishDate, lastEditDate, price, avgRate, chapterHeaders, contributors);
        this.isBought = isBought;
        this.recentOpenedDate = recentOpenedDate;
        this.ratingValue = ratingValue;
    }

    public boolean isBought() {
        return isBought;
    }

    public void setBought(boolean bought) {
        isBought = bought;
    }

    public String getRecentOpenedDate() {
        return recentOpenedDate;
    }

    public void setRecentOpenedDate(String recentOpenedDate) {
        this.recentOpenedDate = recentOpenedDate;
    }

    public Integer getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(Integer ratingValue) {
        this.ratingValue = ratingValue;
    }
}
