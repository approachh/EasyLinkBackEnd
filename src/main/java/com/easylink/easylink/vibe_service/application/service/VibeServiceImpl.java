package com.easylink.easylink.vibe_service.application.service;

import com.easylink.easylink.exceptions.VibeLimitExceededException;
import com.easylink.easylink.vibe_service.application.dto.CreateVibeCommand;
import com.easylink.easylink.vibe_service.application.dto.UpdateVibeCommand;
import com.easylink.easylink.vibe_service.application.dto.VibeDto;
import com.easylink.easylink.vibe_service.application.mapper.VibeDtoMapper;
import com.easylink.easylink.vibe_service.application.port.in.vibe.*;
import com.easylink.easylink.vibe_service.application.port.out.VibeFieldRepositoryPort;
import com.easylink.easylink.vibe_service.application.port.out.VibeRepositoryPort;
import com.easylink.easylink.vibe_service.domain.model.*;
import com.easylink.easylink.vibe_service.web.dto.UpdateVibeRequest;
import com.easylink.easylink.vibe_service.web.dto.VibeFieldDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VibeServiceImpl implements CreateVibeUseCase, UpdateVibeUseCase, DeleteVibeUseCase, GetVibeUseCase, GetVibeByIdUseCase {

    private final VibeRepositoryPort vibeRepositoryPort;
    private final VibeFieldRepositoryPort vibeFieldRepositoryPort;
    private final VibeRateLimitPort rateLimitPort;

    @Override
    public VibeDto create(CreateVibeCommand command, String vibeAccountId) {

        if(!rateLimitPort.canCreateVibe(vibeAccountId)){
            throw new VibeLimitExceededException("Vibe limit exceeded for account: "+vibeAccountId);
        }

        List<UUID> ids = command.getVibeFieldsDTO().stream().map(VibeFieldDTO::getId).collect(Collectors.toList());

        List<VibeField> vibeFieldList = command.getVibeFieldsDTO().stream()
                .map(dto -> {
                    VibeField field;
                    switch (dto.getType().toLowerCase()) {
                        case "email":
                            EmailField emailField = new EmailField();
                            emailField.setEmail(dto.getValue());
                            field = emailField;
                            break;
                        case "phone":
                            PhoneField phoneField = new PhoneField();
                            phoneField.setPhone(dto.getValue());
                            field = phoneField;
                            break;
                        default:
                            field = new LinkField();
                            break;
                   }
                    field.setLabel(dto.getLabel());
                    field.setType(dto.getType());
                    field.setValue(dto.getValue());
                    //return vibeFieldRepositoryPort.save(field);
                    return field;
                })
                .collect(Collectors.toList());


        Vibe vibe = new Vibe();
        vibe.setName(command.getName());
        vibe.setVibeAccountId(UUID.fromString(vibeAccountId));
        vibe.setDescription(command.getDescription());
        vibe.setType(command.getType());

        vibe.setFields(vibeFieldList);

        vibeFieldList.forEach(field->field.setVibe(vibe));

        Vibe savedVibe = vibeRepositoryPort.save(vibe);

        VibeDto vibeDto = VibeDtoMapper.toDto(savedVibe);

        return vibeDto;
    }

    @Override
    public VibeDto update(UpdateVibeRequest request, UUID vibeId, UUID accountId) {

        UpdateVibeCommand updateVibeCommand = new UpdateVibeCommand();
        updateVibeCommand.setId(vibeId);
        updateVibeCommand.setName(request.getName());
        updateVibeCommand.setDescription(request.getDescription());
        updateVibeCommand.setAccountId(accountId);
        updateVibeCommand.setFieldsDTO(request.getFieldsDTO());

        Vibe vibe = vibeRepositoryPort.findById(updateVibeCommand.getId())
                .orElseThrow(() -> new RuntimeException("Vibe not found"));

        if (!vibe.getVibeAccountId().equals(updateVibeCommand.getAccountId())){
            throw new SecurityException("Access denied!");
        }

        vibe.setName(updateVibeCommand.getName());
        vibe.setDescription(updateVibeCommand.getDescription());

        List<VibeFieldDTO> fields = updateVibeCommand.getFieldsDTO();
        if (fields == null) fields = List.of();
        List<VibeField> updatedFields = new java.util.ArrayList<>();

        for (VibeFieldDTO dto : fields) {
            VibeField field;

            if (dto.getId() == null) {

                switch (dto.getType().toLowerCase()) {
                    case "email" -> {
                        EmailField emailField = new EmailField();
                        emailField.setEmail(dto.getValue());
                        field = emailField;
                    }
                    case "phone" -> {
                        PhoneField phoneField = new PhoneField();
                        phoneField.setPhone(dto.getValue());
                        field = phoneField;
                    }
                    default -> field = new LinkField();
                }
            } else {

                field = vibeFieldRepositoryPort.findById(dto.getId())
                        .orElseThrow(() -> new RuntimeException("Field not found: " + dto.getId()));
            }

            field.setLabel(dto.getLabel());
            field.setValue(dto.getValue());
            field.setType(dto.getType());
            field.setVibe(vibe);

            updatedFields.add(vibeFieldRepositoryPort.save(field));
        }


        vibe.getFields().clear();
        vibe.getFields().addAll(updatedFields);

        Vibe updated = vibeRepositoryPort.save(vibe);
        return VibeDtoMapper.toDto(updated);
    }

    @Override
    public void delete(UUID id, UUID accountId) {
        Vibe vibe = vibeRepositoryPort.findById(id).orElseThrow(()->new RuntimeException("Vibe not found"));

        if(!vibe.getVibeAccountId().equals(accountId)){
            throw new SecurityException("Access denied");
        }

        vibeRepositoryPort.delete(vibe);

        rateLimitPort.decrementVibe(accountId.toString());
    }

    @Override
    public List<VibeDto> findAllByVibeAccountId(UUID accountId) {

        List<Vibe> vibeDtoList = vibeRepositoryPort.findAllByAccountId(accountId);

        List<VibeDto> dtoList = vibeDtoList.stream().map(VibeDtoMapper::toDto).toList();

        return dtoList;
    }

    @Override
    public List<VibeDto> findAllByUsername(String user) {
        return List.of();
    }


    @Override
    public List<VibeDto> findAllById(UUID id) {

        List<Vibe> vibeDtoList = vibeRepositoryPort.findAllById(id);

        List<VibeDto> dtoList = vibeDtoList.stream().map(VibeDtoMapper::toDto).toList();

        return dtoList;
    }

    @Override
    public List<VibeDto> findAllByAccountId(UUID id) {

        List<Vibe> vibeDtoList = vibeRepositoryPort.findAllByAccountId(id);

        List<VibeDto> dtoList = vibeDtoList.stream().map(VibeDtoMapper::toDto).toList();

        return dtoList;
    }


    @Override
    public VibeDto getVibeById(UUID id) {
        Vibe vibe = vibeRepositoryPort.findById(id).orElseThrow(()->new RuntimeException("Vibe not found"));

        VibeDto vibeDto = VibeDtoMapper.toDto(vibe);

        return vibeDto;
    }
}
