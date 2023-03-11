package com.example.Backend.s3Connection;

import java.util.UUID;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Random;

import com.amazonaws.regions.Regions;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.paginators.ListObjectsV2Iterable;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadResponse;
import software.amazon.awssdk.services.s3.model.CompletedMultipartUpload;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.CompletedPart;
import software.amazon.awssdk.services.s3.model.CreateBucketConfiguration;
import software.amazon.awssdk.services.s3.model.UploadPartRequest;
import software.amazon.awssdk.services.s3.model.CompleteMultipartUploadRequest;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketResponse;


public class S3fileSystem {

    private static String createFileSystemLibrary(String bucketName, UUID bookId, UUID chapterId){

        String bookFolder = bookId.toString();
        String chapterFolder = chapterId.toString();
        String folderName = bookFolder +"/"+ chapterFolder +"/";

        // TODO: 11-Mar-23 change region to the bucket region 
        Region region = Region.US_WEST_2;
        
        S3Client s3 = S3Client.builder().region(region).build();

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(folderName)
                .build();

        s3.putObject(objectRequest, RequestBody.empty()); // RequestBody.empty() to specify empty object which is a folder



        return folderName;


    }

    public static void main(String[] args) {
        System.out.println(createFileSystemLibrary("bbb", UUID.randomUUID(), UUID.randomUUID()));
    }




}
