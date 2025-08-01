package com.Clientin.Clientin.mapper;

import com.Clientin.Clientin.entity.Analytics;
import com.Clientin.Clientin.dto.AnalyticsDTO;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AnalyticsMapper {

    public Analytics toEntity(AnalyticsDTO dto) {
        if (dto == null) return null;
        
        Analytics entity = new Analytics();
        entity.setId(dto.getId());
        entity.setMetricType(dto.getMetricType());
        entity.setMetricKey(dto.getMetricKey());
        entity.setMetricValue(dto.getMetricValue());
        entity.setDateKey(dto.getDateKey());
        entity.setHourKey(dto.getHourKey());
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setDepartment(dto.getDepartment());
        entity.setMetadata(dto.getMetadata());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setEmployee(dto.getEmployee());
        return entity;
    }

    public AnalyticsDTO toDTO(Analytics entity) {
        if (entity == null) return null;
        
        AnalyticsDTO dto = new AnalyticsDTO();
        dto.setId(entity.getId());
        dto.setMetricType(entity.getMetricType());
        dto.setMetricKey(entity.getMetricKey());
        dto.setMetricValue(entity.getMetricValue());
        dto.setDateKey(entity.getDateKey());
        dto.setHourKey(entity.getHourKey());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setDepartment(entity.getDepartment());
        dto.setMetadata(entity.getMetadata());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setEmployee(entity.getEmployee());
        return dto;
    }

    public List<AnalyticsDTO> toDTOList(List<Analytics> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Analytics> toEntityList(List<AnalyticsDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void partialUpdate(AnalyticsDTO dto, Analytics entity) {
        if (dto == null || entity == null) return;

        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if (dto.getMetricType() != null) {
            entity.setMetricType(dto.getMetricType());
        }
        if (dto.getMetricKey() != null) {
            entity.setMetricKey(dto.getMetricKey());
        }
        if (dto.getMetricValue() != null) {
            entity.setMetricValue(dto.getMetricValue());
        }
        if (dto.getDateKey() != null) {
            entity.setDateKey(dto.getDateKey());
        }
        if (dto.getHourKey() != null) {
            entity.setHourKey(dto.getHourKey());
        }
        if (dto.getEmployeeId() != null) {
            entity.setEmployeeId(dto.getEmployeeId());
        }
        if (dto.getDepartment() != null) {
            entity.setDepartment(dto.getDepartment());
        }
        if (dto.getMetadata() != null) {
            entity.setMetadata(dto.getMetadata());
        }
        if (dto.getCreatedAt() != null) {
            entity.setCreatedAt(dto.getCreatedAt());
        }
        if (dto.getEmployee() != null) {
            entity.setEmployee(dto.getEmployee());
        }
    }
}