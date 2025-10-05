package com.easylink.easylink.review_service.infrastructure.repository;

import com.easylink.easylink.review_service.application.port.ReviewRepositoryPort;
import com.easylink.easylink.review_service.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaReviewRepository extends JpaRepository<Review, UUID>{
}
