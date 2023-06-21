package com.example.Backend.controller;

import com.example.Backend.Repository.ChapterRepository;
import com.example.Backend.Repository.InteractionRepository;
import com.example.Backend.Repository.StudentRepository;
import com.example.Backend.model.Chapter;
import com.example.Backend.model.Interaction;
import com.example.Backend.model.Student;
import com.example.Backend.schema.InteractionInfo;
import com.example.Backend.security.JwtService;
import com.example.Backend.service.ChapterService;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/interaction/")
public class InteractionController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private InteractionRepository interactionRepository;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private ChapterService chapterService;
    @GetMapping()
    public InteractionInfo getInteractionInfo(
            @RequestParam UUID interactionId){
        return chapterService.getInteraction(interactionId);
    }
    @PostMapping()
    public InteractionInfo addInteraction(HttpServletRequest request
            ,@RequestBody InteractionInfo interactionInfo){
        interactionInfo.setStudentId(jwtService.getUserId(request));
        return chapterService.addInteraction(interactionInfo);
    }
    @PutMapping()
    public InteractionInfo updateInteraction(HttpServletRequest request
            ,@RequestBody InteractionInfo interactionInfo){
        return chapterService.updateInteraction(interactionInfo);
    }
    @DeleteMapping()
    public void deleteInteraction(@RequestParam UUID interactionId){
        chapterService.deleteInteraction(interactionId);
    }
    @GetMapping("student-chapter/")
    public List<InteractionInfo> getAllInteractions(HttpServletRequest request
    ,@RequestParam UUID chapterId){
        return chapterService.getInteractions(
                jwtService.getUserId(request),chapterId);
    }
}
