package com.example.Backend.s3Connection;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import java.util.List;
import software.amazon.awssdk.regions.Region;



public class S3DeleteInvalidFiles {


    public static void deleteFilesFromS3(String s3BucketName, List<String> keys){

        Region region = Region.US_EAST_1;

        AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();

        String[] keysArray = keys.toArray(new String[keys.size()]);
        DeleteObjectsRequest request = new DeleteObjectsRequest(s3BucketName).withKeys(keysArray);

        System.out.println("Deleting the following Amazon S3 objects created by Amazon Pinpoint:");

        for (String key : keys) {
            System.out.println("\t- " + key);
        }

        try {
            s3Client.deleteObjects(request);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }

        System.out.println("Finished deleting objects.");

    }



}
