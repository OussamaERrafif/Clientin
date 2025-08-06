package com.Clientin.Clientin.mapper;

import com.Clientin.Clientin.entity.Payslip;
import com.Clientin.Clientin.dto.PayslipDTO;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PayslipMapper {

    public Payslip toEntity(PayslipDTO dto) {
        if (dto == null) return null;
        
        Payslip entity = new Payslip();
        entity.setId(dto.getId());
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setPayPeriod(dto.getPayPeriod());
        entity.setBasicSalary(dto.getBasicSalary());
        entity.setOvertimeHours(dto.getOvertimeHours());
        entity.setOvertimeRate(dto.getOvertimeRate());
        entity.setBonuses(dto.getBonuses());
        entity.setDeductions(dto.getDeductions());
        entity.setTaxDeductions(dto.getTaxDeductions());
        entity.setSocialSecurity(dto.getSocialSecurity());
        entity.setGrossPay(dto.getGrossPay());
        entity.setNetPay(dto.getNetPay());
        entity.setWorkedDays(dto.getWorkedDays());
        entity.setAbsenceDays(dto.getAbsenceDays());
        entity.setStatus(dto.getStatus() != null ? Payslip.PayslipStatus.valueOf(dto.getStatus().name()) : null);
        entity.setGeneratedBy(dto.getGeneratedBy());
        entity.setPaidAt(dto.getPaidAt());
        entity.setNotes(dto.getNotes());
        entity.setCreatedAt(dto.getCreatedAt());
        return entity;
    }

    public PayslipDTO toDTO(Payslip entity) {
        if (entity == null) return null;
        
        PayslipDTO dto = new PayslipDTO();
        dto.setId(entity.getId());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setPayPeriod(entity.getPayPeriod());
        dto.setBasicSalary(entity.getBasicSalary());
        dto.setOvertimeHours(entity.getOvertimeHours());
        dto.setOvertimeRate(entity.getOvertimeRate());
        dto.setBonuses(entity.getBonuses());
        dto.setDeductions(entity.getDeductions());
        dto.setTaxDeductions(entity.getTaxDeductions());
        dto.setSocialSecurity(entity.getSocialSecurity());
        dto.setGrossPay(entity.getGrossPay());
        dto.setNetPay(entity.getNetPay());
        dto.setWorkedDays(entity.getWorkedDays());
        dto.setAbsenceDays(entity.getAbsenceDays());
        dto.setStatus(entity.getStatus() != null ? PayslipDTO.PayslipStatus.valueOf(entity.getStatus().name()) : null);
        dto.setGeneratedBy(entity.getGeneratedBy());
        dto.setPaidAt(entity.getPaidAt());
        dto.setNotes(entity.getNotes());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    public List<PayslipDTO> toDTOList(List<Payslip> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Payslip> toEntityList(List<PayslipDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void partialUpdate(PayslipDTO dto, Payslip entity) {
        if (dto == null || entity == null) return;

        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if (dto.getEmployeeId() != null) {
            entity.setEmployeeId(dto.getEmployeeId());
        }
        if (dto.getPayPeriod() != null) {
            entity.setPayPeriod(dto.getPayPeriod());
        }
        if (dto.getBasicSalary() != null) {
            entity.setBasicSalary(dto.getBasicSalary());
        }
        if (dto.getOvertimeHours() != null) {
            entity.setOvertimeHours(dto.getOvertimeHours());
        }
        if (dto.getOvertimeRate() != null) {
            entity.setOvertimeRate(dto.getOvertimeRate());
        }
        if (dto.getBonuses() != null) {
            entity.setBonuses(dto.getBonuses());
        }
        if (dto.getDeductions() != null) {
            entity.setDeductions(dto.getDeductions());
        }
        if (dto.getTaxDeductions() != null) {
            entity.setTaxDeductions(dto.getTaxDeductions());
        }
        if (dto.getSocialSecurity() != null) {
            entity.setSocialSecurity(dto.getSocialSecurity());
        }
        if (dto.getGrossPay() != null) {
            entity.setGrossPay(dto.getGrossPay());
        }
        if (dto.getNetPay() != null) {
            entity.setNetPay(dto.getNetPay());
        }
        if (dto.getWorkedDays() != null) {
            entity.setWorkedDays(dto.getWorkedDays());
        }
        if (dto.getAbsenceDays() != null) {
            entity.setAbsenceDays(dto.getAbsenceDays());
        }
        if (dto.getStatus() != null) {
            entity.setStatus(Payslip.PayslipStatus.valueOf(dto.getStatus().name()));
        }
        if (dto.getGeneratedBy() != null) {
            entity.setGeneratedBy(dto.getGeneratedBy());
        }
        if (dto.getPaidAt() != null) {
            entity.setPaidAt(dto.getPaidAt());
        }
        if (dto.getNotes() != null) {
            entity.setNotes(dto.getNotes());
        }
        if (dto.getCreatedAt() != null) {
            entity.setCreatedAt(dto.getCreatedAt());
        }
    }
}