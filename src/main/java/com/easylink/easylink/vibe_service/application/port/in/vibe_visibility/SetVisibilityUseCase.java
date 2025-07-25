package com.easylink.easylink.vibe_service.application.port.in.vibe_visibility;

import java.util.UUID;

public interface SetVisibilityUseCase {
    String setVisibility(UUID vibeId);
    boolean cancelVisibility(UUID vibeId);
}
