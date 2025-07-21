package com.easylink.easylink.vibe_service.domain.interaction.offer;

public interface DiscountStrategy {
    int calculateCurrentDiscount(Offer offer);
}
