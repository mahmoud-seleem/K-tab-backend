package com.example.Backend.controller;

import com.example.Backend.Repository.AuthorRepository;
import com.example.Backend.Repository.ChapterRepository;
import com.example.Backend.Repository.WritingRepository;
import com.example.Backend.compositeKeys.WritingKey;
import com.example.Backend.model.Author;
import com.example.Backend.model.Chapter;
import com.example.Backend.model.Writing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/writing")
public class WritingController {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private WritingRepository writingRepository;
    @GetMapping("/newchapter")
    public Chapter creatNewChapter(){
        Chapter chapter = new Chapter("chapter one");
        return  chapterRepository.save(chapter);
    }
    @GetMapping("/newauthor")
    public Author creatNewAuthor(){
        Author author = new Author("mohamed");
        return (authorRepository.save(author));
    }
    @GetMapping("/getallauthors")
    public List<Author> getAllAuthors(){
        return authorRepository.findAll();
    }

    @GetMapping("/getallchapters")
    public List<Chapter> getAllChapters(){return chapterRepository.findAll();}

    @GetMapping("/getauthorwritings")
    public List<Writing> getAllAuthorWritings(){
        Author author = authorRepository.findByName("mohamed");
        return author.getChaptersList();
    }
    @GetMapping("/getchapterwritings")
    public List<Writing> getAllChapterWritings(){
        Chapter chapter = chapterRepository.findByTitle("chapter one");
        return chapter.getAuthorList();
    }
    @GetMapping("/newwriting")
    public Writing createNewWriting(){
        Author author = authorRepository.findByName("mohamed");
        Chapter chapter = chapterRepository.findByTitle("chapter one");
        Writing writing = new Writing(new WritingKey(author.getAuthorId(),chapter.getChapterId()),author,chapter, LocalDateTime.now());
        author.addWriting(writing);
        chapter.addWriting(writing);
        return writingRepository.save(writing);
    }
}
