package com.easylink.easylink.repositories;

import com.easylink.easylink.review_service.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
