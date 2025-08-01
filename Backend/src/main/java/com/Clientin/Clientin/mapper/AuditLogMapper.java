package com.Clientin.Clientin.mapper;

import com.Clientin.Clientin.entity.AuditLog;
import com.Clientin.Clientin.dto.AuditLogDTO;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuditLogMapper {

    public AuditLog toEntity(AuditLogDTO dto) {
        if (dto == null) return null;
        
        AuditLog entity = new AuditLog();
        entity.setId(dto.getId());
        entity.setUserId(dto.getUserId());
        entity.setEntityType(dto.getEntityType());
        entity.setEntityId(dto.getEntityId());
        entity.setAction(dto.getAction());
        entity.setOldValues(dto.getOldValues());
        entity.setNewValues(dto.getNewValues());
        entity.setIpAddress(dto.getIpAddress());
        entity.setUserAgent(dto.getUserAgent());
        entity.setSessionId(dto.getSessionId());
        entity.setRequestId(dto.getRequestId());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUser(dto.getUser());
        return entity;
    }

    public AuditLogDTO toDTO(AuditLog entity) {
        if (entity == null) return null;
        
        AuditLogDTO dto = new AuditLogDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setEntityType(entity.getEntityType());
        dto.setEntityId(entity.getEntityId());
        dto.setAction(entity.getAction());
        dto.setOldValues(entity.getOldValues());
        dto.setNewValues(entity.getNewValues());
        dto.setIpAddress(entity.getIpAddress());
        dto.setUserAgent(entity.getUserAgent());
        dto.setSessionId(entity.getSessionId());
        dto.setRequestId(entity.getRequestId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUser(entity.getUser());
        return dto;
    }

    public List<AuditLogDTO> toDTOList(List<AuditLog> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<AuditLog> toEntityList(List<AuditLogDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void partialUpdate(AuditLogDTO dto, AuditLog entity) {
        if (dto == null || entity == null) return;

        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if (dto.getUserId() != null) {
            entity.setUserId(dto.getUserId());
        }
        if (dto.getEntityType() != null) {
            entity.setEntityType(dto.getEntityType());
        }
        if (dto.getEntityId() != null) {
            entity.setEntityId(dto.getEntityId());
        }
        if (dto.getAction() != null) {
            entity.setAction(dto.getAction());
        }
        if (dto.getOldValues() != null) {
            entity.setOldValues(dto.getOldValues());
        }
        if (dto.getNewValues() != null) {
            entity.setNewValues(dto.getNewValues());
        }
        if (dto.getIpAddress() != null) {
            entity.setIpAddress(dto.getIpAddress());
        }
        if (dto.getUserAgent() != null) {
            entity.setUserAgent(dto.getUserAgent());
        }
        if (dto.getSessionId() != null) {
            entity.setSessionId(dto.getSessionId());
        }
        if (dto.getRequestId() != null) {
            entity.setRequestId(dto.getRequestId());
        }
        if (dto.getCreatedAt() != null) {
            entity.setCreatedAt(dto.getCreatedAt());
        }
        if (dto.getUser() != null) {
            entity.setUser(dto.getUser());
        }
    }
}