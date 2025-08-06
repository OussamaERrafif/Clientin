package com.Clientin.Clientin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "absences", indexes = {
    @Index(name = "idx_absence_employee", columnList = "employee_id"),
    @Index(name = "idx_absence_dates", columnList = "start_date, end_date"),
    @Index(name = "idx_absence_status", columnList = "status")
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Absence extends AuditableEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    // Relationship to User entity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    @NotNull(message = "Employee is required")
    private User employee;
    
    // Keep the employeeId for backward compatibility and easier queries
    @Column(name = "employee_id", insertable = false, updatable = false)
    private String employeeId;
    
    @Column(name = "absence_type", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Absence type is required")
    private AbsenceType absenceType;
    
    @Column(name = "start_date", nullable = false)
    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date cannot be in the past")
    private LocalDate startDate;
    
    @Column(name = "end_date", nullable = false)
    @NotNull(message = "End date is required")
    private LocalDate endDate;
    
    @Column(name = "days_count", nullable = false)
    @Positive(message = "Days count must be positive")
    private Integer daysCount;
    
    @Column(length = 500)
    @Size(max = 500, message = "Reason cannot exceed 500 characters")
    private String reason;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AbsenceStatus status = AbsenceStatus.PENDING;
    
    // Relationship to approver
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by")
    private User approver;
    
    // Keep the approvedBy for backward compatibility
    @Column(name = "approved_by", insertable = false, updatable = false)
    private String approvedBy;
    
    @Column(name = "approved_at")
    private LocalDateTime approvedAt;
    
    @Column(name = "is_paid", nullable = false)
    private Boolean isPaid = false;
    
    @Column(length = 500)
    @Size(max = 500, message = "Comments cannot exceed 500 characters")
    private String comments;
    
    // Business logic methods
    public void approve(User approver) {
        this.status = AbsenceStatus.APPROVED;
        this.approver = approver;
        this.approvedAt = LocalDateTime.now();
    }
    
    public void reject(User approver, String reason) {
        this.status = AbsenceStatus.REJECTED;
        this.approver = approver;
        this.approvedAt = LocalDateTime.now();
        this.comments = reason;
    }
    
    public boolean isApproved() {
        return status == AbsenceStatus.APPROVED;
    }
    
    public boolean isPending() {
        return status == AbsenceStatus.PENDING;
    }
    
    @PrePersist
    @PreUpdate
    private void validateDates() {
        if (endDate != null && startDate != null && endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }
    }
    
    public enum AbsenceType {
        VACATION, SICK_LEAVE, PERSONAL_LEAVE, MATERNITY_LEAVE, PATERNITY_LEAVE, EMERGENCY_LEAVE
    }
    
    public enum AbsenceStatus {
        PENDING, APPROVED, REJECTED, CANCELLED
    }
}
