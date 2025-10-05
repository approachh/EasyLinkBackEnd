package com.easylink.easylink.etl_service.infrastructure;

import com.easylink.easylink.vibe_service.domain.model.AmplitudeEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public  interface SpringAmplitudeEventRepository extends JpaRepository<AmplitudeEvent, UUID> {
    List<AmplitudeEvent> findByUserIdAndServerUploadTimeBetween(String userId, Instant start, Instant end);
    List<AmplitudeEvent> findByOfferIdAndServerUploadTimeBetween(String offerId, Instant start, Instant end);
    AmplitudeEvent findByOfferId(String offerId);
 //   List<AmplitudeEvent> findByCatalogIdAndServerUploadTimeBetween(String catalogId, Instant start, Instant end);
}
