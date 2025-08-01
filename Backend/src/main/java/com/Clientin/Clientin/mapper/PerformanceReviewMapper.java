package com.Clientin.Clientin.mapper;

import com.Clientin.Clientin.entity.PerformanceReview;
import com.Clientin.Clientin.dto.PerformanceReviewDTO;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PerformanceReviewMapper {

    public PerformanceReview toEntity(PerformanceReviewDTO dto) {
        if (dto == null) return null;
        
        PerformanceReview entity = new PerformanceReview();
        entity.setId(dto.getId());
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setReviewerId(dto.getReviewerId());
        entity.setReviewPeriodStart(dto.getReviewPeriodStart());
        entity.setReviewPeriodEnd(dto.getReviewPeriodEnd());
        entity.setOverallScore(dto.getOverallScore());
        entity.setTechnicalSkillsScore(dto.getTechnicalSkillsScore());
        entity.setCommunicationScore(dto.getCommunicationScore());
        entity.setTeamworkScore(dto.getTeamworkScore());
        entity.setLeadershipScore(dto.getLeadershipScore());
        entity.setStrengths(dto.getStrengths());
        entity.setAreasForImprovement(dto.getAreasForImprovement());
        entity.setGoals(dto.getGoals());
        entity.setReviewerComments(dto.getReviewerComments());
        entity.setEmployeeComments(dto.getEmployeeComments());
        entity.setStatus(dto.getStatus());
        entity.setCompletedAt(dto.getCompletedAt());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        entity.setEmployee(dto.getEmployee());
        entity.setReviewer(dto.getReviewer());
        return entity;
    }

    public PerformanceReviewDTO toDTO(PerformanceReview entity) {
        if (entity == null) return null;
        
        PerformanceReviewDTO dto = new PerformanceReviewDTO();
        dto.setId(entity.getId());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setReviewerId(entity.getReviewerId());
        dto.setReviewPeriodStart(entity.getReviewPeriodStart());
        dto.setReviewPeriodEnd(entity.getReviewPeriodEnd());
        dto.setOverallScore(entity.getOverallScore());
        dto.setTechnicalSkillsScore(entity.getTechnicalSkillsScore());
        dto.setCommunicationScore(entity.getCommunicationScore());
        dto.setTeamworkScore(entity.getTeamworkScore());
        dto.setLeadershipScore(entity.getLeadershipScore());
        dto.setStrengths(entity.getStrengths());
        dto.setAreasForImprovement(entity.getAreasForImprovement());
        dto.setGoals(entity.getGoals());
        dto.setReviewerComments(entity.getReviewerComments());
        dto.setEmployeeComments(entity.getEmployeeComments());
        dto.setStatus(entity.getStatus());
        dto.setCompletedAt(entity.getCompletedAt());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setEmployee(entity.getEmployee());
        dto.setReviewer(entity.getReviewer());
        return dto;
    }

    public List<PerformanceReviewDTO> toDTOList(List<PerformanceReview> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<PerformanceReview> toEntityList(List<PerformanceReviewDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void partialUpdate(PerformanceReviewDTO dto, PerformanceReview entity) {
        if (dto == null || entity == null) return;

        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if (dto.getEmployeeId() != null) {
            entity.setEmployeeId(dto.getEmployeeId());
        }
        if (dto.getReviewerId() != null) {
            entity.setReviewerId(dto.getReviewerId());
        }
        if (dto.getReviewPeriodStart() != null) {
            entity.setReviewPeriodStart(dto.getReviewPeriodStart());
        }
        if (dto.getReviewPeriodEnd() != null) {
            entity.setReviewPeriodEnd(dto.getReviewPeriodEnd());
        }
        if (dto.getOverallScore() != null) {
            entity.setOverallScore(dto.getOverallScore());
        }
        if (dto.getTechnicalSkillsScore() != null) {
            entity.setTechnicalSkillsScore(dto.getTechnicalSkillsScore());
        }
        if (dto.getCommunicationScore() != null) {
            entity.setCommunicationScore(dto.getCommunicationScore());
        }
        if (dto.getTeamworkScore() != null) {
            entity.setTeamworkScore(dto.getTeamworkScore());
        }
        if (dto.getLeadershipScore() != null) {
            entity.setLeadershipScore(dto.getLeadershipScore());
        }
        if (dto.getStrengths() != null) {
            entity.setStrengths(dto.getStrengths());
        }
        if (dto.getAreasForImprovement() != null) {
            entity.setAreasForImprovement(dto.getAreasForImprovement());
        }
        if (dto.getGoals() != null) {
            entity.setGoals(dto.getGoals());
        }
        if (dto.getReviewerComments() != null) {
            entity.setReviewerComments(dto.getReviewerComments());
        }
        if (dto.getEmployeeComments() != null) {
            entity.setEmployeeComments(dto.getEmployeeComments());
        }
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
        if (dto.getCompletedAt() != null) {
            entity.setCompletedAt(dto.getCompletedAt());
        }
        if (dto.getCreatedAt() != null) {
            entity.setCreatedAt(dto.getCreatedAt());
        }
        if (dto.getUpdatedAt() != null) {
            entity.setUpdatedAt(dto.getUpdatedAt());
        }
        if (dto.getEmployee() != null) {
            entity.setEmployee(dto.getEmployee());
        }
        if (dto.getReviewer() != null) {
            entity.setReviewer(dto.getReviewer());
        }
    }
}