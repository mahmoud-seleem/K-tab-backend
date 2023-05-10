package com.example.Backend.security;

import com.example.Backend.Repository.AuthorRepository;
import com.example.Backend.Repository.StudentRepository;
import com.example.Backend.model.Author;
import com.example.Backend.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import static com.example.Backend.security.Role.ADMIN;
import static com.example.Backend.security.Role.STUDENT;

@Service("DataBase")
public class AppUserDetailsDaoFromDataBase implements AppUserDetailsDao{
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AuthorRepository authorRepository;
    @Override
    public Optional<AppUserDetails> findAppUserDetailsByUserName(String userName) {
        AppUser user;
        try{
            user = authorRepository.findByAuthorEmail(userName).orElseThrow();
        }catch (Exception e){
            try {
            user = studentRepository.findByStudentEmail(userName).orElseThrow();
            }catch (Exception exception){
                throw new NoSuchElementException();
            }
        }
        List<AppUserDetails> userDetails = new ArrayList<>();
        userDetails.add(constructAppUserDetails(user));
        return userDetails.stream().findAny();
    }
    private AppUserDetails constructAppUserDetails(AppUser user){
        if (user instanceof Author){
            Author author = (Author) user;
            return createUserDetailsFromAuthor(author);
        }
        else {
            Student student = (Student) user;
            return createUserDetailsFromStudent(student);
        }
    }

    private AppUserDetails createUserDetailsFromAuthor(Author author){
        return new AppUserDetails(
                author.getAuthorEmail(),
                author.getPassword(),
                ADMIN.getGrantedAuthorities()
                        .stream().toList(),
                true,
                true,
                true,
                true
        );
    }
    private AppUserDetails createUserDetailsFromStudent(Student student){
        return new AppUserDetails(
                student.getStudentEmail(),
                student.getPassword(),
                STUDENT.getGrantedAuthorities().stream().toList(),
                true,
                true,
                true,
                true
        );
    }
}

