package com.easylink.easylink.vibe_service.application.service;

import com.easylink.easylink.vibe_service.application.dto.CreateOfferCommand;
import com.easylink.easylink.vibe_service.application.dto.OfferDto;
import com.easylink.easylink.vibe_service.application.port.in.offer.CreateOfferUseCase;
import com.easylink.easylink.vibe_service.application.port.out.VibeRepositoryPort;
import com.easylink.easylink.vibe_service.domain.interaction.offer.Offer;
import com.easylink.easylink.vibe_service.domain.model.Vibe;
import com.easylink.easylink.vibe_service.domain.model.VibeType;
import com.easylink.easylink.vibe_service.infrastructure.repository.JpaOfferRepositoryAdapter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements CreateOfferUseCase {

    private final ModelMapper modelMapper;
    private final VibeRepositoryPort vibeRepositoryPort;
    private final JpaOfferRepositoryAdapter jpaOfferRepositoryAdapter;

    @Override
    public OfferDto create(CreateOfferCommand createOfferCommand) {

        UUID vibeId = createOfferCommand.getVibeId();
        Vibe vibe = vibeRepositoryPort.findById(vibeId).orElseThrow(()->new IllegalArgumentException("Vibe not found"));
//        Optional<Vibe> opvibe = vibeRepositoryPort.findById(vibeId);
//        Vibe vibe = opvibe.get();

        if(!VibeType.BUSINESS.equals(vibe.getType())){
            throw new IllegalArgumentException("Offer creation is allowed only for BUSINESS vibes");
        }

        Offer offer = modelMapper.map(createOfferCommand, Offer.class);
        offer.setVibe(vibe);
        offer.setActive(true);
        offer.setStartTime(LocalDateTime.now());
        offer.setStartTime(createOfferCommand.getEndTime());

        Offer offerSaved = jpaOfferRepositoryAdapter.save(offer);

        OfferDto offerDto = modelMapper.map(offerSaved, OfferDto.class);
        offerDto.setVibeId(vibe.getId());
        return offerDto;
    }

    public List<OfferDto> findAllById(UUID id){
        Vibe vibe = vibeRepositoryPort.findById(id).orElseThrow(()->new IllegalArgumentException("Vibe not found"));

        List<Offer> offerList = jpaOfferRepositoryAdapter.findAllByVibe(vibe);

        List<OfferDto> offerDtoList = offerList.stream().map(offer -> modelMapper.map(offer,OfferDto.class)).toList();

        return offerDtoList;
    }
}
