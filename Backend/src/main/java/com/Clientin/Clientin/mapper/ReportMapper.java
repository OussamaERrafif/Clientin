package com.Clientin.Clientin.mapper;

import com.Clientin.Clientin.entity.Report;
import com.Clientin.Clientin.dto.ReportDTO;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReportMapper {

    public Report toEntity(ReportDTO dto) {
        if (dto == null) return null;
        
        Report entity = new Report();
        entity.setId(dto.getId());
        entity.setUserId(dto.getUserId());
        entity.setReportName(dto.getReportName());
        entity.setReportType(dto.getReportType());
        entity.setStatus(dto.getStatus());
        entity.setParameters(dto.getParameters());
        entity.setFilePath(dto.getFilePath());
        entity.setFileSize(dto.getFileSize());
        entity.setFormat(dto.getFormat());
        entity.setScheduledFor(dto.getScheduledFor());
        entity.setGeneratedAt(dto.getGeneratedAt());
        entity.setExpiresAt(dto.getExpiresAt());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        entity.setUser(dto.getUser());
        return entity;
    }

    public ReportDTO toDTO(Report entity) {
        if (entity == null) return null;
        
        ReportDTO dto = new ReportDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setReportName(entity.getReportName());
        dto.setReportType(entity.getReportType());
        dto.setStatus(entity.getStatus());
        dto.setParameters(entity.getParameters());
        dto.setFilePath(entity.getFilePath());
        dto.setFileSize(entity.getFileSize());
        dto.setFormat(entity.getFormat());
        dto.setScheduledFor(entity.getScheduledFor());
        dto.setGeneratedAt(entity.getGeneratedAt());
        dto.setExpiresAt(entity.getExpiresAt());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setUser(entity.getUser());
        return dto;
    }

    public List<ReportDTO> toDTOList(List<Report> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Report> toEntityList(List<ReportDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void partialUpdate(ReportDTO dto, Report entity) {
        if (dto == null || entity == null) return;

        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if (dto.getUserId() != null) {
            entity.setUserId(dto.getUserId());
        }
        if (dto.getReportName() != null) {
            entity.setReportName(dto.getReportName());
        }
        if (dto.getReportType() != null) {
            entity.setReportType(dto.getReportType());
        }
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
        if (dto.getParameters() != null) {
            entity.setParameters(dto.getParameters());
        }
        if (dto.getFilePath() != null) {
            entity.setFilePath(dto.getFilePath());
        }
        if (dto.getFileSize() != null) {
            entity.setFileSize(dto.getFileSize());
        }
        if (dto.getFormat() != null) {
            entity.setFormat(dto.getFormat());
        }
        if (dto.getScheduledFor() != null) {
            entity.setScheduledFor(dto.getScheduledFor());
        }
        if (dto.getGeneratedAt() != null) {
            entity.setGeneratedAt(dto.getGeneratedAt());
        }
        if (dto.getExpiresAt() != null) {
            entity.setExpiresAt(dto.getExpiresAt());
        }
        if (dto.getCreatedAt() != null) {
            entity.setCreatedAt(dto.getCreatedAt());
        }
        if (dto.getUpdatedAt() != null) {
            entity.setUpdatedAt(dto.getUpdatedAt());
        }
        if (dto.getUser() != null) {
            entity.setUser(dto.getUser());
        }
    }
}