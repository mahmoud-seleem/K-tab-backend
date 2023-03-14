package com.example.Backend.s3Connection;

import ch.qos.logback.core.joran.action.AppenderRefAction;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class S3DeleteInvalidFiles {
    @Autowired
    private S3Utils s3Utils;
    public  void deleteInvalidFiles(List<String> keys){
        s3Utils.initializeTheClient();
        String[] keysArray = keys.toArray(new String[keys.size()]);
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
        System.out.println("Finished deleting objects.");
    }
}
