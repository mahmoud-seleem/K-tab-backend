package com.example.Backend.Repository;

import com.example.Backend.model.Chapter;
import com.example.Backend.model.Reading;
import com.example.Backend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReadingRepository extends JpaRepository<Reading, UUID> {
    Reading findByStudentAndChapter(Student student, Chapter chapter);
}
