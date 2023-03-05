package com.example.Backend.service;

import com.example.Backend.Repository.RatingRepository;
import com.example.Backend.model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
//
//@Service
//public class RatingService {
//
//    @Autowired
//    private RatingRepository ratingRepository;
//
//
//
//    public Rating findRatingById(UUID id){
//        return ratingRepository.findById(id).orElseThrow();
//    }
//
//    public Rating insertRating(Rating rating){
//        return ratingRepository.save(rating);
//    }
//
////    public Rating updateRatingInfo(Rating rating){
////        Rating currentRating = ratingRepository.findById(rating.getId()).orElseThrow();
////        currentRating.setStudent(rating.getStudent());
////        currentRating.setBook(rating.getBook());
////        return ratingRepository.save(currentRating);
////    }
//}
