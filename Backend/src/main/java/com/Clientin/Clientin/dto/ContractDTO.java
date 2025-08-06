package com.Clientin.Clientin.dto;

import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String employeeId;
    private ContractType contractType;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal salary;
    private Integer workingHours;
    private String position;
    private String department;
    private ContractStatus status;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public enum ContractType {
        PERMANENT, TEMPORARY, INTERN, FREELANCE
    }
    
    public enum ContractStatus {
        ACTIVE, TERMINATED, SUSPENDED, EXPIRED
    }
}