package com.Clientin.Clientin.mapper;

import com.Clientin.Clientin.entity.User;
import com.Clientin.Clientin.dto.UserDTO;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public User toEntity(UserDTO dto) {
        if (dto == null) return null;
        
        User entity = new User();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setRole(dto.getRole());
        entity.setPhotoUrl(dto.getPhotoUrl());
        entity.setStatus(dto.getStatus());
        entity.setPasswordHash(dto.getPasswordHash());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        return entity;
    }

    public UserDTO toDTO(User entity) {
        if (entity == null) return null;
        
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole());
        dto.setPhotoUrl(entity.getPhotoUrl());
        dto.setStatus(entity.getStatus());
        dto.setPasswordHash(entity.getPasswordHash());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public List<UserDTO> toDTOList(List<User> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<User> toEntityList(List<UserDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void partialUpdate(UserDTO dto, User entity) {
        if (dto == null || entity == null) return;

        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }
        if (dto.getEmail() != null) {
            entity.setEmail(dto.getEmail());
        }
        if (dto.getRole() != null) {
            entity.setRole(dto.getRole());
        }
        if (dto.getPhotoUrl() != null) {
            entity.setPhotoUrl(dto.getPhotoUrl());
        }
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
        if (dto.getPasswordHash() != null) {
            entity.setPasswordHash(dto.getPasswordHash());
        }
        if (dto.getCreatedAt() != null) {
            entity.setCreatedAt(dto.getCreatedAt());
        }
        if (dto.getUpdatedAt() != null) {
            entity.setUpdatedAt(dto.getUpdatedAt());
        }
    }
}