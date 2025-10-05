package com.easylink.easylink.etl_service.application.service;

import com.easylink.easylink.etl_service.infrastructure.SpringAmplitudeEventRepository;
import com.easylink.easylink.etl_service.web.dto.AmplitudeEventResponse;
import com.easylink.easylink.vibe_service.domain.model.AmplitudeEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AmplitudeEventQueryService {

    private final SpringAmplitudeEventRepository repository;

    public List<AmplitudeEventResponse> getEventsByUser(String userId, Instant start, Instant end) {
        return repository.findByUserIdAndServerUploadTimeBetween(userId, start, end).stream()
                .map(AmplitudeEventResponse::from)
                .toList();
    }

    public List<AmplitudeEventResponse> getEventsByOffer(String offerId, Instant start, Instant end) {

      //  var events = repository.findByOfferId(offerId);


        List<AmplitudeEventResponse> ae =repository.findByOfferIdAndServerUploadTimeBetween(offerId, start, end).stream()
                .map(AmplitudeEventResponse::from)
                .toList();

        return ae;
    }

//    public List<AmplitudeEventResponse> getEventsByCatalog(String catalogId, Instant start, Instant end) {
//        return repository.findByCatalogIdAndServerUploadTimeBetween(catalogId, start, end).stream()
//                .map(AmplitudeEventResponse::from)
//                .toList();
//    }
}
