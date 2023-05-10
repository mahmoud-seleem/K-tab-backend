package com.example.Backend.security;

import com.example.Backend.model.Student;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping(path = "students/{id}")
    //@PostAuthorize(value = "hasAuthority('chapter_write')")
    public Student getStudent(@PathVariable int id) {
        Student student = new Student();
        try {
            student = students.get(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return student;
    }

    @GetMapping(path ="admin/")
    public List<String> getStudents(){
        List<String> names = new ArrayList<>();
        for (Student student: students){
            names.add(student.getStudentName());
    }
        return names;
    }


}
