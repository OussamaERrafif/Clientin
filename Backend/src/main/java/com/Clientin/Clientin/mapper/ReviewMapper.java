package com.Clientin.Clientin.mapper;

import com.Clientin.Clientin.entity.Review;
import com.Clientin.Clientin.dto.ReviewDTO;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReviewMapper {

    public Review toEntity(ReviewDTO dto) {
        if (dto == null) return null;
        
        Review entity = new Review();
        entity.setId(dto.getId());
        entity.setReviewerId(dto.getReviewerId());
        entity.setRevieweeId(dto.getRevieweeId());
        entity.setReviewType(dto.getReviewType() != null ? Review.ReviewType.valueOf(dto.getReviewType().name()) : null);
        entity.setReviewPeriod(dto.getReviewPeriod());
        entity.setOverallRating(dto.getOverallRating());
        entity.setPerformanceRating(dto.getPerformanceRating());
        entity.setTeamworkRating(dto.getTeamworkRating());
        entity.setCommunicationRating(dto.getCommunicationRating());
        entity.setLeadershipRating(dto.getLeadershipRating());
        entity.setStrengths(dto.getStrengths());
        entity.setAreasForImprovement(dto.getAreasForImprovement());
        entity.setGoalsForNextPeriod(dto.getGoalsForNextPeriod());
        entity.setAdditionalComments(dto.getAdditionalComments());
        entity.setStatus(dto.getStatus() != null ? Review.ReviewStatus.valueOf(dto.getStatus().name()) : null);
        entity.setSubmittedAt(dto.getSubmittedAt());
        entity.setAcknowledgedAt(dto.getAcknowledgedAt());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        return entity;
    }

    public ReviewDTO toDTO(Review entity) {
        if (entity == null) return null;
        
        ReviewDTO dto = new ReviewDTO();
        dto.setId(entity.getId());
        dto.setReviewerId(entity.getReviewerId());
        dto.setRevieweeId(entity.getRevieweeId());
        dto.setReviewType(entity.getReviewType() != null ? ReviewDTO.ReviewType.valueOf(entity.getReviewType().name()) : null);
        dto.setReviewPeriod(entity.getReviewPeriod());
        dto.setOverallRating(entity.getOverallRating());
        dto.setPerformanceRating(entity.getPerformanceRating());
        dto.setTeamworkRating(entity.getTeamworkRating());
        dto.setCommunicationRating(entity.getCommunicationRating());
        dto.setLeadershipRating(entity.getLeadershipRating());
        dto.setStrengths(entity.getStrengths());
        dto.setAreasForImprovement(entity.getAreasForImprovement());
        dto.setGoalsForNextPeriod(entity.getGoalsForNextPeriod());
        dto.setAdditionalComments(entity.getAdditionalComments());
        dto.setStatus(entity.getStatus() != null ? ReviewDTO.ReviewStatus.valueOf(entity.getStatus().name()) : null);
        dto.setSubmittedAt(entity.getSubmittedAt());
        dto.setAcknowledgedAt(entity.getAcknowledgedAt());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public List<ReviewDTO> toDTOList(List<Review> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Review> toEntityList(List<ReviewDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void partialUpdate(ReviewDTO dto, Review entity) {
        if (dto == null || entity == null) return;

        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if (dto.getReviewerId() != null) {
            entity.setReviewerId(dto.getReviewerId());
        }
        if (dto.getRevieweeId() != null) {
            entity.setRevieweeId(dto.getRevieweeId());
        }
        if (dto.getReviewType() != null) {
            entity.setReviewType(Review.ReviewType.valueOf(dto.getReviewType().name()));
        }
        if (dto.getReviewPeriod() != null) {
            entity.setReviewPeriod(dto.getReviewPeriod());
        }
        if (dto.getOverallRating() != null) {
            entity.setOverallRating(dto.getOverallRating());
        }
        if (dto.getPerformanceRating() != null) {
            entity.setPerformanceRating(dto.getPerformanceRating());
        }
        if (dto.getTeamworkRating() != null) {
            entity.setTeamworkRating(dto.getTeamworkRating());
        }
        if (dto.getCommunicationRating() != null) {
            entity.setCommunicationRating(dto.getCommunicationRating());
        }
        if (dto.getLeadershipRating() != null) {
            entity.setLeadershipRating(dto.getLeadershipRating());
        }
        if (dto.getStrengths() != null) {
            entity.setStrengths(dto.getStrengths());
        }
        if (dto.getAreasForImprovement() != null) {
            entity.setAreasForImprovement(dto.getAreasForImprovement());
        }
        if (dto.getGoalsForNextPeriod() != null) {
            entity.setGoalsForNextPeriod(dto.getGoalsForNextPeriod());
        }
        if (dto.getAdditionalComments() != null) {
            entity.setAdditionalComments(dto.getAdditionalComments());
        }
        if (dto.getStatus() != null) {
            entity.setStatus(Review.ReviewStatus.valueOf(dto.getStatus().name()));
        }
        if (dto.getSubmittedAt() != null) {
            entity.setSubmittedAt(dto.getSubmittedAt());
        }
        if (dto.getAcknowledgedAt() != null) {
            entity.setAcknowledgedAt(dto.getAcknowledgedAt());
        }
        if (dto.getCreatedAt() != null) {
            entity.setCreatedAt(dto.getCreatedAt());
        }
        if (dto.getUpdatedAt() != null) {
            entity.setUpdatedAt(dto.getUpdatedAt());
        }
    }
}