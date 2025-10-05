package com.easylink.easylink.review_service.web.controller;

import com.easylink.easylink.review_service.application.dto.ReviewDTO;
import com.easylink.easylink.review_service.application.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v3/reviews")
@CrossOrigin
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewDTO> addReview(@RequestBody ReviewDTO reviewDTO, @AuthenticationPrincipal Jwt jwt) {
        ReviewDTO savedDTO = reviewService.addReview(reviewDTO);
        return ResponseEntity.ok(savedDTO);
    }

    @GetMapping
    public List<ReviewDTO> getAllReviews() {
        return reviewService.findAll();
    }

}
