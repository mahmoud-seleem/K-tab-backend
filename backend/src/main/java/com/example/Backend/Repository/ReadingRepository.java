package com.example.Backend.Repository;

import com.example.Backend.model.Chapter;
import com.example.Backend.model.Reading;
import com.example.Backend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReadingRepository extends JpaRepository<Reading, UUID> {
    Reading findByStudentAndChapter(Student student, Chapter chapter);
}
