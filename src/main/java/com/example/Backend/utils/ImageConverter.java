package com.example.Backend.utils;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
@Component
public class ImageConverter {

    private byte[] decodeImage(String imageAsBinaryString) {
        return Base64.decodeBase64(imageAsBinaryString);
    }


    public InputStream convertImgToFile(String imageAsBinaryString){
        System.out.println(decodeImage(imageAsBinaryString).length);
        InputStream inputStream = new ByteArrayInputStream(decodeImage(imageAsBinaryString));
        return inputStream;
    }
}
