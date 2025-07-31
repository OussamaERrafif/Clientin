package com.Clientin.Clientin.mapper;

import com.Clientin.Clientin.entity.Subscription;
import com.Clientin.Clientin.dto.SubscriptionDTO;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SubscriptionMapper {

    public Subscription toEntity(SubscriptionDTO dto) {
        if (dto == null) return null;
        
        Subscription entity = new Subscription();
        entity.setId(dto.getId());
        entity.setUserId(dto.getUserId());
        entity.setPlan(dto.getPlan());
        entity.setStatus(dto.getStatus());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setPaymentMethod(dto.getPaymentMethod());
        entity.setUser(dto.getUser());
        return entity;
    }

    public SubscriptionDTO toDTO(Subscription entity) {
        if (entity == null) return null;
        
        SubscriptionDTO dto = new SubscriptionDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setPlan(entity.getPlan());
        dto.setStatus(entity.getStatus());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setPaymentMethod(entity.getPaymentMethod());
        dto.setUser(entity.getUser());
        return dto;
    }

    public List<SubscriptionDTO> toDTOList(List<Subscription> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Subscription> toEntityList(List<SubscriptionDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void partialUpdate(SubscriptionDTO dto, Subscription entity) {
        if (dto == null || entity == null) return;

        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if (dto.getUserId() != null) {
            entity.setUserId(dto.getUserId());
        }
        if (dto.getPlan() != null) {
            entity.setPlan(dto.getPlan());
        }
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
        if (dto.getStartDate() != null) {
            entity.setStartDate(dto.getStartDate());
        }
        if (dto.getEndDate() != null) {
            entity.setEndDate(dto.getEndDate());
        }
        if (dto.getPaymentMethod() != null) {
            entity.setPaymentMethod(dto.getPaymentMethod());
        }
        if (dto.getUser() != null) {
            entity.setUser(dto.getUser());
        }
    }
}