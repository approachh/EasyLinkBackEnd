package com.easylink.easylink.repositories;

import com.easylink.easylink.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
