package com.easylink.easylink.services;

import com.easylink.easylink.dtos.ReviewDTO;
import com.easylink.easylink.entities.Review;
import com.easylink.easylink.exceptions.RateLimitExceededException;
import com.easylink.easylink.redis.service.RateLimitService;
import com.easylink.easylink.repositories.ReviewRepository;
import com.nimbusds.jose.jwk.source.RateLimitReachedException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;
    private final RateLimitService rateLimitService;

    public ReviewDTO addReview(ReviewDTO reviewDTO){

        if(!rateLimitService.canCreateReview(reviewDTO.getUsername())){
            throw new RateLimitExceededException("You can create only one review per day");
        }

        Review review = reviewRepository.save(modelMapper.map(reviewDTO,Review.class));

        if(review!=null){
            return modelMapper.map(review,ReviewDTO.class);
        }
        throw new RuntimeException("Saving review failed");
    }

    public List<ReviewDTO> findAll(){
        List<Review> reviewList = reviewRepository.findAll();
        return reviewList.stream().map(review -> modelMapper.map(review,ReviewDTO.class)).toList();
    }
}
