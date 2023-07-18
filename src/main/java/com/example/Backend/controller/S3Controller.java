package com.example.Backend.controller;

import com.example.Backend.Repository.*;
import com.example.Backend.model.*;
import com.example.Backend.s3Connection.S3DeleteInvalidFiles;
import com.example.Backend.s3Connection.S3PreSignedURL;
import com.example.Backend.s3Connection.S3fileSystem;
import com.example.Backend.enums.ImageType;
import com.example.Backend.schema.*;
import com.example.Backend.security.JwtService;
import com.example.Backend.service.S3Service;
import com.example.Backend.validation.InputNotLogicallyValidException;
import com.example.Backend.validation.ValidationUtils;
import com.example.Backend.validation.json.ValidJson;
import com.example.Backend.validation.json.ValidParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/s3/")
public class S3Controller {

    @Value("${AI_DOMAIN}")
    private String AI_DOMAIN;

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
    @Autowired
    private ValidationUtils validationUtils;
    @GetMapping("image/")
    public ImageUploadResponse getPerSignedForWriteImage(@ValidParam UUID chapterId) throws InputNotLogicallyValidException {
        validationUtils.checkForNull("chapterId",chapterId);
        validationUtils.checkChapterIsExisted(chapterId);
        ImageUploadResponse response = new ImageUploadResponse();
            String imagePath = s3Service
                    .createNewImagePath(chapterId);
            response.setImagePath(imagePath);
            String imageUrl = s3Service
                    .getPreSignedForWrite(s3Service
                            .createEmptyPlace(imagePath));
            response.setImageUrl(imageUrl);
        return response;
    }


    @GetMapping("description/")
    public ImageDescriptionDto getImageDescription(@ValidParam String imagePath) {
        String url = s3Service.getPreSignedForRead(imagePath);

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("img_url", url);

        Map<String, Object> aiResponse = restTemplate.postForObject(AI_DOMAIN, requestBody , Map.class);
        System.out.println( aiResponse.get("type"));
        if( aiResponse.get("type").equals("math")){
            Map<String, String> data = (Map<String, String>) aiResponse.get("data");
            return new ImageDescriptionDto(ImageType.MATH, data.get("text").toString());
        }
        return new ImageDescriptionDto(ImageType.SCENE, aiResponse.get("data").toString());
    }


    @PostMapping("audio/")
    public void saveAudio(@ValidJson("AudioInfo") AudioInfo audioInfo) {
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

    @GetMapping("/resources/")
    public ResourceUrl getS3Resource(@ValidParam String resourcePath) throws InputNotLogicallyValidException {
        validationUtils.checkForNullEmptyAndBlankString("resourcePath",resourcePath);
//        RedirectView redirectView = new RedirectView();
        return new ResourceUrl(s3Service.getPreSignedForRead(resourcePath));
    }
    @GetMapping("save-content/")
    public String getPreSignedForWriteContent(@ValidParam String contentPath) {
        return s3Service.getPreSignedForWrite(contentPath);
    }

    @DeleteMapping("invalid/")
    public void deleteInvalidImages(@ValidJson("InvalidInfo") InvalidInfo invalidInfo) {
        s3Service.deleteInvalidImages(
                invalidInfo.getInvalidImagesPaths());
    }

    @GetMapping("read/")
    public ChapterReadResponse chapterReadResponse(HttpServletRequest request, @ValidParam UUID chapterId){
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
            payment.setRecentOpenedDate(LocalDateTime.now());
            paymentRepository.save(payment);
        }
    return chapterReadResponse;
    }
}
