package com.example.Backend.security;

import com.example.Backend.model.Student;
import com.example.Backend.schema.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/security/")
public class SecurityController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AppUserDetailsService appUserDetailsService;
    private final List<Student>  students = Arrays.asList(
            new Student("mahmoud"),
            new Student("mohamed"),
            new Student("ahmed mohamed")
    );

    @PostMapping(path = "login/")
    public String login(@RequestBody LoginForm loginForm){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginForm.getEmail(),
                        loginForm.getPassword()
                )
        );
//        UserDetails userDetails =
//                appUserDetailsService.
//                        loadUserByUsername(loginForm.getEmail());
        return jwtService.generateJwtToken(authentication);
    }
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
