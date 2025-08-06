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
public class ImprovementProposalDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String employeeId;
    private String title;
    private String description;
    private ProposalCategory category;
    private Priority priority;
    private String expectedImpact;
    private ImplementationEffort implementationEffort;
    private ProposalStatus status;
    private String reviewedBy;
    private LocalDateTime reviewedAt;
    private String reviewComments;
    private LocalDateTime implementedAt;
    private String implementationNotes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public enum ProposalCategory {
        PROCESS_IMPROVEMENT, COST_REDUCTION, QUALITY_ENHANCEMENT, TECHNOLOGY, WORKPLACE_ENVIRONMENT, CUSTOMER_SERVICE
    }
    
    public enum Priority {
        LOW, MEDIUM, HIGH, CRITICAL
    }
    
    public enum ImplementationEffort {
        LOW, MEDIUM, HIGH, VERY_HIGH
    }
    
    public enum ProposalStatus {
        SUBMITTED, UNDER_REVIEW, APPROVED, REJECTED, IMPLEMENTED, CLOSED
    }
}