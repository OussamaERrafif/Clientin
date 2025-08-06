package com.Clientin.Clientin.mapper;

import com.Clientin.Clientin.entity.Absence;
import com.Clientin.Clientin.dto.AbsenceDTO;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AbsenceMapper {

    public Absence toEntity(AbsenceDTO dto) {
        if (dto == null) return null;
        
        Absence entity = new Absence();
        entity.setId(dto.getId());
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setAbsenceType(dto.getAbsenceType() != null ? Absence.AbsenceType.valueOf(dto.getAbsenceType().name()) : null);
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setDaysCount(dto.getDaysCount());
        entity.setReason(dto.getReason());
        entity.setStatus(dto.getStatus() != null ? Absence.AbsenceStatus.valueOf(dto.getStatus().name()) : null);
        entity.setApprovedBy(dto.getApprovedBy());
        entity.setApprovedAt(dto.getApprovedAt());
        entity.setIsPaid(dto.getIsPaid());
        entity.setComments(dto.getComments());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        return entity;
    }

    public AbsenceDTO toDTO(Absence entity) {
        if (entity == null) return null;
        
        AbsenceDTO dto = new AbsenceDTO();
        dto.setId(entity.getId());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setAbsenceType(entity.getAbsenceType() != null ? AbsenceDTO.AbsenceType.valueOf(entity.getAbsenceType().name()) : null);
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setDaysCount(entity.getDaysCount());
        dto.setReason(entity.getReason());
        dto.setStatus(entity.getStatus() != null ? AbsenceDTO.AbsenceStatus.valueOf(entity.getStatus().name()) : null);
        dto.setApprovedBy(entity.getApprovedBy());
        dto.setApprovedAt(entity.getApprovedAt());
        dto.setIsPaid(entity.getIsPaid());
        dto.setComments(entity.getComments());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public List<AbsenceDTO> toDTOList(List<Absence> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Absence> toEntityList(List<AbsenceDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void partialUpdate(AbsenceDTO dto, Absence entity) {
        if (dto == null || entity == null) return;

        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if (dto.getEmployeeId() != null) {
            entity.setEmployeeId(dto.getEmployeeId());
        }
        if (dto.getAbsenceType() != null) {
            entity.setAbsenceType(Absence.AbsenceType.valueOf(dto.getAbsenceType().name()));
        }
        if (dto.getStartDate() != null) {
            entity.setStartDate(dto.getStartDate());
        }
        if (dto.getEndDate() != null) {
            entity.setEndDate(dto.getEndDate());
        }
        if (dto.getDaysCount() != null) {
            entity.setDaysCount(dto.getDaysCount());
        }
        if (dto.getReason() != null) {
            entity.setReason(dto.getReason());
        }
        if (dto.getStatus() != null) {
            entity.setStatus(Absence.AbsenceStatus.valueOf(dto.getStatus().name()));
        }
        if (dto.getApprovedBy() != null) {
            entity.setApprovedBy(dto.getApprovedBy());
        }
        if (dto.getApprovedAt() != null) {
            entity.setApprovedAt(dto.getApprovedAt());
        }
        if (dto.getIsPaid() != null) {
            entity.setIsPaid(dto.getIsPaid());
        }
        if (dto.getComments() != null) {
            entity.setComments(dto.getComments());
        }
        if (dto.getCreatedAt() != null) {
            entity.setCreatedAt(dto.getCreatedAt());
        }
        if (dto.getUpdatedAt() != null) {
            entity.setUpdatedAt(dto.getUpdatedAt());
        }
    }
}