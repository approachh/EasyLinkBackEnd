package com.easylink.easylink.vibe_service.application.service;

import com.easylink.easylink.vibe_service.application.dto.CreateOfferCommand;
import com.easylink.easylink.vibe_service.application.dto.OfferDto;
import com.easylink.easylink.vibe_service.application.port.in.offer.CreateOfferUseCase;
import com.easylink.easylink.vibe_service.application.port.out.VibeRepositoryPort;
import com.easylink.easylink.vibe_service.domain.interaction.offer.Offer;
import com.easylink.easylink.vibe_service.domain.model.Vibe;
import com.easylink.easylink.vibe_service.domain.model.VibeType;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements CreateOfferUseCase {

    private final ModelMapper modelMapper;
    private final VibeRepositoryPort vibeRepositoryPort;

    @Override
    public OfferDto create(CreateOfferCommand createOfferCommand) {

        UUID vibeId = createOfferCommand.getVibeId();
        Vibe vibe = vibeRepositoryPort.findById(vibeId).orElseThrow(()->new IllegalArgumentException("Vibe not found"));

        if(!VibeType.BUSINESS.equals(vibe.getType())){
            throw new IllegalArgumentException("Offer creation is allowed only for BUSINESS vibes");
        }

        Offer offer = modelMapper.map(createOfferCommand, Offer.class);
        offer.setVibe(vibe);

        OfferDto offerDto = modelMapper.map(offer, OfferDto.class);
        offerDto.setVibeId(vibe.getId());
        return offerDto;
    }
}
