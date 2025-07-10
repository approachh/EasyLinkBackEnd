package com.easylink.easylink.vibe_service.web.dto;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public record InteractionWithOfferResponse(UUID id,
                                           UUID subscriber_vibe_id,
                                           UUID target_vibe_id,
                                           LocalDateTime created_at,
                                           Integer active_offer_count) {
}
