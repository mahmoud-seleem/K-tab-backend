package com.example.Backend.s3Connection;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class S3DeleteInvalidFiles {
    @Autowired
    private S3Utils s3Utils;

    public  void deleteInvalidFiles(List<String> keys){
        s3Utils.initializeTheClient();
        String[] keysArray = keys.toArray(new String[keys.size()]);
        DeleteObjectsRequest request = new DeleteObjectsRequest(
                s3Utils.getBucketName())
                .withKeys(keysArray);
        System.out.println("Deleting the following Amazon S3 objects created by Amazon Pinpoint:");
        for (String key : keys) {
            System.out.println("\t- " + key);
        }
        try {
            s3Utils.getAmazonS3().deleteObjects(request);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
        System.out.println("Finished deleting objects.");
    }
}
