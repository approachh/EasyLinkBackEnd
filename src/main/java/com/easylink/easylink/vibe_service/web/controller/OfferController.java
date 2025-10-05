package com.easylink.easylink.vibe_service.web.controller;

import com.easylink.easylink.vibe_service.application.dto.CreateOfferCommand;
import com.easylink.easylink.vibe_service.application.dto.OfferDto;
import com.easylink.easylink.vibe_service.application.service.OfferServiceImpl;
import com.easylink.easylink.vibe_service.web.dto.CreateOfferRequest;
import com.easylink.easylink.vibe_service.web.dto.OfferResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v3/offers")
@RequiredArgsConstructor
public class OfferController {

    private final OfferServiceImpl offerService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<OfferResponse> createOffer(@RequestBody CreateOfferRequest createOfferRequest, @AuthenticationPrincipal Jwt jwt){

        OfferDto offerDto = offerService.create(modelMapper.map(createOfferRequest, CreateOfferCommand.class));

        return ResponseEntity.ok(modelMapper.map(offerDto,OfferResponse.class));
    }

    @GetMapping("/vibe/{vibeId}")
    public ResponseEntity<List<OfferResponse>> getOffersByVibeId(@PathVariable UUID vibeId){

        List<OfferDto> offerDtoList = offerService.findAllById(vibeId);

        List<OfferResponse> offerResponseList = offerDtoList.stream().map(offerDto -> {return modelMapper.map(offerDto,OfferResponse.class);}).toList();

        return ResponseEntity.ok(offerResponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferResponse> getOfferById(@PathVariable UUID id){

        OfferDto offerDto = offerService.findOfferById(id);

        OfferResponse offerResponse = modelMapper.map(offerDto,OfferResponse.class);

        return ResponseEntity.ok(offerResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateOffer(@PathVariable UUID id, @RequestBody Map<String,Object> updatedFields, @AuthenticationPrincipal Jwt jwt){

        offerService.updateOfferFields(id,updatedFields);

       return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{offerId}")
    public ResponseEntity<Void> deleteOffer(@PathVariable UUID offerId,
                                            @RequestParam UUID vibeId) {
        offerService.deleteOffer(offerId, vibeId);
        return ResponseEntity.noContent().build();
    }
}
