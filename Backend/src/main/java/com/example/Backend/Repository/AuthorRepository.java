package com.example.Backend.Repository;

import com.example.Backend.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {

    @Query(
            value = "SELECT * FROM author WHERE author.author_name = ?1",
            nativeQuery = true)
    Author findByName(String userName);

}
