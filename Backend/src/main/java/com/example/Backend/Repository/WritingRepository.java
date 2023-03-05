package com.example.Backend.Repository;

import com.example.Backend.compositeKeys.WritingKey;
import com.example.Backend.model.Writing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WritingRepository extends JpaRepository<Writing, WritingKey> {
}
