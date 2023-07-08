package com.example.Backend.controller;

import com.example.Backend.Repository.ChapterRepository;
import com.example.Backend.Repository.InteractionRepository;
import com.example.Backend.Repository.StudentRepository;
import com.example.Backend.schema.InteractionInfo;
import com.example.Backend.security.JwtService;
import com.example.Backend.service.ChapterService;
import com.example.Backend.validation.InputNotLogicallyValidException;
import com.example.Backend.validation.json.ValidJson;
import com.example.Backend.validation.json.ValidParam;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public InteractionInfo getInteractionInfo(HttpServletRequest request,
            @ValidParam UUID interactionId) throws InputNotLogicallyValidException {
        return chapterService.getInteraction(
                jwtService.getUserId(request), interactionId);
    }
    @PostMapping()
    public InteractionInfo addInteraction(HttpServletRequest request
            ,@ValidJson("InteractionInfo") InteractionInfo interactionInfo) throws InputNotLogicallyValidException {
        interactionInfo.setStudentId(jwtService.getUserId(request));
        return chapterService.addInteraction(interactionInfo);
    }
    @PutMapping()
    public InteractionInfo updateInteraction(HttpServletRequest request
            ,@ValidJson("InteractionInfo") InteractionInfo interactionInfo) throws InputNotLogicallyValidException {
        return chapterService.updateInteraction(interactionInfo);
    }
    @DeleteMapping()
    public List<InteractionInfo> deleteInteraction(HttpServletRequest request,
                                                   @ValidParam UUID interactionId) throws InputNotLogicallyValidException {
        return chapterService.deleteInteraction(
                jwtService.getUserId(request),
                interactionId);
    }
    @GetMapping("all/")
    public List<InteractionInfo> getAllInteractions(HttpServletRequest request
    ,@ValidParam UUID chapterId) throws InputNotLogicallyValidException {
        return chapterService.getInteractions(
                jwtService.getUserId(request),chapterId);
    }
}
