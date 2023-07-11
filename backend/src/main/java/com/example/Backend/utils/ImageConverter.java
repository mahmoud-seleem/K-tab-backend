package com.example.Backend.utils;

import com.example.Backend.validation.InputNotLogicallyValidException;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
@Component
public class ImageConverter {

    private byte[] decodeImage(String imageAsBinaryString) {
        return Base64.decodeBase64(imageAsBinaryString);
    }

    public void checkForValidImage(String fieldName ,String base64String) throws InputNotLogicallyValidException {
        try {
            byte[] decodedBytes = decodeImage(base64String);
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(decodedBytes));
            image.getHeight();
        } catch (Exception e) {
            throw new InputNotLogicallyValidException(
                    fieldName,
                    "the Binary String doesn't represent valid image file !");
        }
    }
    public InputStream convertImgToFile(String imageAsBinaryString){
        System.out.println(decodeImage(imageAsBinaryString).length);
        InputStream inputStream = new ByteArrayInputStream(decodeImage(imageAsBinaryString));
        return inputStream;
    }
}
