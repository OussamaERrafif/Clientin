package com.Clientin.Clientin.mapper;

import com.Clientin.Clientin.entity.Goal;
import com.Clientin.Clientin.dto.GoalDTO;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GoalMapper {

    public Goal toEntity(GoalDTO dto) {
        if (dto == null) return null;
        
        Goal entity = new Goal();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setProgress(dto.getProgress());
        entity.setDueDate(dto.getDueDate());
        entity.setEmployeeProfileUserId(dto.getEmployeeProfileUserId());
        entity.setEmployeeProfile(dto.getEmployeeProfile());
        return entity;
    }

    public GoalDTO toDTO(Goal entity) {
        if (entity == null) return null;
        
        GoalDTO dto = new GoalDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setProgress(entity.getProgress());
        dto.setDueDate(entity.getDueDate());
        dto.setEmployeeProfileUserId(entity.getEmployeeProfileUserId());
        dto.setEmployeeProfile(entity.getEmployeeProfile());
        return dto;
    }

    public List<GoalDTO> toDTOList(List<Goal> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Goal> toEntityList(List<GoalDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void partialUpdate(GoalDTO dto, Goal entity) {
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
        if (dto.getProgress() != null) {
            entity.setProgress(dto.getProgress());
        }
        if (dto.getDueDate() != null) {
            entity.setDueDate(dto.getDueDate());
        }
        if (dto.getEmployeeProfileUserId() != null) {
            entity.setEmployeeProfileUserId(dto.getEmployeeProfileUserId());
        }
        if (dto.getEmployeeProfile() != null) {
            entity.setEmployeeProfile(dto.getEmployeeProfile());
        }
    }
}