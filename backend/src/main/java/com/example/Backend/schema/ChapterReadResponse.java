package com.example.Backend.schema;

import java.util.List;
import java.util.Map;

public class ChapterReadResponse {
    private String contentUrl;
    private List<ImageUploadResponse> imagesUrls;

    public ChapterReadResponse(String contentUrl, List<ImageUploadResponse> imagesUrls) {
        this.contentUrl = contentUrl;
        this.imagesUrls = imagesUrls;
    }

    public ChapterReadResponse() {
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public List<ImageUploadResponse> getImagesUrls() {
        return imagesUrls;
    }

    public void setImagesUrls(List<ImageUploadResponse> imagesUrls) {
        this.imagesUrls = imagesUrls;
    }
}
