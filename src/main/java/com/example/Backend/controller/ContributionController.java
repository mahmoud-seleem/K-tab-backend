package com.example.Backend.controller;

import com.example.Backend.Repository.AuthorRepository;
import com.example.Backend.Repository.ChapterRepository;
import com.example.Backend.Repository.ContributionRepository;
import com.example.Backend.compositeKeys.ContributionKey;
import com.example.Backend.model.Author;
import com.example.Backend.model.Chapter;
import com.example.Backend.model.Contribution;
import com.example.Backend.schema.BookInfo;
import com.example.Backend.schema.ContributionInfo;
import com.example.Backend.security.JwtService;
import com.example.Backend.service.BookService;
import com.example.Backend.validation.InputNotLogicallyValidException;
import com.example.Backend.validation.json.ValidJson;
import com.example.Backend.validation.json.ValidParam;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/contribution/")
public class ContributionController {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private ContributionRepository contributionRepository;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private BookService bookService;

    @PostMapping()
    public BookInfo addContribution(HttpServletRequest request, @ValidJson("ContributionInfo") ContributionInfo contributionInfo) throws Exception {
        contributionInfo.setOwnerId(jwtService.getUserId(request));
        return bookService.addContribution(contributionInfo);
    }

    public BookInfo addContribution(@ValidJson("ContributionInfo") ContributionInfo contributionInfo) throws Exception {
        return bookService.addContribution(contributionInfo);
    }
    public void addContributionWithoutRes(@ValidJson("ContributionInfo") ContributionInfo contributionInfo) throws Exception {
        bookService.addContributionWithoutRes(contributionInfo);
    }

    @PutMapping
    public BookInfo updateContribution(HttpServletRequest request, @ValidJson("ContributionInfo") ContributionInfo contributionInfo) throws Exception {
        contributionInfo.setOwnerId(jwtService.getUserId(request));
        return bookService.updateContribution(contributionInfo);

    }

    @DeleteMapping
    public BookInfo deleteContribution(HttpServletRequest request, @ValidJson("ContributionInfo") ContributionInfo contributionInfo) throws Exception {
        contributionInfo.setOwnerId(jwtService.getUserId(request));
            return bookService.removeContribution(contributionInfo);
        }
}
//    @GetMapping("/newchapter")
//    public Chapter creatNewChapter(){
//        Chapter chapter = new Chapter("chapter one");
//        return  chapterRepository.save(chapter);
//    }
//    @GetMapping("/newauthor")
//    public Author creatNewAuthor(){
//        Author author = new Author("mohamed");
//        return (authorRepository.save(author));
//    }
//    @GetMapping("/getallauthors")
//    public List<Author> getAllAuthors(){
//        return authorRepository.findAll();
//    }
//
//    @GetMapping("/getallchapters")
//    public List<Chapter> getAllChapters(){return chapterRepository.findAll();}

//    @GetMapping("/getauthorwritings")
//    public List<Contribution> getAllAuthorWritings(){
//        Author author = authorRepository.findByName("mohamed");
//        return author.getChaptersList();
//    }
//    @GetMapping("/getchapterwritings")
//    public List<Contribution> getAllChapterWritings(){
//        Chapter chapter = chapterRepository.findByTitle("chapter one");
//        return chapter.getAuthorList();
//    }
//    @GetMapping("/newwriting")
//    public Contribution createNewWriting(){
//        Author author = authorRepository.findByName("mohamed");
//        Chapter chapter = chapterRepository.findByTitle("chapter one");
//        Contribution contribution = new Contribution(new ContributionKey(author.getAuthorId(),chapter.getChapterId()),author,chapter, LocalDateTime.now());
//        author.addContribution(contribution);
//        chapter.addContribution(contribution);
//        return writingRepository.save(contribution);
//    }
