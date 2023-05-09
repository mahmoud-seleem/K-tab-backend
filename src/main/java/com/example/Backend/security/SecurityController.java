package com.example.Backend.security;

import com.example.Backend.model.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@RestController
@RequestMapping("/api/security/")
public class SecurityController {

    private final List<Student>  students = Arrays.asList(
            new Student("mahmoud"),
            new Student("mohamed"),
            new Student("ahmed mohamed")
    );

    @GetMapping(path = "/{id}")
    public Student getStudent(@PathVariable int id) {
        Student student = new Student();
        try {
            student = students.get(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return student;
    }
}
