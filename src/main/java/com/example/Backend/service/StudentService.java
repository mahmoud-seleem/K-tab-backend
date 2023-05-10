package com.example.Backend.service;

import com.example.Backend.Repository.StudentRepository;
import com.example.Backend.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student findById(UUID id){
        return studentRepository.findById(id).orElseThrow();
    }

    public Student addStudent(Student student){
        studentRepository.save(student);
        return student;
    }

    public Student findByEmail(String email){
       return studentRepository.findByStudentEmail(email).get();
    }

}
