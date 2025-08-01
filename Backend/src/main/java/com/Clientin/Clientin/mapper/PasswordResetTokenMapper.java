package com.Clientin.Clientin.mapper;

import com.Clientin.Clientin.entity.PasswordResetToken;
import com.Clientin.Clientin.dto.PasswordResetTokenDTO;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PasswordResetTokenMapper {

    public PasswordResetToken toEntity(PasswordResetTokenDTO dto) {
        if (dto == null) return null;
        
        PasswordResetToken entity = new PasswordResetToken();
        entity.setId(dto.getId());
        entity.setUserId(dto.getUserId());
        entity.setTokenHash(dto.getTokenHash());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setExpiresAt(dto.getExpiresAt());
        entity.setUsed(dto.getUsed());
        entity.setIpAddress(dto.getIpAddress());
        entity.setUser(dto.getUser());
        return entity;
    }

    public PasswordResetTokenDTO toDTO(PasswordResetToken entity) {
        if (entity == null) return null;
        
        PasswordResetTokenDTO dto = new PasswordResetTokenDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setTokenHash(entity.getTokenHash());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setExpiresAt(entity.getExpiresAt());
        dto.setUsed(entity.getUsed());
        dto.setIpAddress(entity.getIpAddress());
        dto.setUser(entity.getUser());
        return dto;
    }

    public List<PasswordResetTokenDTO> toDTOList(List<PasswordResetToken> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<PasswordResetToken> toEntityList(List<PasswordResetTokenDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void partialUpdate(PasswordResetTokenDTO dto, PasswordResetToken entity) {
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
        if (dto.getCreatedAt() != null) {
            entity.setCreatedAt(dto.getCreatedAt());
        }
        if (dto.getExpiresAt() != null) {
            entity.setExpiresAt(dto.getExpiresAt());
        }
        if (dto.getUsed() != null) {
            entity.setUsed(dto.getUsed());
        }
        if (dto.getIpAddress() != null) {
            entity.setIpAddress(dto.getIpAddress());
        }
        if (dto.getUser() != null) {
            entity.setUser(dto.getUser());
        }
    }
}