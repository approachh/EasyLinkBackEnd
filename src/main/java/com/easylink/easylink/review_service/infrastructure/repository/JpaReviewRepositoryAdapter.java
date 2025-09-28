package com.easylink.easylink.review_service.infrastructure.repository;

import com.easylink.easylink.review_service.application.port.ReviewRepositoryPort;
import com.easylink.easylink.review_service.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JpaReviewRepositoryAdapter implements ReviewRepositoryPort {

    private final JpaReviewRepository jpaReviewRepository;

    @Override
    public Review save(Review review) {
        return jpaReviewRepository.save(review);
    }

    @Override
    public List<Review> findAll() {
        return jpaReviewRepository.findAll();
    }
}
