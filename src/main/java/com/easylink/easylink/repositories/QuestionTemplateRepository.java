package com.easylink.easylink.repositories;

import com.easylink.easylink.entities.QuestionTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuestionTemplateRepository extends JpaRepository<QuestionTemplate, UUID> {

    List<QuestionTemplate> findAllByPredefinedTrue();

    Optional<QuestionTemplate> findByText(String text);
}
