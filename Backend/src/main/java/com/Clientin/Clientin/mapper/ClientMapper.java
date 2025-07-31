package com.Clientin.Clientin.mapper;

import com.Clientin.Clientin.entity.Client;
import com.Clientin.Clientin.dto.ClientDTO;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ClientMapper {

    public Client toEntity(ClientDTO dto) {
        if (dto == null) return null;
        
        Client entity = new Client();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setIsAnonymous(dto.getIsAnonymous());
        entity.setCreatedAt(dto.getCreatedAt());
        return entity;
    }

    public ClientDTO toDTO(Client entity) {
        if (entity == null) return null;
        
        ClientDTO dto = new ClientDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setIsAnonymous(entity.getIsAnonymous());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    public List<ClientDTO> toDTOList(List<Client> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Client> toEntityList(List<ClientDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void partialUpdate(ClientDTO dto, Client entity) {
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
        if (dto.getIsAnonymous() != null) {
            entity.setIsAnonymous(dto.getIsAnonymous());
        }
        if (dto.getCreatedAt() != null) {
            entity.setCreatedAt(dto.getCreatedAt());
        }
    }
}