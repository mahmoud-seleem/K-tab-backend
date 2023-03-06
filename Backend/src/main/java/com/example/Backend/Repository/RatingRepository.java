package com.example.Backend.Repository;

import com.example.Backend.model.Book;
import com.example.Backend.model.Rating;
import com.example.Backend.model.RatingKey;
import com.example.Backend.service.RatingService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RatingRepository extends JpaRepository<Rating, RatingKey> {
}
