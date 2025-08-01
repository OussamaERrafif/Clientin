package com.Clientin.Clientin.mapper;

import com.Clientin.Clientin.entity.AuthToken;
import com.Clientin.Clientin.dto.AuthTokenDTO;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuthTokenMapper {

    public AuthToken toEntity(AuthTokenDTO dto) {
        if (dto == null) return null;
        
        AuthToken entity = new AuthToken();
        entity.setId(dto.getId());
        entity.setUserId(dto.getUserId());
        entity.setTokenHash(dto.getTokenHash());
        entity.setTokenType(dto.getTokenType());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setExpiresAt(dto.getExpiresAt());
        entity.setRevoked(dto.getRevoked());
        entity.setDeviceInfo(dto.getDeviceInfo());
        entity.setIpAddress(dto.getIpAddress());
        entity.setUser(dto.getUser());
        return entity;
    }

    public AuthTokenDTO toDTO(AuthToken entity) {
        if (entity == null) return null;
        
        AuthTokenDTO dto = new AuthTokenDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setTokenHash(entity.getTokenHash());
        dto.setTokenType(entity.getTokenType());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setExpiresAt(entity.getExpiresAt());
        dto.setRevoked(entity.getRevoked());
        dto.setDeviceInfo(entity.getDeviceInfo());
        dto.setIpAddress(entity.getIpAddress());
        dto.setUser(entity.getUser());
        return dto;
    }

    public List<AuthTokenDTO> toDTOList(List<AuthToken> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<AuthToken> toEntityList(List<AuthTokenDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void partialUpdate(AuthTokenDTO dto, AuthToken entity) {
        if (dto == null || entity == null) return;

        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if (dto.getUserId() != null) {
            entity.setUserId(dto.getUserId());
        }
        if (dto.getTokenHash() != null) {
            entity.setTokenHash(dto.getTokenHash());
        }
        if (dto.getTokenType() != null) {
            entity.setTokenType(dto.getTokenType());
        }
        if (dto.getCreatedAt() != null) {
            entity.setCreatedAt(dto.getCreatedAt());
        }
        if (dto.getExpiresAt() != null) {
            entity.setExpiresAt(dto.getExpiresAt());
        }
        if (dto.getRevoked() != null) {
            entity.setRevoked(dto.getRevoked());
        }
        if (dto.getDeviceInfo() != null) {
            entity.setDeviceInfo(dto.getDeviceInfo());
        }
        if (dto.getIpAddress() != null) {
            entity.setIpAddress(dto.getIpAddress());
        }
        if (dto.getUser() != null) {
            entity.setUser(dto.getUser());
        }
    }
}