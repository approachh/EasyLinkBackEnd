package com.easylink.easylink.vibe_service.application.port.in.offer;

import com.easylink.easylink.vibe_service.application.dto.CreateOfferCommand;
import com.easylink.easylink.vibe_service.application.dto.OfferDto;

public interface CreateOfferUseCase {
    OfferDto create(CreateOfferCommand createOfferCommand);
}
