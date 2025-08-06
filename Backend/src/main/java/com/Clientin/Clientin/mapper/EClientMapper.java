package com.Clientin.Clientin.mapper;

import com.Clientin.Clientin.entity.EClient;
import com.Clientin.Clientin.dto.EClientDTO;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EClientMapper {

    public EClient toEntity(EClientDTO dto) {
        if (dto == null) return null;
        
        EClient entity = new EClient();
        entity.setId(dto.getId());
        entity.setCompanyName(dto.getCompanyName());
        entity.setContactPerson(dto.getContactPerson());
        entity.setEmail(dto.getEmail());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setAddress(dto.getAddress());
        entity.setIndustry(dto.getIndustry());
        entity.setCompanySize(dto.getCompanySize() != null ? EClient.CompanySize.valueOf(dto.getCompanySize().name()) : null);
        entity.setSubscriptionType(dto.getSubscriptionType() != null ? EClient.SubscriptionType.valueOf(dto.getSubscriptionType().name()) : null);
        entity.setStatus(dto.getStatus() != null ? EClient.ClientStatus.valueOf(dto.getStatus().name()) : null);
        entity.setContractStartDate(dto.getContractStartDate());
        entity.setContractEndDate(dto.getContractEndDate());
        entity.setAssignedAccountManager(dto.getAssignedAccountManager());
        entity.setNotes(dto.getNotes());
        entity.setLogoUrl(dto.getLogoUrl());
        entity.setWebsiteUrl(dto.getWebsiteUrl());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        return entity;
    }

    public EClientDTO toDTO(EClient entity) {
        if (entity == null) return null;
        
        EClientDTO dto = new EClientDTO();
        dto.setId(entity.getId());
        dto.setCompanyName(entity.getCompanyName());
        dto.setContactPerson(entity.getContactPerson());
        dto.setEmail(entity.getEmail());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setAddress(entity.getAddress());
        dto.setIndustry(entity.getIndustry());
        dto.setCompanySize(entity.getCompanySize() != null ? EClientDTO.CompanySize.valueOf(entity.getCompanySize().name()) : null);
        dto.setSubscriptionType(entity.getSubscriptionType() != null ? EClientDTO.SubscriptionType.valueOf(entity.getSubscriptionType().name()) : null);
        dto.setStatus(entity.getStatus() != null ? EClientDTO.ClientStatus.valueOf(entity.getStatus().name()) : null);
        dto.setContractStartDate(entity.getContractStartDate());
        dto.setContractEndDate(entity.getContractEndDate());
        dto.setAssignedAccountManager(entity.getAssignedAccountManager());
        dto.setNotes(entity.getNotes());
        dto.setLogoUrl(entity.getLogoUrl());
        dto.setWebsiteUrl(entity.getWebsiteUrl());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public List<EClientDTO> toDTOList(List<EClient> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<EClient> toEntityList(List<EClientDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void partialUpdate(EClientDTO dto, EClient entity) {
        if (dto == null || entity == null) return;

        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if (dto.getCompanyName() != null) {
            entity.setCompanyName(dto.getCompanyName());
        }
        if (dto.getContactPerson() != null) {
            entity.setContactPerson(dto.getContactPerson());
        }
        if (dto.getEmail() != null) {
            entity.setEmail(dto.getEmail());
        }
        if (dto.getPhoneNumber() != null) {
            entity.setPhoneNumber(dto.getPhoneNumber());
        }
        if (dto.getAddress() != null) {
            entity.setAddress(dto.getAddress());
        }
        if (dto.getIndustry() != null) {
            entity.setIndustry(dto.getIndustry());
        }
        if (dto.getCompanySize() != null) {
            entity.setCompanySize(EClient.CompanySize.valueOf(dto.getCompanySize().name()));
        }
        if (dto.getSubscriptionType() != null) {
            entity.setSubscriptionType(EClient.SubscriptionType.valueOf(dto.getSubscriptionType().name()));
        }
        if (dto.getStatus() != null) {
            entity.setStatus(EClient.ClientStatus.valueOf(dto.getStatus().name()));
        }
        if (dto.getContractStartDate() != null) {
            entity.setContractStartDate(dto.getContractStartDate());
        }
        if (dto.getContractEndDate() != null) {
            entity.setContractEndDate(dto.getContractEndDate());
        }
        if (dto.getAssignedAccountManager() != null) {
            entity.setAssignedAccountManager(dto.getAssignedAccountManager());
        }
        if (dto.getNotes() != null) {
            entity.setNotes(dto.getNotes());
        }
        if (dto.getLogoUrl() != null) {
            entity.setLogoUrl(dto.getLogoUrl());
        }
        if (dto.getWebsiteUrl() != null) {
            entity.setWebsiteUrl(dto.getWebsiteUrl());
        }
        if (dto.getCreatedAt() != null) {
            entity.setCreatedAt(dto.getCreatedAt());
        }
        if (dto.getUpdatedAt() != null) {
            entity.setUpdatedAt(dto.getUpdatedAt());
        }
    }
}