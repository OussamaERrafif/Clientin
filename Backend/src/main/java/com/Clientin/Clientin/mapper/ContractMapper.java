package com.Clientin.Clientin.mapper;

import com.Clientin.Clientin.entity.Contract;
import com.Clientin.Clientin.dto.ContractDTO;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ContractMapper {

    public Contract toEntity(ContractDTO dto) {
        if (dto == null) return null;
        
        Contract entity = new Contract();
        entity.setId(dto.getId());
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setContractType(dto.getContractType() != null ? Contract.ContractType.valueOf(dto.getContractType().name()) : null);
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setSalary(dto.getSalary());
        entity.setWorkingHours(dto.getWorkingHours());
        entity.setPosition(dto.getPosition());
        entity.setDepartment(dto.getDepartment());
        entity.setStatus(dto.getStatus() != null ? Contract.ContractStatus.valueOf(dto.getStatus().name()) : null);
        entity.setNotes(dto.getNotes());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        return entity;
    }

    public ContractDTO toDTO(Contract entity) {
        if (entity == null) return null;
        
        ContractDTO dto = new ContractDTO();
        dto.setId(entity.getId());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setContractType(entity.getContractType() != null ? ContractDTO.ContractType.valueOf(entity.getContractType().name()) : null);
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setSalary(entity.getSalary());
        dto.setWorkingHours(entity.getWorkingHours());
        dto.setPosition(entity.getPosition());
        dto.setDepartment(entity.getDepartment());
        dto.setStatus(entity.getStatus() != null ? ContractDTO.ContractStatus.valueOf(entity.getStatus().name()) : null);
        dto.setNotes(entity.getNotes());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public List<ContractDTO> toDTOList(List<Contract> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Contract> toEntityList(List<ContractDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void partialUpdate(ContractDTO dto, Contract entity) {
        if (dto == null || entity == null) return;

        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if (dto.getEmployeeId() != null) {
            entity.setEmployeeId(dto.getEmployeeId());
        }
        if (dto.getContractType() != null) {
            entity.setContractType(Contract.ContractType.valueOf(dto.getContractType().name()));
        }
        if (dto.getStartDate() != null) {
            entity.setStartDate(dto.getStartDate());
        }
        if (dto.getEndDate() != null) {
            entity.setEndDate(dto.getEndDate());
        }
        if (dto.getSalary() != null) {
            entity.setSalary(dto.getSalary());
        }
        if (dto.getWorkingHours() != null) {
            entity.setWorkingHours(dto.getWorkingHours());
        }
        if (dto.getPosition() != null) {
            entity.setPosition(dto.getPosition());
        }
        if (dto.getDepartment() != null) {
            entity.setDepartment(dto.getDepartment());
        }
        if (dto.getStatus() != null) {
            entity.setStatus(Contract.ContractStatus.valueOf(dto.getStatus().name()));
        }
        if (dto.getNotes() != null) {
            entity.setNotes(dto.getNotes());
        }
        if (dto.getCreatedAt() != null) {
            entity.setCreatedAt(dto.getCreatedAt());
        }
        if (dto.getUpdatedAt() != null) {
            entity.setUpdatedAt(dto.getUpdatedAt());
        }
    }
}