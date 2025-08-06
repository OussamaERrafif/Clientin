package com.Clientin.Clientin.mapper;

import com.Clientin.Clientin.entity.Reward;
import com.Clientin.Clientin.dto.RewardDTO;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RewardMapper {

    public Reward toEntity(RewardDTO dto) {
        if (dto == null) return null;
        
        Reward entity = new Reward();
        entity.setId(dto.getId());
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setRewardType(dto.getRewardType() != null ? Reward.RewardType.valueOf(dto.getRewardType().name()) : null);
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setPointsValue(dto.getPointsValue());
        entity.setMonetaryValue(dto.getMonetaryValue());
        entity.setAwardedBy(dto.getAwardedBy());
        entity.setReason(dto.getReason());
        entity.setStatus(dto.getStatus() != null ? Reward.RewardStatus.valueOf(dto.getStatus().name()) : null);
        entity.setBadgeId(dto.getBadgeId());
        entity.setExpiryDate(dto.getExpiryDate());
        entity.setRedeemedAt(dto.getRedeemedAt());
        entity.setCategory(dto.getCategory() != null ? Reward.RewardCategory.valueOf(dto.getCategory().name()) : null);
        entity.setCreatedAt(dto.getCreatedAt());
        return entity;
    }

    public RewardDTO toDTO(Reward entity) {
        if (entity == null) return null;
        
        RewardDTO dto = new RewardDTO();
        dto.setId(entity.getId());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setRewardType(entity.getRewardType() != null ? RewardDTO.RewardType.valueOf(entity.getRewardType().name()) : null);
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setPointsValue(entity.getPointsValue());
        dto.setMonetaryValue(entity.getMonetaryValue());
        dto.setAwardedBy(entity.getAwardedBy());
        dto.setReason(entity.getReason());
        dto.setStatus(entity.getStatus() != null ? RewardDTO.RewardStatus.valueOf(entity.getStatus().name()) : null);
        dto.setBadgeId(entity.getBadgeId());
        dto.setExpiryDate(entity.getExpiryDate());
        dto.setRedeemedAt(entity.getRedeemedAt());
        dto.setCategory(entity.getCategory() != null ? RewardDTO.RewardCategory.valueOf(entity.getCategory().name()) : null);
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    public List<RewardDTO> toDTOList(List<Reward> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Reward> toEntityList(List<RewardDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void partialUpdate(RewardDTO dto, Reward entity) {
        if (dto == null || entity == null) return;

        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if (dto.getEmployeeId() != null) {
            entity.setEmployeeId(dto.getEmployeeId());
        }
        if (dto.getRewardType() != null) {
            entity.setRewardType(Reward.RewardType.valueOf(dto.getRewardType().name()));
        }
        if (dto.getTitle() != null) {
            entity.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }
        if (dto.getPointsValue() != null) {
            entity.setPointsValue(dto.getPointsValue());
        }
        if (dto.getMonetaryValue() != null) {
            entity.setMonetaryValue(dto.getMonetaryValue());
        }
        if (dto.getAwardedBy() != null) {
            entity.setAwardedBy(dto.getAwardedBy());
        }
        if (dto.getReason() != null) {
            entity.setReason(dto.getReason());
        }
        if (dto.getStatus() != null) {
            entity.setStatus(Reward.RewardStatus.valueOf(dto.getStatus().name()));
        }
        if (dto.getBadgeId() != null) {
            entity.setBadgeId(dto.getBadgeId());
        }
        if (dto.getExpiryDate() != null) {
            entity.setExpiryDate(dto.getExpiryDate());
        }
        if (dto.getRedeemedAt() != null) {
            entity.setRedeemedAt(dto.getRedeemedAt());
        }
        if (dto.getCategory() != null) {
            entity.setCategory(Reward.RewardCategory.valueOf(dto.getCategory().name()));
        }
        if (dto.getCreatedAt() != null) {
            entity.setCreatedAt(dto.getCreatedAt());
        }
    }
}