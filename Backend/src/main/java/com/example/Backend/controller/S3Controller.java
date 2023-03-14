package com.example.Backend.controller;

import com.example.Backend.Repository.BookRepository;
import com.example.Backend.Repository.ChapterRepository;
import com.example.Backend.model.Book;
import com.example.Backend.model.Chapter;
import com.example.Backend.s3Connection.AccessType;
import com.example.Backend.s3Connection.S3DeleteInvalidFiles;
import com.example.Backend.s3Connection.S3PreSignedURL;
import com.example.Backend.s3Connection.S3fileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/s3")
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
    @GetMapping("/newbook/{title}")
    public String CreateNewBook(@PathVariable String title){
        Book book = new Book(title);
        book = bookRepository.save(book);
        return s3fileSystem.createBookFolder(book.getBookId());
    }
    @GetMapping("/newchapter/{title}")
    public String CreateNewChapter(@PathVariable String title){
        Book book = bookRepository.findByTitle("temp-title");
        Chapter chapter = new Chapter(title);
        chapter = chapterRepository.save(chapter);
        return s3fileSystem.createChapterFolder(book.getBookId(),chapter.getChapterId());
    }
    @GetMapping("/url/write/")
    public String getPreSignedUrlForWrite(@RequestBody String fileName){
        return s3PreSignedURL.generatePreSignedUrl(fileName,60, AccessType.WRITE).toString();
    }
    @GetMapping("/url/read/")
    public String getPreSignedUrlForRead(@RequestBody String fileName){
        return s3PreSignedURL.generatePreSignedUrl(fileName,60, AccessType.READ).toString();
    }
    @GetMapping("/getkeys")
    public List<String> getAllKeys(){
        return s3fileSystem.getAllKeys();
    }
    @GetMapping("invalid/")
    public void deleteInvalidFiles(@RequestBody List<String> keys){
        s3DeleteInvalidFiles.deleteInvalidFiles(keys);
    }
}
