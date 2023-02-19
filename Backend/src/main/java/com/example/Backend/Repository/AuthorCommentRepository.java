package com.example.Backend.Repository;

import com.example.Backend.model.AuthorComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorCommentRepository extends JpaRepository<AuthorComment, UUID> {
}
