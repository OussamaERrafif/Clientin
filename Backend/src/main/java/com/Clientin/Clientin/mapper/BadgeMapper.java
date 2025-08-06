package com.Clientin.Clientin.mapper;

import com.Clientin.Clientin.entity.Badge;
import com.Clientin.Clientin.dto.BadgeDTO;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BadgeMapper {

    public Badge toEntity(BadgeDTO dto) {
        if (dto == null) return null;
        
        Badge entity = new Badge();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setBadgeType(dto.getBadgeType() != null ? Badge.BadgeType.valueOf(dto.getBadgeType().name()) : null);
        entity.setIconUrl(dto.getIconUrl());
        entity.setPointsValue(dto.getPointsValue());
        entity.setLevel(dto.getLevel() != null ? Badge.BadgeLevel.valueOf(dto.getLevel().name()) : null);
        entity.setCriteria(dto.getCriteria());
        entity.setIsActive(dto.getIsActive());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setCreatedAt(dto.getCreatedAt());
        return entity;
    }

    public BadgeDTO toDTO(Badge entity) {
        if (entity == null) return null;
        
        BadgeDTO dto = new BadgeDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setBadgeType(entity.getBadgeType() != null ? BadgeDTO.BadgeType.valueOf(entity.getBadgeType().name()) : null);
        dto.setIconUrl(entity.getIconUrl());
        dto.setPointsValue(entity.getPointsValue());
        dto.setLevel(entity.getLevel() != null ? BadgeDTO.BadgeLevel.valueOf(entity.getLevel().name()) : null);
        dto.setCriteria(entity.getCriteria());
        dto.setIsActive(entity.getIsActive());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    public List<BadgeDTO> toDTOList(List<Badge> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Badge> toEntityList(List<BadgeDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void partialUpdate(BadgeDTO dto, Badge entity) {
        if (dto == null || entity == null) return;

        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }
        if (dto.getBadgeType() != null) {
            entity.setBadgeType(Badge.BadgeType.valueOf(dto.getBadgeType().name()));
        }
        if (dto.getIconUrl() != null) {
            entity.setIconUrl(dto.getIconUrl());
        }
        if (dto.getPointsValue() != null) {
            entity.setPointsValue(dto.getPointsValue());
        }
        if (dto.getLevel() != null) {
            entity.setLevel(Badge.BadgeLevel.valueOf(dto.getLevel().name()));
        }
        if (dto.getCriteria() != null) {
            entity.setCriteria(dto.getCriteria());
        }
        if (dto.getIsActive() != null) {
            entity.setIsActive(dto.getIsActive());
        }
        if (dto.getCreatedBy() != null) {
            entity.setCreatedBy(dto.getCreatedBy());
        }
        if (dto.getCreatedAt() != null) {
            entity.setCreatedAt(dto.getCreatedAt());
        }
    }
}