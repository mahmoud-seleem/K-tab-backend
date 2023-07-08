package com.example.Backend.service;

import com.example.Backend.Repository.*;
import com.example.Backend.jsonConversion.JsonConverter;
import com.example.Backend.model.*;
import com.example.Backend.s3Connection.S3fileSystem;
import com.example.Backend.schema.*;
import com.example.Backend.security.AppUserDetailsService;
import com.example.Backend.security.JwtService;
import com.example.Backend.utils.ImageConverter;
import com.example.Backend.utils.Utils;
import com.example.Backend.validation.InputNotLogicallyValidException;
import com.example.Backend.validation.ValidationUtils;
import com.example.Backend.validation.helpers.StudentValidation;
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
import java.util.Map;
import java.util.UUID;

@Service
public class StudentService {

    @Autowired
    private DisabilityRepository disabilityRepository;
    @Autowired
    private StudentDisabilityRepository studentDisabilityRepository;
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

    @Autowired
    private ValidationUtils validationUtils;
    @Autowired
    private StudentValidation studentValidation;

    public StudentSignUpResponse saveNewStudent(StudentSignUpForm form) throws Exception {
        studentValidation.validateStudentSignUpForm(form);
        Student student = createNewStudent(form);
        String jwtToken = authenticateNewStudent(form);
        return constructResponse(student, jwtToken);
    }

    private String authenticateNewStudent(StudentSignUpForm form) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        form.getStudentEmail(),
                        form.getPassword()
                )
        );
        return jwtService.generateJwtToken(authentication);
    }

    public StudentSignUpResponse updateStudentInfo(StudentSignUpForm form) throws Exception {
        Student student = studentValidation.validateStudentUpdateForm(form);
        updateStudentData(form, student);
        return constructResponse(studentRepository.save(student));
    }

    public StudentSignUpResponse getStudentInfo(UUID studentId) throws InputNotLogicallyValidException {
        return constructResponse(
                validationUtils.checkStudentIsExisted(studentId)
        );
    }

    public StudentProfile getAuthorProfileInfo(UUID studentId) throws InputNotLogicallyValidException {
        return constructProfile(
                validationUtils.checkStudentIsExisted(studentId)
        );
    }

    private String storeProfilePhotoPath(Student student) {
        String photoPath = ("Students/" + student.getStudentId().toString() + "/profilePhoto.png");
        s3fileSystem.reserveEmptyPlace(photoPath);
        student.setProfilePhoto(photoPath);
        return photoPath;
    }

    private void setupProfilePhoto(Student student, StudentSignUpForm form) {
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
            if (field.get(form) != null &&
                    (!field.getName().equals(
                            "profilePhotoAsBinaryString")) &&
                    (!field.getName().equals(
                            "studentId"))) {
                updateField(field, form, student);
            }
        }
        setupProfilePhoto(student, form);
        return studentRepository.save(student);
    }

    private void updateField(Field field, StudentSignUpForm form, Student student) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        switch (field.getName()) {
            case "password": {
                updatePassword(form, student);
                break;
            }
            case "disabilities": {
                updateStudentDisabilities(form, student);
                break;
            }
            default: {
                utils.getMethodBySignature("set", field
                        , student, field.getType()).invoke(student, field.get(form));
            }
        }

    }

    private void updateStudentDisabilities(StudentSignUpForm form, Student student) {
        List<DisabilityHeader> disabilitiesInfo = form.getDisabilities();
        List<StudentDisability> studentDisabilityList = student.getStudentDisabilityList();
        for (StudentDisability studentDisability : studentDisabilityList) {
            studentDisabilityRepository.delete(studentDisability);
        }
        student.getStudentDisabilityList().clear();
        for (DisabilityHeader disabilityHeader : disabilitiesInfo) {
            connectStudentWithDisabilities(disabilityHeader, student);
        }
    }

    private void connectStudentWithDisabilities(DisabilityHeader disabilityHeader, Student student) {
        Disability defaultDisability = disabilityRepository.
                findByDisabilityName(disabilityHeader.getName());
        StudentDisability studentDisability = new StudentDisability(
                disabilityHeader.getDetails(),
                student, defaultDisability);
        studentDisability = studentDisabilityRepository.save(studentDisability);
        student.addStudentDisability(studentDisability);
        defaultDisability.addStudentDisability(studentDisability);
        studentRepository.save(student);
        disabilityRepository.save(defaultDisability);
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
                student.getDisabilitiesInfo()
        );
    }

    private StudentProfile constructProfile(Student student) {
        return new StudentProfile(
                student.getStudentId(),
                student.getStudentName(),
                student.getStudentEmail(),
                null,
                null,
                student.getProfilePhoto(),
                student.getEducationLevel(),
                null,
                null
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
                student.getDisabilitiesInfo(),
                jwtToken
        );
    }

    private void updatePassword(StudentSignUpForm form, Student student) {
        if (form.getPassword() != null) {
            student.setPassword(
                    passwordEncoder
                            .encode(form.getPassword()));
        }
    }
}
