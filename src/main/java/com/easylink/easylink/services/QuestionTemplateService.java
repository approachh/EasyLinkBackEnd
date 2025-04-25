package com.easylink.easylink.services;

import com.easylink.easylink.dtos.QuestionTemplateDTO;
import com.easylink.easylink.entities.QuestionTemplate;
import com.easylink.easylink.repositories.QuestionTemplateRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import static java.util.stream.Collectors.toList;

@Service
public class QuestionTemplateService {
    @Autowired
    private QuestionTemplateRepository questionTemplateRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<QuestionTemplateDTO> getAllQuestionTemplates(){
        List<QuestionTemplate> templates = questionTemplateRepository.findAllByPredefinedTrue(); // predefined=true
        return templates.stream()
                   .map(q -> modelMapper.map(q, QuestionTemplateDTO.class)).toList();
    }
}
