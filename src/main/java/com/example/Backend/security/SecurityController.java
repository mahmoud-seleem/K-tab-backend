package com.example.Backend.security;

import com.example.Backend.model.Student;
import com.example.Backend.schema.LoginForm;
import com.example.Backend.schema.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    public LoginResponse login(@RequestBody LoginForm loginForm){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginForm.getEmail(),
                        loginForm.getPassword()
                )
        );
//        UserDetails userDetails =
//                appUserDetailsService.
//                        loadUserByUsername(loginForm.getEmail());

        AppUserDetails userDetails =
                (AppUserDetails)authentication.getPrincipal();
        LoginResponse response = new LoginResponse();
        response.setToken(jwtService.generateJwtToken(authentication));
        response.setUserType(userDetails.getUserType());
        response.setUserId(userDetails.getUserId());
        return response;
    }

    @GetMapping(path = "students/{id}")
    @PostAuthorize(value = "hasAuthority('chapter_write')")
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
    @PutMapping(path ="user/")
    public UUID getUser(HttpServletRequest request){
        return jwtService.getUserId(request);
    }

}
