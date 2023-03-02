package com.example.Backend.controller;

import com.example.Backend.Repository.StudentCommentRepository;
import com.example.Backend.Repository.StudentRepository;
import com.example.Backend.model.Student;
import com.example.Backend.model.StudentComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentCommentRepository studentCommentRepository;

    @GetMapping("/new")
    public Student saveNewStudent(){
        Student student = new Student("mahmoud");
        StudentComment comment1 = new StudentComment("blablabla");
        student.addStudentComment(comment1);
        Student a = studentRepository.save(student);
        return a;
    }
    @GetMapping("/get/{id}")
    public List<StudentComment> getStudent(@PathVariable UUID id){
        return studentRepository.findById(id).get().getStudentCommentList();
    }

    @GetMapping("/newcomment/{id}")
    public StudentComment saveNewStudentComment(@PathVariable UUID id){
        StudentComment studentComment = new StudentComment("blablablablablabalbala");
        Student a = studentRepository.findById(id).get();
        a.addStudentComment(studentComment);
        StudentComment ac = studentCommentRepository.save(studentComment);
        return ac;
    }
}
