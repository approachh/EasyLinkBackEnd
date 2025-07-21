package com.easylink.easylink.vibe_service.domain.interaction.offer;

import java.util.EnumMap;
import java.util.Map;

public class DiscountStrategyFactory {

    private final Map<DiscountType,DiscountStrategy> strategies = new EnumMap<>(DiscountType.class);

    public DiscountStrategyFactory(){
        strategies.put(DiscountType.DYNAMIC, new DynamicDiscountStrategy());
    }

    public DiscountStrategy getStrategy(DiscountType type){
        return strategies.getOrDefault(type,new DynamicDiscountStrategy());
    }
}
