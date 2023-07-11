package com.example.Backend.s3Connection;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.Instant;

@Component
public class S3PreSignedURL {
    @Autowired
    private S3Utils s3Utils;

    public URL generatePreSignedUrl(String fileName,long expTimeInSec,AccessType accessType){
        s3Utils.initializeTheClient();
        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = Instant.now().toEpochMilli();
        expTimeMillis += 1000 * expTimeInSec;
        expiration.setTime(expTimeMillis);
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(s3Utils.getBucketName(), fileName)
                        .withMethod(
                                (accessType == AccessType.READ)?HttpMethod.GET:HttpMethod.PUT
                        )
                        .withExpiration(expiration);
        URL url = s3Utils.getAmazonS3().generatePresignedUrl(generatePresignedUrlRequest);
        return url;
    }


}
