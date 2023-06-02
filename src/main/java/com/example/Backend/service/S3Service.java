package com.example.Backend.service;

import com.example.Backend.Repository.BookRepository;
import com.example.Backend.Repository.ChapterRepository;
import com.example.Backend.s3Connection.AccessType;
import com.example.Backend.s3Connection.S3DeleteInvalidFiles;
import com.example.Backend.s3Connection.S3PreSignedURL;
import com.example.Backend.s3Connection.S3fileSystem;
import com.example.Backend.schema.ImageUploadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class S3Service {

    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private S3fileSystem s3fileSystem;
    @Autowired
    private S3DeleteInvalidFiles s3DeleteInvalidFiles;
    @Autowired
    private S3PreSignedURL s3PreSignedURL;

    public String getPreSignedForWrite(String path) {
        return s3PreSignedURL.generatePreSignedUrl(
                path,
                60,
                AccessType.WRITE
        ).toString();
    }

    public String getPreSignedForRead(String path) {
        return s3PreSignedURL.generatePreSignedUrl(
                path,
                60,
                AccessType.READ
        ).toString();
    }

    public String createEmptyPlace(String path) {
        return s3fileSystem.reserveEmptyPlace(path);
    }

    public String createNewImagePath(UUID chapterId) {
        UUID imageId = UUID.randomUUID();
        return createImagesPrefix(chapterId)
                + imageId.toString()
                + ".png";
    }

    public String createImagesPrefix(UUID chapterId) {
        return "Books/"
                + getBookIdFromChapterId(chapterId)
                .toString()
                + "/Chapters/"
                + chapterId.toString()
                + "/Images/";
    }
    public String createContentPath(UUID chapterId) {
        return "Books/"
                + getBookIdFromChapterId(chapterId)
                .toString()
                + "/Chapters/"
                + chapterId.toString()
                + "/content.json";
    }

    private UUID getBookIdFromChapterId(UUID chapterId) {
        return chapterRepository
                .findById(chapterId)
                .get()
                .getBook()
                .getBookId();
    }

    public List<ImageUploadResponse> getImagesReadUrls(UUID chapterId) {
        String prefix = createImagesPrefix(chapterId);
        List<String> imagesPaths = s3fileSystem
                .getKeysWithPrefix(prefix);
        List<ImageUploadResponse> readUrls = new ArrayList<>();
        for (String path : imagesPaths){
            ImageUploadResponse element = new ImageUploadResponse();
            element.setImagePath(path);
            element.setImageUrl(getPreSignedForRead(path));
            readUrls.add(element);
        }
        return readUrls;
    }

    public void deleteInvalidImages(List<String> invalidImagesPaths) {
        s3DeleteInvalidFiles.deleteInvalidFiles(invalidImagesPaths);
    }
}
