package com.example.Backend.s3Connection;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class S3fileSystem {

    @Autowired
    private S3Utils s3Utils;

    public String uploadPhoto(String photoPath, InputStream inputStream) {
        s3Utils.initializeTheClient();
        s3Utils.getAmazonS3().putObject(
                new PutObjectRequest(s3Utils.getBucketName()
                        , photoPath, inputStream, new ObjectMetadata()));
        return photoPath;
    }

    public String reserveEmptyPlace(String path) {
        s3Utils.initializeTheClient();
        s3Utils.getAmazonS3().putObject(s3Utils.getBucketName(), path, "");
        return path;
    }

    public String uploadProfilePhoto2(String authorId, File file) {
        s3Utils.initializeTheClient();
        String profilePhotoPath = "Authors/" + authorId + "/profilePhoto.png";
        System.out.println("starting");
        PutObjectResult result = s3Utils.getAmazonS3().putObject(s3Utils.getBucketName()
                , profilePhotoPath, file);
        System.out.println("end");
        return profilePhotoPath;
    }

    public String createChapterFolder(UUID bookId, UUID chapterId) {
        s3Utils.initializeTheClient();
        String bookFolder = bookId.toString();
        String chapterFolder = chapterId.toString();
        String folderName = "books/" + bookFolder + "/" + chapterFolder + "/";
        s3Utils.getAmazonS3().putObject(s3Utils.getBucketName(), folderName, ""); // RequestBody.empty() to specify empty object which is a folder
        return folderName;
    }

    public String createBookFolder(UUID bookId) {
        s3Utils.initializeTheClient();
        String bookFolder = bookId.toString();
        String folderName = "books/" + bookFolder + "/";
        s3Utils.getAmazonS3().putObject(s3Utils.getBucketName(), folderName, ""); // RequestBody.empty() to specify empty object which is a folder
        return folderName;
    }

    public List<String> getAllKeys() {
        s3Utils.initializeTheClient();
        ObjectListing objectListing = s3Utils.getAmazonS3().listObjects(s3Utils.getBucketName(), "books/");
        List<S3ObjectSummary> objectSummaryList = objectListing.getObjectSummaries();
        List<String> keys = new ArrayList<>();
        for (S3ObjectSummary objectSummary : objectSummaryList) {
            keys.add(objectSummary.getKey());
        }
        return keys;
    }

    public List<String> getKeysWithPrefix(String prefix) {
        s3Utils.initializeTheClient();
        ListObjectsRequest listObjectsRequest =
                new ListObjectsRequest()
                        .withBucketName(
                                s3Utils.getBucketName())
                        .withPrefix(prefix);
        ObjectListing objectListing = s3Utils
                .getAmazonS3()
                .listObjects(listObjectsRequest);
        List<S3ObjectSummary> objectSummaryList = objectListing
                .getObjectSummaries();
        List<String> keys = new ArrayList<>();
        for (S3ObjectSummary objectSummary : objectSummaryList) {
            keys.add(objectSummary.getKey());
        }
        return keys;
    }
}
