package com.example.Backend.controller;

import com.example.Backend.Repository.*;
import com.example.Backend.model.*;
import com.example.Backend.s3Connection.AccessType;
import com.example.Backend.s3Connection.S3DeleteInvalidFiles;
import com.example.Backend.s3Connection.S3PreSignedURL;
import com.example.Backend.s3Connection.S3fileSystem;
import com.example.Backend.schema.*;
import com.example.Backend.security.JwtService;
import com.example.Backend.service.S3Service;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.awt.*;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/s3/")
public class S3Controller {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private S3fileSystem s3fileSystem;
    @Autowired
    private S3DeleteInvalidFiles s3DeleteInvalidFiles;
    @Autowired
    private S3PreSignedURL s3PreSignedURL;
    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private S3Service s3Service;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ReadingRepository readingRepository;

    @Autowired
    private PaymentRepository paymentRepository;
    @GetMapping("image/")
    public ImageUploadResponse getPerSignedForWriteImage(@RequestParam UUID chapterId) {
        ImageUploadResponse response = new ImageUploadResponse();
        try {
            String imagePath = s3Service
                    .createNewImagePath(chapterId);
            response.setImagePath(imagePath);
            String imageUrl = s3Service
                    .getPreSignedForWrite(s3Service
                            .createEmptyPlace(imagePath));
            response.setImageUrl(imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @GetMapping("description/")
    public String getImageDescription(@RequestParam String imagePath){
        String url = s3Service.getPreSignedForRead(imagePath);
        // calling the AI server getImageDescription() and
        // give it the url for the image
        // AI server will respond with json which will be
        // returned to the frontend
        return url + " " +UUID.randomUUID();
    }

    @PostMapping("audio/")
    public void saveAudio(@RequestBody AudioInfo audioInfo){
        String contentPreSignedReadUrl = s3Service
                .getPreSignedForRead(audioInfo.getContentPath());
        String audioPreSignedWriteUrl = s3Service
                .getPreSignedForWrite(audioInfo.getAudioPath());
        Chapter chapter = chapterRepository
                .findById(audioInfo.getChapterId()).get();
        // calling the AI server and get the reading duration
        chapter.setReadingDuration(10.0);
        chapterRepository.save(chapter);
    }
    @GetMapping("save-content/")
    public String getPreSignedForWriteContent(@RequestParam String contentPath){
        return s3Service.getPreSignedForWrite(contentPath);
    }

    @DeleteMapping("invalid/")
    public void deleteInvalidImages(@RequestBody InvalidInfo invalidInfo){
        s3Service.deleteInvalidImages(
                invalidInfo.getInvalidImagesPaths()
        );
    }

    @GetMapping("read/")
    public ChapterReadResponse chapterReadResponse(HttpServletRequest request,@RequestParam UUID chapterId){
        ChapterReadResponse chapterReadResponse = new ChapterReadResponse();
        chapterReadResponse.setContentUrl(
                s3Service.getPreSignedForRead(
                        s3Service.createContentPath(chapterId)));
        chapterReadResponse.setImagesUrls(
                s3Service.getImagesReadUrls(chapterId));
        if (jwtService.getUserType(request).equals("STUDENT")){
            Student student = studentRepository.findById(
                    jwtService.getUserId(request)).get();
            Chapter chapter = chapterRepository.findById(chapterId).get();
            Book book  = chapter.getBook();
            Payment payment = paymentRepository.findByStudentAndBook(student,book);
            payment.setRecentOpenedChapterId(chapterId);
            paymentRepository.save(payment);
        }
    return chapterReadResponse;
    }
}