package com.easylink.easylink.review_service.application.port;

public interface RateLimitPort {
    boolean canCreateReview(String userId);
}
