package com.example.Backend.controller;

import com.example.Backend.Repository.DisabilityRepository;
import com.example.Backend.Repository.StudentCommentRepository;
import com.example.Backend.Repository.StudentRepository;
import com.example.Backend.model.Disability;
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

    @Autowired
    private DisabilityRepository disabilityRepository;
    @GetMapping("/new")
    public Student saveNewStudent(){
        Student student = new Student("mahmoud");
        //StudentComment comment1 = new StudentComment("blablabla");
        //Disability disability = new Disability("Dyslexia");
        //student.addDisability(disability);
        //student.addStudentComment(comment1);
        //student.getDisabilityList().add(disability);
        Disability d = disabilityRepository.findAll().get(0);
        student.addDisability(d);
        Student a = studentRepository.save(student);
        return a;
    }
    @GetMapping("/getallstudents")
    public List<Student> getStudent(){
        return studentRepository.findAll();
    }
    @GetMapping("/getalldis")
    public List<Disability> getAllDis(){
        return disabilityRepository.findAll();
    }
//    @GetMapping("/get/{id}")
//    public List<StudentComment> getStudent(@PathVariable UUID id){
//        return studentRepository.findById(id).get().getStudentCommentList();
//    }
    @GetMapping("/getstudentdis/{id}")
    public List<Disability> getStudentdis(@PathVariable UUID id){
        return studentRepository.findById(id).get().getDisabilityList();
    }

    @GetMapping("/newcomment/{id}")
    public StudentComment saveNewStudentComment(@PathVariable UUID id){
        StudentComment studentComment = new StudentComment("blablablablablabalbala");
        Student a = studentRepository.findById(id).get();
        a.addStudentComment(studentComment);
        StudentComment ac = studentCommentRepository.save(studentComment);
        return ac;
    }
    @GetMapping("/newdis/{dis}")
    public Disability saveNewdis(@PathVariable String dis){
        Disability disability = new Disability(dis);
        Disability d = disabilityRepository.save(disability);
        return d;
    }
    @GetMapping("/getdisstudents/{id}")
    public List<Student> getstudents(@PathVariable UUID id){
        Disability d = disabilityRepository.findById(id).get();
        return d.getStudentList();
    }

}
