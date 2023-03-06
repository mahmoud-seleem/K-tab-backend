package com.example.Backend.service;

import com.example.Backend.Repository.RatingRepository;
import com.example.Backend.model.Book;
import com.example.Backend.model.Rating;
import com.example.Backend.model.RatingKey;
import com.example.Backend.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;



    public Rating findRatingById(RatingKey id){
        if (ratingRepository.findById(id) == null) {
            Rating rating = new Rating();
            UUID studentId = rating.getStudent().getStudentId();
            UUID bookID = rating.getBook().getBookId();
            RatingKey Nid = new RatingKey(studentId, bookID);
            rating.setId(Nid);
        }
        return ratingRepository.findById(id).orElseThrow();
    }

    public Rating insertRating(Rating rating){
        return ratingRepository.save(rating);
    }

//    public Rating updateRatingInfo(Rating rating){
//        Rating currentRating = ratingRepository.findById(rating.getId()).orElseThrow();
//        currentRating.setStudent(rating.getStudent());
//        currentRating.setBook(rating.getBook());
//        return ratingRepository.save(currentRating);
//    }

    public Rating insertSpecificRating(){
        Book book = new Book("Mariam's fav book");
        Student student = new Student("Mariam");
        Rating rating = new Rating(book, student, 5);
        return ratingRepository.save(rating);
    }
}
