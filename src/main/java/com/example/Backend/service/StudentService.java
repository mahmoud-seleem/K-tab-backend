package com.example.Backend.service;

import com.example.Backend.Repository.*;
import com.example.Backend.jsonConversion.JsonConverter;
import com.example.Backend.model.Author;
import com.example.Backend.model.AuthorSettings;
import com.example.Backend.model.Student;
import com.example.Backend.model.StudentSettings;
import com.example.Backend.s3Connection.S3fileSystem;
import com.example.Backend.schema.AuthorSignUpForm;
import com.example.Backend.schema.AuthorSignUpResponse;
import com.example.Backend.schema.StudentSignUpForm;
import com.example.Backend.schema.StudentSignUpResponse;
import com.example.Backend.security.AppUserDetailsService;
import com.example.Backend.security.JwtService;
import com.example.Backend.utils.ImageConverter;
import com.example.Backend.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private StudentSettingsRepository studentSettingsRepository;
    @Autowired
    private S3fileSystem s3fileSystem;
    @Autowired
    private ImageConverter imageConverter;
    @Autowired
    private Utils utils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private JsonConverter jsonConverter;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AppUserDetailsService appUserDetailsService;

    public StudentSignUpResponse saveNewStudent(StudentSignUpForm form) throws Exception {
        Student student = createNewStudent(form);
        String jwtToken = authenticateNewAuthor(form);
        return constructResponse(student, jwtToken);
    }

    private String authenticateNewAuthor(StudentSignUpForm form) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        form.getStudentEmail(),
                        form.getPassword()
                )
        );
        return jwtService.generateJwtToken(authentication);
    }

    public StudentSignUpResponse updateAuthorInfo(StudentSignUpForm form) throws Exception {
        Student student = studentRepository.findById(form.getStudentId()).get()
        updateStudentData(form,student);
        return constructResponse(studentRepository.save(student));
    }

    public StudentSignUpResponse getAuthorInfo(UUID studentId) {
        return constructResponse(
                studentRepository
                        .findById(studentId)
                        .get()
        );
    }

    private String storeProfilePhotoPath(Student student) {
        String photoPath = ("Students/" + student.getStudentId().toString() + "/profilePhoto.png");
        s3fileSystem.reserveEmptyPlace(photoPath);
        student.setProfilePhoto(photoPath);
        return photoPath;
    }

    private void setupProfilePhoto(Student student,StudentSignUpForm form) {
        String photoPath = storeProfilePhotoPath(student);
        if (form.getProfilePhotoAsBinaryString() != null) {
            InputStream inputStream = imageConverter.convertImgToFile(
                    form.getProfilePhotoAsBinaryString());
            s3fileSystem.uploadPhoto(photoPath, inputStream);
        }
    }

    private Student createNewStudent(StudentSignUpForm form) throws Exception {
        Student student = new Student(
                form.getStudentName(),
                form.getStudentEmail(),
                passwordEncoder.encode(form.getPassword()));
        return updateStudentData(form,
                createDefaultSettings(student));
    }

    private Student createDefaultSettings(Student student) {
        StudentSettings studentSettings = new StudentSettings();
        studentSettingsRepository.save(studentSettings);
        student.setStudentSettings(studentSettings);
        return studentRepository.save(student);
    }

    private Student updateStudentData(StudentSignUpForm form, Student student) throws Exception {
        List<Field> fields = new ArrayList<>();
        fields = utils.getAllFields(fields, StudentSignUpForm.class);
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.get(form) != null) {
                updateField(field,form,student);
            }
        }
        return studentRepository.save(student);
}

    private void updateField(Field field ,StudentSignUpForm form,Student student) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        switch (field.getName()) {
            case "profilePhotoAsBinaryString": {
                setupProfilePhoto(student, form);
            }
            case "password": {
                updatePassword(form, student);
            }
            case "disabilities": {

            }
            default: {
                utils.getMethodBySignature("set", field
                        , student, field.getType()).invoke(student, field.get(form));
            }
        }

    }
    private StudentSignUpResponse constructResponse(Student student) {
        return new StudentSignUpResponse(
                student.getStudentId(),
                student.getStudentName(),
                student.getStudentEmail(),
                student.getContact(),
                student.getStudentSettings().getStudentSettingsId(),
                student.getProfilePhoto(),
                student.getEducationLevel(),
                student.getDisabilitiesNames()
        );
    }

    private StudentSignUpResponse constructResponse(Student student, String jwtToken) {
        return new StudentSignUpResponse(
                student.getStudentId(),
                student.getStudentName(),
                student.getStudentEmail(),
                student.getContact(),
                student.getStudentSettings().getStudentSettingsId(),
                student.getProfilePhoto(),
                student.getEducationLevel(),
                student.getDisabilitiesNames(),
                jwtToken
        );
    }

    private void updatePassword(StudentSignUpForm form,Student student) {
        if (form.getPassword() != null) {
            student.setPassword(
                    passwordEncoder
                            .encode(form.getPassword()));
        }
    }
}
