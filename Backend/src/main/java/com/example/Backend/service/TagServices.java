package com.example.Backend.service;

import com.example.Backend.Repository.ChapterRepository;
import com.example.Backend.Repository.TagRepository;
import com.example.Backend.model.Chapter;
import com.example.Backend.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class TagServices {

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private TagRepository tagRepository;

    public Chapter createNewChapter(){
        Chapter chapter = new Chapter("chapter one");
        return chapterRepository.save(chapter);
    }

    public Tag createNewTag(){
        Tag tag = new Tag("science");
        return  tagRepository.save(tag);
    }


    public Chapter addTag(){
        Tag tag = tagRepository.findByName("science");
        Chapter chapter = chapterRepository.findByTitle("chapter one");
        chapter.addTag(tag);
        tagRepository.save(tag);
        return chapter;
    }

    public List<Tag> getChapterTags(){
        //Tag tag = tagRepository.findByName("science");
        Chapter chapter = chapterRepository.findByTitle("chapter one");
        return chapter.getTags();
    }

    public List<Chapter> getTagChapters(){
        Tag tag = tagRepository.findByName("science");
        //Chapter chapter = chapterRepository.findByTitle("chapter one");
        return tag.getChapterList();
    }


}
