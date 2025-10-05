package com.easylink.easylink.vibe_service.application.port.in.vibe;

public interface VibeRateLimitPort {
    boolean canCreateVibe(String userId);
    void decrementVibe(String userId);
}
