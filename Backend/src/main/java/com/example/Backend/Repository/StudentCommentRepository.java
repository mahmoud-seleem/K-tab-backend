package com.example.Backend.Repository;

import com.example.Backend.model.StudentComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentCommentRepository extends JpaRepository<StudentComment, UUID> {
}
