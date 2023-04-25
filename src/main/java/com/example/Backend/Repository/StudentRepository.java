package com.example.Backend.Repository;

import com.example.Backend.model.Author;
import com.example.Backend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    @Query(
            value = "SELECT * FROM student WHERE student.student_name = ?1",
            nativeQuery = true)
    Student findByName(String userName);

//    @Query(
//            value = "SELECT * FROM student WHERE student.student_email = ?1",
//            nativeQuery = true)
    Student findByStudentEmail(String studentEmail);

}
