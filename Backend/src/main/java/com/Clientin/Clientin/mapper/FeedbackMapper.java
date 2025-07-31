package com.Clientin.Clientin.mapper;

import com.Clientin.Clientin.entity.Feedback;
import com.Clientin.Clientin.dto.FeedbackDTO;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FeedbackMapper {

    public Feedback toEntity(FeedbackDTO dto) {
        if (dto == null) return null;
        
        Feedback entity = new Feedback();
        entity.setId(dto.getId());
        entity.setClientId(dto.getClientId());
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setRating(dto.getRating());
        entity.setSentiment(dto.getSentiment());
        entity.setComment(dto.getComment());
        entity.setSource(dto.getSource());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setClient(dto.getClient());
        entity.setEmployee(dto.getEmployee());
        return entity;
    }

    public FeedbackDTO toDTO(Feedback entity) {
        if (entity == null) return null;
        
        FeedbackDTO dto = new FeedbackDTO();
        dto.setId(entity.getId());
        dto.setClientId(entity.getClientId());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setRating(entity.getRating());
        dto.setSentiment(entity.getSentiment());
        dto.setComment(entity.getComment());
        dto.setSource(entity.getSource());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setClient(entity.getClient());
        dto.setEmployee(entity.getEmployee());
        return dto;
    }

    public List<FeedbackDTO> toDTOList(List<Feedback> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Feedback> toEntityList(List<FeedbackDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void partialUpdate(FeedbackDTO dto, Feedback entity) {
        if (dto == null || entity == null) return;

        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if (dto.getClientId() != null) {
            entity.setClientId(dto.getClientId());
        }
        if (dto.getEmployeeId() != null) {
            entity.setEmployeeId(dto.getEmployeeId());
        }
        if (dto.getRating() != null) {
            entity.setRating(dto.getRating());
        }
        if (dto.getSentiment() != null) {
            entity.setSentiment(dto.getSentiment());
        }
        if (dto.getComment() != null) {
            entity.setComment(dto.getComment());
        }
        if (dto.getSource() != null) {
            entity.setSource(dto.getSource());
        }
        if (dto.getCreatedAt() != null) {
            entity.setCreatedAt(dto.getCreatedAt());
        }
        if (dto.getClient() != null) {
            entity.setClient(dto.getClient());
        }
        if (dto.getEmployee() != null) {
            entity.setEmployee(dto.getEmployee());
        }
    }
}