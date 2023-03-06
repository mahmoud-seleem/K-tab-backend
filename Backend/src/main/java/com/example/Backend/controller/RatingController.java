package com.example.Backend.controller;


import com.example.Backend.model.Book;
import com.example.Backend.model.Rating;
import com.example.Backend.model.RatingKey;
import com.example.Backend.model.Student;
import com.example.Backend.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/rating")
public class RatingController {

    @Autowired
    private RatingService ratingService;


    @GetMapping("/{id}")
    public Rating findRatingById(@PathVariable RatingKey id){
        return ratingService.findRatingById(id);
    }

    @PostMapping()
    public Rating insertRating(@RequestBody Rating rating){
        return ratingService.insertRating(rating);
    }

    @GetMapping("/new")
    public Rating insertSpecificRating(){
        return ratingService.insertSpecificRating();
    }

//    @PutMapping
//    public Rating updateRatingInfo(@RequestBody Rating rating){
//        return ratingService.updateRatingInfo(rating);
//    }

//    if (id == null) {
//        Rating rating = new Rating();
//        UUID studentId = rating.getStudent().getStudentId();
//        UUID bookID = rating.getBook().getBookId();
//        RatingKey id = new RatingKey(studentId, bookID);
//        rating.setId(id);
//    }

}
