package com.Clientin.Clientin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Table(name = "contracts", indexes = {
    @Index(name = "idx_contract_employee", columnList = "employee_id"),
    @Index(name = "idx_contract_status", columnList = "status"),
    @Index(name = "idx_contract_dates", columnList = "start_date, end_date")
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Contract extends AuditableEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    // Relationship to User entity
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    @NotNull(message = "Employee is required")
    private User employee;
    
    // Keep the employeeId for backward compatibility
    @Column(name = "employee_id", insertable = false, updatable = false)
    private String employeeId;
    
    @Column(name = "contract_type", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Contract type is required")
    private ContractType contractType;
    
    @Column(name = "start_date", nullable = false)
    @NotNull(message = "Start date is required")
    private LocalDate startDate;
    
    @Column(name = "end_date")
    @Future(message = "End date must be in the future")
    private LocalDate endDate;
    
    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Salary is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Salary must be positive")
    private BigDecimal salary;
    
    @Column(name = "working_hours", nullable = false)
    @NotNull(message = "Working hours is required")
    @Min(value = 1, message = "Working hours must be at least 1")
    @Max(value = 168, message = "Working hours cannot exceed 168 per week")
    private Integer workingHours;
    
    @Column(name = "position", nullable = false, length = 100)
    @NotBlank(message = "Position is required")
    @Size(max = 100, message = "Position cannot exceed 100 characters")
    private String position;
    
    @Column(name = "department", length = 100)
    @Size(max = 100, message = "Department cannot exceed 100 characters")
    private String department;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContractStatus status = ContractStatus.ACTIVE;
    
    @Column(length = 500)
    @Size(max = 500, message = "Notes cannot exceed 500 characters")
    private String notes;
    
    // Business logic methods
    public void terminate() {
        this.status = ContractStatus.TERMINATED;
        this.endDate = LocalDate.now();
    }
    
    public void suspend() {
        this.status = ContractStatus.SUSPENDED;
    }
    
    public void reactivate() {
        if (this.status == ContractStatus.SUSPENDED) {
            this.status = ContractStatus.ACTIVE;
        }
    }
    
    public boolean isActive() {
        return status == ContractStatus.ACTIVE;
    }
    
    public boolean isExpired() {
        return endDate != null && endDate.isBefore(LocalDate.now());
    }
    
    @PrePersist
    @PreUpdate
    private void validateContract() {
        if (endDate != null && endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }
        
        if (contractType == ContractType.TEMPORARY && endDate == null) {
            throw new IllegalArgumentException("Temporary contracts must have an end date");
        }
    }
    
    public enum ContractType {
        PERMANENT, TEMPORARY, INTERN, FREELANCE
    }
    
    public enum ContractStatus {
        ACTIVE, TERMINATED, SUSPENDED, EXPIRED
    }
}
