package com.easylink.easylink.vibe_service.application.service;

import com.easylink.easylink.vibe_service.application.dto.CreateOfferCommand;
import com.easylink.easylink.vibe_service.application.dto.OfferDto;
import com.easylink.easylink.vibe_service.application.port.in.offer.CreateOfferUseCase;
import com.easylink.easylink.vibe_service.application.port.out.VibeRepositoryPort;
import com.easylink.easylink.vibe_service.domain.interaction.offer.DiscountType;
import com.easylink.easylink.vibe_service.domain.interaction.offer.Offer;
import com.easylink.easylink.vibe_service.domain.model.Vibe;
import com.easylink.easylink.vibe_service.domain.model.VibeType;
import com.easylink.easylink.vibe_service.infrastructure.exception.OfferUpdateException;
import com.easylink.easylink.vibe_service.infrastructure.repository.JpaOfferRepositoryAdapter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements CreateOfferUseCase {

    private final ModelMapper modelMapper;
    private final VibeRepositoryPort vibeRepositoryPort;
    private final JpaOfferRepositoryAdapter jpaOfferRepositoryAdapter;
    private final AmplitudeService amplitudeService;

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

        amplitudeService.sendEvent(
                vibe.getName(),
                "Created Offer",
                Map.of(
                        "offerId", offerSaved.getId(),
                        "vibeId", vibe.getId(),
                        "title", offerSaved.getTitle(),
                        "source", "backend"
                )
        );


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

    public OfferDto findOfferById(UUID id){

        Offer offer =jpaOfferRepositoryAdapter.findById(id).orElseThrow(()->new RuntimeException("Offer not found!"));

        OfferDto offerDto = modelMapper.map(offer, OfferDto.class);

        return offerDto;
    }

    @SuppressWarnings("unchecked")
    private <T> T getValue(Map<String, Object> fields, String key, Class<T> targetType) {
        Object value = fields.get(key);
        if (value == null) return null;

        if (targetType.isInstance(value)) {
            return (T) value;
        }

        if (targetType == Integer.class) {
            if (value instanceof Number) {
                return (T) Integer.valueOf(((Number) value).intValue());
            } else if (value instanceof String) {
                return (T) Integer.valueOf(Integer.parseInt((String) value));
            }
        }

        if (targetType == Boolean.class) {
            if (value instanceof Boolean) {
                return (T) value;
            } else if (value instanceof String) {
                return (T) Boolean.valueOf(Boolean.parseBoolean((String) value));
            }
        }

        if (targetType == LocalDateTime.class) {
            if (value instanceof String) {
                return (T) LocalDateTime.parse((String) value);
            }
        }

        if (targetType == String.class) {
            return (T) value.toString();
        }

        throw new IllegalArgumentException("Unsupported type or incompatible value: " + key + " → " + value);
    }


    public void updateOfferFields(UUID id, Map<String,Object> updatedFields){

        Offer offer = jpaOfferRepositoryAdapter.findById(id).orElseThrow(()->new RuntimeException("Offer not found"));

        if (updatedFields.containsKey("title")) {
            offer.setTitle(getValue(updatedFields,"title",String.class));
        }
        if (updatedFields.containsKey("description")) {
            offer.setDescription(getValue(updatedFields,"description",String.class));
        }
        if (updatedFields.containsKey("discountType")) {
            offer.setDiscountType(getValue(updatedFields,"discountType", DiscountType.class));
        }
        if (updatedFields.containsKey("initialDiscount")) {
            offer.setInitialDiscount(Integer.parseInt((String) updatedFields.get("initialDiscount")));
            offer.setInitialDiscount(getValue(updatedFields,"initialDiscount", Integer.class));
        }
        if (updatedFields.containsKey("currentDiscount")) {
            offer.setCurrentDiscount(getValue(updatedFields,"currentDiscount", Integer.class));
        }
        if (updatedFields.containsKey("decreaseStep")) {
            offer.setDecreaseStep(getValue(updatedFields,"decreaseStep", Integer.class));
        }
        if (updatedFields.containsKey("decreaseIntervalMinutes")) {
            offer.setDecreaseIntervalMinutes(getValue(updatedFields,"decreaseIntervalMinutes", Integer.class));
        }
        if (updatedFields.containsKey("active")) {
            offer.setActive(getValue(updatedFields,"active", Boolean.class));
        }
        if (updatedFields.containsKey("startTime")) {
            offer.setStartTime(getValue(updatedFields,"startTime",LocalDateTime.class));
        }
        if (updatedFields.containsKey("endTime")) {
            offer.setEndTime(getValue(updatedFields,"endTime", LocalDateTime.class));
        }

        try{
            jpaOfferRepositoryAdapter.save(offer);
        }catch (Exception e){
            throw new OfferUpdateException("Error in updating offer",e);
        }
    }

}
