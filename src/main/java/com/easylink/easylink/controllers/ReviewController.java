package com.easylink.easylink.controllers;

import com.easylink.easylink.dtos.ReviewDTO;
import com.easylink.easylink.services.ReviewService;
import com.easylink.easylink.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v3/reviews")
@CrossOrigin
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewDTO> addReview(@RequestBody ReviewDTO reviewDTO) {
        ReviewDTO savedDTO = reviewService.addReview(reviewDTO);
        return ResponseEntity.ok(savedDTO);
    }

    @GetMapping
    public List<ReviewDTO> getAllReviews() {
        return reviewService.findAll();
    }
}
