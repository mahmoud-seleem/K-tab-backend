package com.example.Backend.s3Connection;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class S3fileSystem {

    @Autowired
    private S3Utils s3Utils;
    public  String createChapterFolder(UUID bookId, UUID chapterId) {
        s3Utils.initializeTheClient();
        String bookFolder = bookId.toString();
        String chapterFolder = chapterId.toString();
        String folderName = "books/" + bookFolder + "/" + chapterFolder + "/";
        s3Utils.getAmazonS3().putObject(s3Utils.getBucketName(),folderName,""); // RequestBody.empty() to specify empty object which is a folder
        return folderName;
    }
    public  String createBookFolder(UUID bookId) {
        s3Utils.initializeTheClient();
        String bookFolder = bookId.toString();
        String folderName = "books/" + bookFolder + "/";
        s3Utils.getAmazonS3().putObject(s3Utils.getBucketName(),folderName,""); // RequestBody.empty() to specify empty object which is a folder
        return folderName;
    }

    public List<String> getAllKeys(){
        s3Utils.initializeTheClient();
        ObjectListing objectListing = s3Utils.getAmazonS3().listObjects(s3Utils.getBucketName(),"books/");
        List<S3ObjectSummary> objectSummaryList = objectListing.getObjectSummaries();
        List<String> keys = new ArrayList<>();
        for(S3ObjectSummary objectSummary : objectSummaryList){
            keys.add(objectSummary.getKey());
        }
        return keys;
    }
}
