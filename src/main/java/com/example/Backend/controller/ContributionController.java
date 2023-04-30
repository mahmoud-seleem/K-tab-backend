package com.example.Backend.controller;

import com.example.Backend.Repository.AuthorRepository;
import com.example.Backend.Repository.ChapterRepository;
import com.example.Backend.Repository.ContributionRepository;
import com.example.Backend.compositeKeys.ContributionKey;
import com.example.Backend.model.Author;
import com.example.Backend.model.Chapter;
import com.example.Backend.model.Contribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/writing")
public class ContributionController {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private ContributionRepository writingRepository;
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
    public List<Contribution> getAllAuthorWritings(){
        Author author = authorRepository.findByName("mohamed");
        return author.getChaptersList();
    }
    @GetMapping("/getchapterwritings")
    public List<Contribution> getAllChapterWritings(){
        Chapter chapter = chapterRepository.findByTitle("chapter one");
        return chapter.getAuthorList();
    }
    @GetMapping("/newwriting")
    public Contribution createNewWriting(){
        Author author = authorRepository.findByName("mohamed");
        Chapter chapter = chapterRepository.findByTitle("chapter one");
        Contribution contribution = new Contribution(new ContributionKey(author.getAuthorId(),chapter.getChapterId()),author,chapter, LocalDateTime.now());
        author.addContribution(contribution);
        chapter.addContribution(contribution);
        return writingRepository.save(contribution);
    }
}
