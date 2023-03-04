package com.example.Backend.controller;


import com.example.Backend.model.Rating;
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
    public Rating findRatingById(@PathVariable UUID id){
        return ratingService.findRatingById(id);
    }

    @PostMapping()
    public Rating insertRating(@RequestBody Rating rating){
        return ratingService.insertRating(rating);
    }

    @PutMapping
    public Rating updateRatingInfo(@RequestBody Rating rating){
        return ratingService.updateRatingInfo(rating);
    }


}
