package com.Clientin.Clientin.mapper;

import com.Clientin.Clientin.entity.EmployeeProfile;
import com.Clientin.Clientin.dto.EmployeeProfileDTO;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EmployeeProfileMapper {

    public EmployeeProfile toEntity(EmployeeProfileDTO dto) {
        if (dto == null) return null;
        
        EmployeeProfile entity = new EmployeeProfile();
        entity.setUserId(dto.getUserId());
        entity.setPosition(dto.getPosition());
        entity.setDepartment(dto.getDepartment());
        entity.setSalary(dto.getSalary());
        entity.setLeaveBalance(dto.getLeaveBalance());
        entity.setPerformanceScore(dto.getPerformanceScore());
        entity.setBadges(dto.getBadges());
        entity.setGoals(dto.getGoals());
        entity.setTrainingProgress(dto.getTrainingProgress());
        entity.setUser(dto.getUser());
        return entity;
    }

    public EmployeeProfileDTO toDTO(EmployeeProfile entity) {
        if (entity == null) return null;
        
        EmployeeProfileDTO dto = new EmployeeProfileDTO();
        dto.setUserId(entity.getUserId());
        dto.setPosition(entity.getPosition());
        dto.setDepartment(entity.getDepartment());
        dto.setSalary(entity.getSalary());
        dto.setLeaveBalance(entity.getLeaveBalance());
        dto.setPerformanceScore(entity.getPerformanceScore());
        dto.setBadges(entity.getBadges());
        dto.setGoals(entity.getGoals());
        dto.setTrainingProgress(entity.getTrainingProgress());
        dto.setUser(entity.getUser());
        return dto;
    }

    public List<EmployeeProfileDTO> toDTOList(List<EmployeeProfile> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<EmployeeProfile> toEntityList(List<EmployeeProfileDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void partialUpdate(EmployeeProfileDTO dto, EmployeeProfile entity) {
        if (dto == null || entity == null) return;

        if (dto.getUserId() != null) {
            entity.setUserId(dto.getUserId());
        }
        if (dto.getPosition() != null) {
            entity.setPosition(dto.getPosition());
        }
        if (dto.getDepartment() != null) {
            entity.setDepartment(dto.getDepartment());
        }
        if (dto.getSalary() != null) {
            entity.setSalary(dto.getSalary());
        }
        if (dto.getLeaveBalance() != null) {
            entity.setLeaveBalance(dto.getLeaveBalance());
        }
        if (dto.getPerformanceScore() != null) {
            entity.setPerformanceScore(dto.getPerformanceScore());
        }
        if (dto.getBadges() != null) {
            entity.setBadges(dto.getBadges());
        }
        if (dto.getGoals() != null) {
            entity.setGoals(dto.getGoals());
        }
        if (dto.getTrainingProgress() != null) {
            entity.setTrainingProgress(dto.getTrainingProgress());
        }
        if (dto.getUser() != null) {
            entity.setUser(dto.getUser());
        }
    }
}