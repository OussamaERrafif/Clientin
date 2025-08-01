package com.Clientin.Clientin.mapper;

import com.Clientin.Clientin.entity.Training;
import com.Clientin.Clientin.dto.TrainingDTO;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TrainingMapper {

    public Training toEntity(TrainingDTO dto) {
        if (dto == null) return null;
        
        Training entity = new Training();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setTrainingType(dto.getTrainingType());
        entity.setDurationHours(dto.getDurationHours());
        entity.setContentUrl(dto.getContentUrl());
        entity.setPrerequisites(dto.getPrerequisites());
        entity.setLearningObjectives(dto.getLearningObjectives());
        entity.setStatus(dto.getStatus());
        entity.setMaxParticipants(dto.getMaxParticipants());
        entity.setPassingScore(dto.getPassingScore());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        return entity;
    }

    public TrainingDTO toDTO(Training entity) {
        if (entity == null) return null;
        
        TrainingDTO dto = new TrainingDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setTrainingType(entity.getTrainingType());
        dto.setDurationHours(entity.getDurationHours());
        dto.setContentUrl(entity.getContentUrl());
        dto.setPrerequisites(entity.getPrerequisites());
        dto.setLearningObjectives(entity.getLearningObjectives());
        dto.setStatus(entity.getStatus());
        dto.setMaxParticipants(entity.getMaxParticipants());
        dto.setPassingScore(entity.getPassingScore());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public List<TrainingDTO> toDTOList(List<Training> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Training> toEntityList(List<TrainingDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void partialUpdate(TrainingDTO dto, Training entity) {
        if (dto == null || entity == null) return;

        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if (dto.getTitle() != null) {
            entity.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }
        if (dto.getTrainingType() != null) {
            entity.setTrainingType(dto.getTrainingType());
        }
        if (dto.getDurationHours() != null) {
            entity.setDurationHours(dto.getDurationHours());
        }
        if (dto.getContentUrl() != null) {
            entity.setContentUrl(dto.getContentUrl());
        }
        if (dto.getPrerequisites() != null) {
            entity.setPrerequisites(dto.getPrerequisites());
        }
        if (dto.getLearningObjectives() != null) {
            entity.setLearningObjectives(dto.getLearningObjectives());
        }
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
        if (dto.getMaxParticipants() != null) {
            entity.setMaxParticipants(dto.getMaxParticipants());
        }
        if (dto.getPassingScore() != null) {
            entity.setPassingScore(dto.getPassingScore());
        }
        if (dto.getCreatedAt() != null) {
            entity.setCreatedAt(dto.getCreatedAt());
        }
        if (dto.getUpdatedAt() != null) {
            entity.setUpdatedAt(dto.getUpdatedAt());
        }
    }
}