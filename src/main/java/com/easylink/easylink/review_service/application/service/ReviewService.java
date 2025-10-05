package com.easylink.easylink.review_service.application.service;

import com.easylink.easylink.review_service.application.dto.ReviewDTO;
import com.easylink.easylink.review_service.application.port.ReviewRepositoryPort;
import com.easylink.easylink.review_service.domain.Review;
import com.easylink.easylink.exceptions.RateLimitExceededException;
import com.easylink.easylink.repositories.ReviewRepository;
import com.easylink.easylink.review_service.infrastructure.redis.RedisRateLimiterAdapter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepositoryPort reviewRepositoryPort;
    private final ModelMapper modelMapper;
    private final RedisRateLimiterAdapter redisRateLimiterAdapter;

    public ReviewDTO addReview(ReviewDTO reviewDTO){

        if(!redisRateLimiterAdapter.canCreateReview(reviewDTO.getUsername())){
            throw new RateLimitExceededException("You can create only one review per day");
        }

        Review review = reviewRepositoryPort.save(modelMapper.map(reviewDTO,Review.class));

        if(review!=null){
            return modelMapper.map(review,ReviewDTO.class);
        }
        throw new RuntimeException("Saving review failed");
    }

    public List<ReviewDTO> findAll(){
        List<Review> reviewList = reviewRepositoryPort.findAll();
        return reviewList.stream().map(review -> modelMapper.map(review,ReviewDTO.class)).toList();
    }
}
