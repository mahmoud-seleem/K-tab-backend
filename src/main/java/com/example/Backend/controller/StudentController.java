package com.example.Backend.controller;

import com.example.Backend.Repository.*;
import com.example.Backend.model.*;
import com.example.Backend.schema.*;
import com.example.Backend.security.JwtService;
import com.example.Backend.service.StudentService;
import com.example.Backend.validation.InputNotLogicallyValidException;
import com.example.Backend.validation.json.ValidJson;
import com.example.Backend.validation.json.ValidParam;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/student/")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;


    @Autowired
    private StudentNotificationRepository studentNotificationRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private StudentSettingsRepository studentSettingsRepository;
    @Autowired
    private DisabilityRepository disabilityRepository;

    @PostMapping("signup/")
    public StudentSignUpResponse saveSignUpData(@ValidJson("StudentSignUpForm") StudentSignUpForm studentSignUpForm) throws Exception {
            return studentService.saveNewStudent(studentSignUpForm);
    }

    @PutMapping
    public StudentSignUpResponse updateStudentProfileInfo(HttpServletRequest request
            , @ValidJson("StudentSignUpForm") StudentSignUpForm studentSignUpForm) throws Exception {
        studentSignUpForm.setStudentId(jwtService.getUserId(request));
        return studentService.updateStudentInfo(studentSignUpForm);
    }

    @GetMapping
    public StudentSignUpResponse getStudentProfileInfo(HttpServletRequest request) throws InputNotLogicallyValidException {
        return studentService.getStudentInfo(jwtService.getUserId(request));
    }
    @GetMapping("/profile/")
    public StudentProfile getStudentProfileInfo(@ValidParam UUID studentId) throws InputNotLogicallyValidException {
        return studentService.getAuthorProfileInfo(studentId);
    }

//    @GetMapping("/new")
//    public Student saveNewStudent() {
//        Student student = new Student("mahmoud");
//        return studentRepository.save(student);
//    }
//
//    @GetMapping("/getallstudents/")
//    public List<Student> getStudent() {
//        return studentRepository.findAll();
//    }
//
//    @GetMapping("/newcomment/{id}")
//    public StudentComment saveNewStudentComment(@PathVariable UUID id) {
//        StudentComment studentComment = new StudentComment("blablablablablabalbala");
//        Student a = studentRepository.findById(id).get();
//        a.addStudentComment(studentComment);
//        StudentComment ac = studentCommentRepository.save(studentComment);
//        return ac;
//    }
//
//    @GetMapping("/getcomments/{id}")
//    public List<StudentComment> getAllStudentComments(@PathVariable UUID id) {
//        Student a = studentRepository.findById(id).get();
//        return a.getStudentCommentList();
//    }
//
//    @GetMapping("/getcommentstudent/{id}")
//    public Student getCommentStudent(@PathVariable UUID id) {
//        StudentComment studentComment = studentCommentRepository.findById(id).get();
//        return studentComment.getStudent();
//    }
//
//    @GetMapping("/newsettings/{id}")
//    public StudentSettings newStudentSettings(@PathVariable UUID id) {
//        Student student = studentRepository.findById(id).get();
//        StudentSettings studentSettings = new StudentSettings();
//        student.setStudentSettings(studentSettings);
//        return studentSettingsRepository.save(studentSettings);
//    }
//
//    @GetMapping("/getstudentsettings/{id}")
//    public StudentSettings getStudentSettings(@PathVariable UUID id) {
//        return studentRepository.findById(id).get().getStudentSettings();
//    }
//
//    @GetMapping("/getsettingsauthor/{id}")
//    public Student getSettingsStudent(@PathVariable UUID id) {
//        return studentSettingsRepository.findById(id).get().getStudent();
//    }
//
//    @GetMapping("/getalldis")
//    public List<Disability> getAllDis() {
//        return disabilityRepository.findAll();
//    }

    //    @GetMapping("/get/{id}")
//    public List<StudentComment> getStudent(@PathVariable UUID id){
//        return studentRepository.findById(id).get().getStudentCommentList();
//    }
//    @GetMapping("/getstudentdis/{id}")
//    public List<Disability> getStudentdis(@PathVariable UUID id) {
//        return studentRepository.findById(id).get().getDisabilityList();
//    }

//    @GetMapping("/newdis/{dis}")
//    public Disability saveNewdis(@PathVariable String dis) {
//        Disability disability = new Disability(dis);
//        return disabilityRepository.save(disability);
//    }

//    @GetMapping("/getdisstudents/{id}")
//    public List<Student> getstudents(@PathVariable UUID id) {
//        Disability d = disabilityRepository.findById(id).get();
//        return d.getStudentList();
//    }

//    @GetMapping("/adddistostudent/{disId}/{stdId}")
//    public Disability addDisToStudent(@PathVariable UUID disId, @PathVariable UUID stdId) {
//        Disability disability = disabilityRepository.findById(disId).get();
//        Student student = studentRepository.findById(stdId).get();
//        student.addDisability(disability);
//        return disabilityRepository.save(disability);
//    }

//    @PostMapping("/newstudent/")
//    public Student createNewStudent(@RequestBody Student student){
//        for(Disability d : student.getDisabilityList()){
//            disabilityRepository.save(d);
//        }
//        return studentRepository.save(student);
//    }
//    @GetMapping("get/{id}")
//    public Student getStudentById(@PathVariable UUID id){
//        return studentService.findById(id);
//
//    }
//
//
//    @CrossOrigin
//    @PostMapping
//    public Student addStudent(@RequestBody Student student){
//        return studentService.addStudent(student);
//    }
//
//    @GetMapping("/get")
//    public Student getByEmail(@RequestParam(value="email") String email){
//        return studentService.findByEmail(email);
//    }
//
//
////    @GetMapping("check/{email}")
////    public String existByEmail(@PathVariable String email){
////         if(studentService.existByEmail(email)){
////             return "student";
////
////         }
////         else{
////             return "none";
////         }
////    }
//
}
