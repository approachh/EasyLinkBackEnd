package com.easylink.easylink.vibe_service.application.port.out;

import com.easylink.easylink.vibe_service.domain.model.EarlyAccessRequest;

public interface EarlyAccessRequestPort {
    EarlyAccessRequest save(EarlyAccessRequest earlyAccessRequest);
}
