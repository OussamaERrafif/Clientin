package com.Clientin.Clientin.mapper;

import com.Clientin.Clientin.entity.NFCSession;
import com.Clientin.Clientin.dto.NFCSessionDTO;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NFCSessionMapper {

    public NFCSession toEntity(NFCSessionDTO dto) {
        if (dto == null) return null;
        
        NFCSession entity = new NFCSession();
        entity.setId(dto.getId());
        entity.setDeviceId(dto.getDeviceId());
        entity.setSessionToken(dto.getSessionToken());
        entity.setClientId(dto.getClientId());
        entity.setStatus(dto.getStatus());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setExpiresAt(dto.getExpiresAt());
        entity.setCompletedAt(dto.getCompletedAt());
        entity.setMetadata(dto.getMetadata());
        entity.setDevice(dto.getDevice());
        entity.setClient(dto.getClient());
        return entity;
    }

    public NFCSessionDTO toDTO(NFCSession entity) {
        if (entity == null) return null;
        
        NFCSessionDTO dto = new NFCSessionDTO();
        dto.setId(entity.getId());
        dto.setDeviceId(entity.getDeviceId());
        dto.setSessionToken(entity.getSessionToken());
        dto.setClientId(entity.getClientId());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setExpiresAt(entity.getExpiresAt());
        dto.setCompletedAt(entity.getCompletedAt());
        dto.setMetadata(entity.getMetadata());
        dto.setDevice(entity.getDevice());
        dto.setClient(entity.getClient());
        return dto;
    }

    public List<NFCSessionDTO> toDTOList(List<NFCSession> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<NFCSession> toEntityList(List<NFCSessionDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void partialUpdate(NFCSessionDTO dto, NFCSession entity) {
        if (dto == null || entity == null) return;

        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if (dto.getDeviceId() != null) {
            entity.setDeviceId(dto.getDeviceId());
        }
        if (dto.getSessionToken() != null) {
            entity.setSessionToken(dto.getSessionToken());
        }
        if (dto.getClientId() != null) {
            entity.setClientId(dto.getClientId());
        }
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
        if (dto.getCreatedAt() != null) {
            entity.setCreatedAt(dto.getCreatedAt());
        }
        if (dto.getExpiresAt() != null) {
            entity.setExpiresAt(dto.getExpiresAt());
        }
        if (dto.getCompletedAt() != null) {
            entity.setCompletedAt(dto.getCompletedAt());
        }
        if (dto.getMetadata() != null) {
            entity.setMetadata(dto.getMetadata());
        }
        if (dto.getDevice() != null) {
            entity.setDevice(dto.getDevice());
        }
        if (dto.getClient() != null) {
            entity.setClient(dto.getClient());
        }
    }
}