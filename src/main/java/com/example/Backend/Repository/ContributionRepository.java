package com.example.Backend.Repository;

import com.example.Backend.compositeKeys.ContributionKey;
import com.example.Backend.model.Author;
import com.example.Backend.model.Book;
import com.example.Backend.model.Contribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContributionRepository extends JpaRepository<Contribution, UUID> {
    Optional<Contribution> findByAuthorAndBook(Author author, Book book);
}
