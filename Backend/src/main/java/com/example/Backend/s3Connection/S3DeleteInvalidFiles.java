package com.example.Backend.s3Connection;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;

public class S3DeleteInvalidFiles {

    final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();


    public void deleteFilesFromS3(String bucketName, String[] objectsKeys){

        try

        {
            DeleteObjectsRequest dor = new DeleteObjectsRequest(bucketName)
                    .withKeys(objectsKeys);
            s3.deleteObjects(dor);
        } catch(
                AmazonServiceException e)

        {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }


    }
}
