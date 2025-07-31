package com.Clientin.Clientin.mapper;

import com.Clientin.Clientin.entity.Insight;
import com.Clientin.Clientin.dto.InsightDTO;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InsightMapper {

    public Insight toEntity(InsightDTO dto) {
        if (dto == null) return null;
        
        Insight entity = new Insight();
        entity.setId(dto.getId());
        entity.setPeriod(dto.getPeriod());
        entity.setPositiveCount(dto.getPositiveCount());
        entity.setNegativeCount(dto.getNegativeCount());
        entity.setAvgResponseTime(dto.getAvgResponseTime());
        entity.setTotalFeedbacks(dto.getTotalFeedbacks());
        entity.setGeneratedAt(dto.getGeneratedAt());
        return entity;
    }

    public InsightDTO toDTO(Insight entity) {
        if (entity == null) return null;
        
        InsightDTO dto = new InsightDTO();
        dto.setId(entity.getId());
        dto.setPeriod(entity.getPeriod());
        dto.setPositiveCount(entity.getPositiveCount());
        dto.setNegativeCount(entity.getNegativeCount());
        dto.setAvgResponseTime(entity.getAvgResponseTime());
        dto.setTotalFeedbacks(entity.getTotalFeedbacks());
        dto.setGeneratedAt(entity.getGeneratedAt());
        return dto;
    }

    public List<InsightDTO> toDTOList(List<Insight> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Insight> toEntityList(List<InsightDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void partialUpdate(InsightDTO dto, Insight entity) {
        if (dto == null || entity == null) return;

        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if (dto.getPeriod() != null) {
            entity.setPeriod(dto.getPeriod());
        }
        if (dto.getPositiveCount() != null) {
            entity.setPositiveCount(dto.getPositiveCount());
        }
        if (dto.getNegativeCount() != null) {
            entity.setNegativeCount(dto.getNegativeCount());
        }
        if (dto.getAvgResponseTime() != null) {
            entity.setAvgResponseTime(dto.getAvgResponseTime());
        }
        if (dto.getTotalFeedbacks() != null) {
            entity.setTotalFeedbacks(dto.getTotalFeedbacks());
        }
        if (dto.getGeneratedAt() != null) {
            entity.setGeneratedAt(dto.getGeneratedAt());
        }
    }
}