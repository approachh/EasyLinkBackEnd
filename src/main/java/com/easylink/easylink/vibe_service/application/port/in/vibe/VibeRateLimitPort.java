package com.easylink.easylink.vibe_service.application.port.in.vibe;

public interface VibeRateLimitPort {
    boolean canCreateVibe(String userId,long existingVibeCountFromDb);
    void decrementVibe(String userId);
}
