package com.example.Backend.service;

import com.example.Backend.controller.StudentController;
import com.example.Backend.model.Author;
import com.example.Backend.model.Student;
import com.example.Backend.schema.AuthorProfile;
import com.example.Backend.schema.UserInfo;
import com.example.Backend.security.AppUser;
import com.example.Backend.validation.InputNotLogicallyValidException;
import com.example.Backend.validation.ValidationUtils;
import com.google.j2objc.annotations.AutoreleasePool;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private ValidationUtils validationUtils;

    public UserInfo getStudentInfo(UUID studentId) throws InputNotLogicallyValidException {
        Student student = validationUtils.checkStudentIsExisted(studentId);
        return createUserInfoFromStudent(student);
    }
    public UserInfo getAuthorInfo(UUID authorId) throws InputNotLogicallyValidException {
        Author author = validationUtils.checkAuthorIsExisted(authorId);
        return createUserInfoFromAuthor(author);
    }
    private UserInfo createUserInfoFromStudent(Student student){
        return new UserInfo(
                student.getStudentId(),
                student.getStudentName(),
                student.getStudentEmail(),
                "STUDENT",
                null,
                student.getProfilePhoto()
        );
    }
    private UserInfo createUserInfoFromAuthor(Author author){
        return new UserInfo(
                author.getAuthorId(),
                author.getAuthorName(),
                author.getAuthorEmail(),
                "AUTHOR",
                null,
                author.getProfilePhoto()
        );
    }
}
