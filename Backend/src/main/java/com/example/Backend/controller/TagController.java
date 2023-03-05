package com.example.Backend.controller;

import com.example.Backend.Repository.ChapterRepository;
import com.example.Backend.Repository.TagRepository;
import com.example.Backend.model.Chapter;
import com.example.Backend.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TagController{
    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private TagRepository tagRepository;

    @GetMapping("/newchapter")
    public Chapter creatNewChapter(){
        Chapter chapter = new Chapter("chapter one");
        return  chapterRepository.save(chapter);
    }
    @GetMapping("/newtag")
    public Tag creatNewtag(){
        Tag tag = new Tag("science");
        return  tagRepository.save(tag);
    }

    @GetMapping("/addtag")
    public Chapter addTag(){
        Tag tag = tagRepository.findByName("science");
        Chapter chapter = chapterRepository.findByTitle("chapter one");
        chapter.addTag(tag);
        tagRepository.save(tag);
        return chapter;
    }
    @GetMapping("/gettags")
    public List<Tag> getChapterTags(){
        //Tag tag = tagRepository.findByName("science");
        Chapter chapter = chapterRepository.findByTitle("chapter one");
        return chapter.getTags();
    }
    @GetMapping("/getchapters")
    public List<Chapter> getTagChapters(){
        Tag tag = tagRepository.findByName("science");
        //Chapter chapter = chapterRepository.findByTitle("chapter one");
        return tag.getChapterList();
    }


}
