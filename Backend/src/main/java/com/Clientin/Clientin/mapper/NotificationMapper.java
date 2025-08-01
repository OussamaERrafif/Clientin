package com.Clientin.Clientin.mapper;

import com.Clientin.Clientin.entity.Notification;
import com.Clientin.Clientin.dto.NotificationDTO;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NotificationMapper {

    public Notification toEntity(NotificationDTO dto) {
        if (dto == null) return null;
        
        Notification entity = new Notification();
        entity.setId(dto.getId());
        entity.setUserId(dto.getUserId());
        entity.setTitle(dto.getTitle());
        entity.setMessage(dto.getMessage());
        entity.setType(dto.getType());
        entity.setPriority(dto.getPriority());
        entity.setReadStatus(dto.getReadStatus());
        entity.setReadAt(dto.getReadAt());
        entity.setActionUrl(dto.getActionUrl());
        entity.setMetadata(dto.getMetadata());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setExpiresAt(dto.getExpiresAt());
        entity.setUser(dto.getUser());
        return entity;
    }

    public NotificationDTO toDTO(Notification entity) {
        if (entity == null) return null;
        
        NotificationDTO dto = new NotificationDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setTitle(entity.getTitle());
        dto.setMessage(entity.getMessage());
        dto.setType(entity.getType());
        dto.setPriority(entity.getPriority());
        dto.setReadStatus(entity.getReadStatus());
        dto.setReadAt(entity.getReadAt());
        dto.setActionUrl(entity.getActionUrl());
        dto.setMetadata(entity.getMetadata());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setExpiresAt(entity.getExpiresAt());
        dto.setUser(entity.getUser());
        return dto;
    }

    public List<NotificationDTO> toDTOList(List<Notification> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Notification> toEntityList(List<NotificationDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void partialUpdate(NotificationDTO dto, Notification entity) {
        if (dto == null || entity == null) return;

        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if (dto.getUserId() != null) {
            entity.setUserId(dto.getUserId());
        }
        if (dto.getTitle() != null) {
            entity.setTitle(dto.getTitle());
        }
        if (dto.getMessage() != null) {
            entity.setMessage(dto.getMessage());
        }
        if (dto.getType() != null) {
            entity.setType(dto.getType());
        }
        if (dto.getPriority() != null) {
            entity.setPriority(dto.getPriority());
        }
        if (dto.getReadStatus() != null) {
            entity.setReadStatus(dto.getReadStatus());
        }
        if (dto.getReadAt() != null) {
            entity.setReadAt(dto.getReadAt());
        }
        if (dto.getActionUrl() != null) {
            entity.setActionUrl(dto.getActionUrl());
        }
        if (dto.getMetadata() != null) {
            entity.setMetadata(dto.getMetadata());
        }
        if (dto.getCreatedAt() != null) {
            entity.setCreatedAt(dto.getCreatedAt());
        }
        if (dto.getExpiresAt() != null) {
            entity.setExpiresAt(dto.getExpiresAt());
        }
        if (dto.getUser() != null) {
            entity.setUser(dto.getUser());
        }
    }
}