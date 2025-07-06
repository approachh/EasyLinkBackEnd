package com.easylink.easylink.vibe_service.web.controller;

import com.easylink.easylink.vibe_service.application.dto.CreateOfferCommand;
import com.easylink.easylink.vibe_service.application.dto.OfferDto;
import com.easylink.easylink.vibe_service.application.port.in.offer.CreateOfferUseCase;
import com.easylink.easylink.vibe_service.application.service.OfferServiceImpl;
import com.easylink.easylink.vibe_service.web.dto.CreateOfferRequest;
import com.easylink.easylink.vibe_service.web.dto.OfferResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
