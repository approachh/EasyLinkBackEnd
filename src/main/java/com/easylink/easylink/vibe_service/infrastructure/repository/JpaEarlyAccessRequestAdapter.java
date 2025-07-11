package com.easylink.easylink.vibe_service.infrastructure.repository;

import com.easylink.easylink.vibe_service.application.port.out.EarlyAccessRequestPort;
import com.easylink.easylink.vibe_service.domain.model.EarlyAccessRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaEarlyAccessRequestAdapter implements EarlyAccessRequestPort {

    private final SpringDataEarlyAccessRequestRepository springDataEarlyAccessRequestRepository;


    @Override
    public EarlyAccessRequest save(EarlyAccessRequest earlyAccessRequest) {

        EarlyAccessRequest earlyAccessRequestSaved = springDataEarlyAccessRequestRepository.save(earlyAccessRequest);

        return earlyAccessRequestSaved;
    }
}
