package com.easylink.easylink.vibe_service.domain.interaction.offer;

import java.time.Duration;
import java.time.LocalDateTime;

public class DynamicDiscountStrategy implements DiscountStrategy{

    @Override
    public int calculateCurrentDiscount(Offer offer) {
        if(!offer.isActive() || offer.getStartTime()==null || offer.getDecreaseIntervalMinutes()==0){
            return offer.getInitialDiscount();
        }

        long minutesPassed = Duration.between(offer.getStartTime(), LocalDateTime.now()).toMinutes();
        int steps = (int) (minutesPassed/offer.getDecreaseIntervalMinutes());
        int newDiscount = offer.getInitialDiscount()-steps* offer.getDecreaseStep();

        return Math.max(newDiscount,0);

    }
}
