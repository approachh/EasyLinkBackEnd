package com.easylink.easylink.review_service.application.port;

import com.easylink.easylink.review_service.application.dto.ReviewDTO;
import com.easylink.easylink.review_service.domain.Review;

import java.util.List;

public interface ReviewRepositoryPort {
    Review save(Review review);
    List<Review> findAll();
}
