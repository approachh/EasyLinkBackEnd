package com.easylink.easylink.services;

import com.easylink.easylink.dtos.ReviewDTO;
import com.easylink.easylink.entities.Review;
import com.easylink.easylink.repositories.ReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ModelMapper modelMapper;

    public ReviewDTO addReview(ReviewDTO reviewDTO){

        System.out.println("⚠️ Add a review: " + reviewDTO);

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
