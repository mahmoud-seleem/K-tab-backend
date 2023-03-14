package com.example.Backend.controller;

import com.example.Backend.Repository.ChapterRepository;
import com.example.Backend.Repository.InteractionRepository;
import com.example.Backend.Repository.StudentRepository;
import com.example.Backend.model.Chapter;
import com.example.Backend.model.Interaction;
import com.example.Backend.model.Student;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inter")
public class InteractionController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private InteractionRepository interactionRepository;

    //    @GetMapping("/newjson")
//    public Interaction newjson(){
//        JSONObject jsonObject = new JSONObject("{\"key\":\"value\"}");
//        Interaction interaction = new Interaction(jsonObject);
//        return interactionRepository.save(interaction);
//    }
    @GetMapping("/newchapter")
    public Chapter creatNewChapter() {
        Chapter chapter = new Chapter("chapter one");
        return chapterRepository.save(chapter);
    }
    @GetMapping("/newstudent")
    public Student creatNewStudent() {
        Student student = new Student("mahmoud");
        return (studentRepository.save(student));
    }
    @GetMapping("/getallstudents")
    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    @GetMapping("/getallchapters")
    public List<Chapter> getAllChapters(){
        return chapterRepository.findAll();
    }

    @GetMapping("/newinter")
    public Interaction createNewInteraction(@RequestBody Map<String,Object> interactionData){
        Chapter chapter = chapterRepository.findByTitle("chapter one");
        Student student = studentRepository.findByName("mahmoud");
        JSONObject data = new JSONObject(interactionData);
        Interaction interaction = new Interaction(data);
        chapter.addInteraction(interaction);
        student.addInteraction(interaction);
        return interactionRepository.save(interaction);
    }

    @GetMapping("/getallinter")
    public List<Interaction> getAllInteractions(){
        return interactionRepository.findAll();
    }
}
