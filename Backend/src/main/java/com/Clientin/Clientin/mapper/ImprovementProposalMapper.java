package com.Clientin.Clientin.mapper;

import com.Clientin.Clientin.entity.ImprovementProposal;
import com.Clientin.Clientin.dto.ImprovementProposalDTO;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ImprovementProposalMapper {

    public ImprovementProposal toEntity(ImprovementProposalDTO dto) {
        if (dto == null) return null;
        
        ImprovementProposal entity = new ImprovementProposal();
        entity.setId(dto.getId());
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setCategory(dto.getCategory() != null ? ImprovementProposal.ProposalCategory.valueOf(dto.getCategory().name()) : null);
        entity.setPriority(dto.getPriority() != null ? ImprovementProposal.Priority.valueOf(dto.getPriority().name()) : null);
        entity.setExpectedImpact(dto.getExpectedImpact());
        entity.setImplementationEffort(dto.getImplementationEffort() != null ? ImprovementProposal.ImplementationEffort.valueOf(dto.getImplementationEffort().name()) : null);
        entity.setStatus(dto.getStatus() != null ? ImprovementProposal.ProposalStatus.valueOf(dto.getStatus().name()) : null);
        entity.setReviewedBy(dto.getReviewedBy());
        entity.setReviewedAt(dto.getReviewedAt());
        entity.setReviewComments(dto.getReviewComments());
        entity.setImplementedAt(dto.getImplementedAt());
        entity.setImplementationNotes(dto.getImplementationNotes());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        return entity;
    }

    public ImprovementProposalDTO toDTO(ImprovementProposal entity) {
        if (entity == null) return null;
        
        ImprovementProposalDTO dto = new ImprovementProposalDTO();
        dto.setId(entity.getId());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setCategory(entity.getCategory() != null ? ImprovementProposalDTO.ProposalCategory.valueOf(entity.getCategory().name()) : null);
        dto.setPriority(entity.getPriority() != null ? ImprovementProposalDTO.Priority.valueOf(entity.getPriority().name()) : null);
        dto.setExpectedImpact(entity.getExpectedImpact());
        dto.setImplementationEffort(entity.getImplementationEffort() != null ? ImprovementProposalDTO.ImplementationEffort.valueOf(entity.getImplementationEffort().name()) : null);
        dto.setStatus(entity.getStatus() != null ? ImprovementProposalDTO.ProposalStatus.valueOf(entity.getStatus().name()) : null);
        dto.setReviewedBy(entity.getReviewedBy());
        dto.setReviewedAt(entity.getReviewedAt());
        dto.setReviewComments(entity.getReviewComments());
        dto.setImplementedAt(entity.getImplementedAt());
        dto.setImplementationNotes(entity.getImplementationNotes());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public List<ImprovementProposalDTO> toDTOList(List<ImprovementProposal> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ImprovementProposal> toEntityList(List<ImprovementProposalDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void partialUpdate(ImprovementProposalDTO dto, ImprovementProposal entity) {
        if (dto == null || entity == null) return;

        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if (dto.getEmployeeId() != null) {
            entity.setEmployeeId(dto.getEmployeeId());
        }
        if (dto.getTitle() != null) {
            entity.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }
        if (dto.getCategory() != null) {
            entity.setCategory(ImprovementProposal.ProposalCategory.valueOf(dto.getCategory().name()));
        }
        if (dto.getPriority() != null) {
            entity.setPriority(ImprovementProposal.Priority.valueOf(dto.getPriority().name()));
        }
        if (dto.getExpectedImpact() != null) {
            entity.setExpectedImpact(dto.getExpectedImpact());
        }
        if (dto.getImplementationEffort() != null) {
            entity.setImplementationEffort(ImprovementProposal.ImplementationEffort.valueOf(dto.getImplementationEffort().name()));
        }
        if (dto.getStatus() != null) {
            entity.setStatus(ImprovementProposal.ProposalStatus.valueOf(dto.getStatus().name()));
        }
        if (dto.getReviewedBy() != null) {
            entity.setReviewedBy(dto.getReviewedBy());
        }
        if (dto.getReviewedAt() != null) {
            entity.setReviewedAt(dto.getReviewedAt());
        }
        if (dto.getReviewComments() != null) {
            entity.setReviewComments(dto.getReviewComments());
        }
        if (dto.getImplementedAt() != null) {
            entity.setImplementedAt(dto.getImplementedAt());
        }
        if (dto.getImplementationNotes() != null) {
            entity.setImplementationNotes(dto.getImplementationNotes());
        }
        if (dto.getCreatedAt() != null) {
            entity.setCreatedAt(dto.getCreatedAt());
        }
        if (dto.getUpdatedAt() != null) {
            entity.setUpdatedAt(dto.getUpdatedAt());
        }
    }
}