package com.example.Backend.controller;

import com.example.Backend.model.Chapter;
import com.example.Backend.model.Tag;
import com.example.Backend.service.TagServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TagController{

    @Autowired
    private TagServices tagServices;

    @GetMapping("/newchapter")
    public Chapter creatNewChapter(){

        return  tagServices.createNewChapter();
    }
    @GetMapping("/newtag")
    public Tag creatNewtag(){
        return  tagServices.createNewTag();
    }

    @GetMapping("/addtag")
    public Chapter addTag(){
        return tagServices.addTag();
    }

    @GetMapping("/gettags")
    public List<Tag> getChapterTags(){
        return tagServices.getChapterTags();
    }

    @GetMapping("/getchapters")
    public List<Chapter> getTagChapters(){
       return tagServices.getTagChapters();
    }


}
