package com.example.Backend.s3Connection;

import java.util.UUID;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;



public class S3fileSystem {



    public static String createFileSystemLibrary(String bucketName, UUID bookId, UUID chapterId) {

        String bookFolder = bookId.toString();
        String chapterFolder = chapterId.toString();
        String folderName = "books/" + bookFolder + "/" + chapterFolder + "/";

        Region region = Region.US_EAST_1;

        S3Client s3 = S3Client.builder().region(region).build();

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(folderName)
                .build();

        s3.putObject(objectRequest, RequestBody.empty()); // RequestBody.empty() to specify empty object which is a folder


        return folderName;


    }

    public static void main(String[] args) {

        AWSCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
        System.out.println(credentialsProvider.getCredentials());

        System.out.println(createFileSystemLibrary("content-pwd-aat", UUID.randomUUID(), UUID.randomUUID()));
    }


}
