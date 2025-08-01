package com.Clientin.Clientin.mapper;

import com.Clientin.Clientin.entity.EmailTemplate;
import com.Clientin.Clientin.dto.EmailTemplateDTO;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EmailTemplateMapper {

    public EmailTemplate toEntity(EmailTemplateDTO dto) {
        if (dto == null) return null;
        
        EmailTemplate entity = new EmailTemplate();
        entity.setId(dto.getId());
        entity.setTemplateName(dto.getTemplateName());
        entity.setTemplateType(dto.getTemplateType());
        entity.setSubject(dto.getSubject());
        entity.setHtmlContent(dto.getHtmlContent());
        entity.setTextContent(dto.getTextContent());
        entity.setVariables(dto.getVariables());
        entity.setActive(dto.getActive());
        entity.setVersion(dto.getVersion());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        return entity;
    }

    public EmailTemplateDTO toDTO(EmailTemplate entity) {
        if (entity == null) return null;
        
        EmailTemplateDTO dto = new EmailTemplateDTO();
        dto.setId(entity.getId());
        dto.setTemplateName(entity.getTemplateName());
        dto.setTemplateType(entity.getTemplateType());
        dto.setSubject(entity.getSubject());
        dto.setHtmlContent(entity.getHtmlContent());
        dto.setTextContent(entity.getTextContent());
        dto.setVariables(entity.getVariables());
        dto.setActive(entity.getActive());
        dto.setVersion(entity.getVersion());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public List<EmailTemplateDTO> toDTOList(List<EmailTemplate> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<EmailTemplate> toEntityList(List<EmailTemplateDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void partialUpdate(EmailTemplateDTO dto, EmailTemplate entity) {
        if (dto == null || entity == null) return;

        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if (dto.getTemplateName() != null) {
            entity.setTemplateName(dto.getTemplateName());
        }
        if (dto.getTemplateType() != null) {
            entity.setTemplateType(dto.getTemplateType());
        }
        if (dto.getSubject() != null) {
            entity.setSubject(dto.getSubject());
        }
        if (dto.getHtmlContent() != null) {
            entity.setHtmlContent(dto.getHtmlContent());
        }
        if (dto.getTextContent() != null) {
            entity.setTextContent(dto.getTextContent());
        }
        if (dto.getVariables() != null) {
            entity.setVariables(dto.getVariables());
        }
        if (dto.getActive() != null) {
            entity.setActive(dto.getActive());
        }
        if (dto.getVersion() != null) {
            entity.setVersion(dto.getVersion());
        }
        if (dto.getCreatedAt() != null) {
            entity.setCreatedAt(dto.getCreatedAt());
        }
        if (dto.getUpdatedAt() != null) {
            entity.setUpdatedAt(dto.getUpdatedAt());
        }
    }
}