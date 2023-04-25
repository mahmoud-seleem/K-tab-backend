package com.example.Backend.Repository;

import com.example.Backend.model.Student;
import com.example.Backend.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {
    @Query(
            value = "SELECT * FROM tag WHERE tag.tag_name = ?1",
            nativeQuery = true)
    Tag findByName(String tagName);
}
