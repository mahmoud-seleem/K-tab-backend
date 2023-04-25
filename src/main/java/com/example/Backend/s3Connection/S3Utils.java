package com.example.Backend.s3Connection;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class S3Utils {
    @Autowired
    @Value("${ACCESS_KEY}")
    private  String accessKey;
    @Value("${SECRET_KEY}")
    @Autowired
    private  String secretKey;
    @Value("${REGION}")
    @Autowired
    private  String region;
    @Value("${BUCKET_NAME}")
    @Autowired
    private  String bucketName;
    private  AWSCredentials awsCredentials;
    private  AmazonS3 amazonS3;

    public void initializeTheClient(){
        this.awsCredentials = new BasicAWSCredentials(accessKey,secretKey);
        this.amazonS3 = AmazonS3ClientBuilder.standard().withRegion(region).withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
    }
    public  String getAccessKey() {
        return this.accessKey;
    }

    public  String getSecretKey() {
        return this.secretKey;
    }

    public  String getRegion() {
        return this.region;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey= secretKey ;
    }

    public void setRegion(String region) {
        this.region = region ;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName ;
    }

    public  String getBucketName() {
        return this.bucketName;
    }
    public void setAmazonS3(AmazonS3 amazonS3){
        this.amazonS3 = amazonS3;
    }
    public AmazonS3 getAmazonS3(){
        return amazonS3;
    }

    public AWSCredentials getAwsCredentials() {
        return awsCredentials;
    }

    public void setAwsCredentials(AWSCredentials awsCredentials) {
        this.awsCredentials = awsCredentials;
    }
}

