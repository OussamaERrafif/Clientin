package com.Clientin.Clientin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "improvement_proposals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImprovementProposal {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "employee_id", nullable = false)
    private String employeeId;
    
    @Column(nullable = false, length = 200)
    private String title;
    
    @Column(nullable = false, length = 2000)
    private String description;
    
    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProposalCategory category;
    
    @Column(name = "priority", nullable = false)
    @Enumerated(EnumType.STRING)
    private Priority priority = Priority.MEDIUM;
    
    @Column(name = "expected_impact", length = 1000)
    private String expectedImpact;
    
    @Column(name = "implementation_effort")
    @Enumerated(EnumType.STRING)
    private ImplementationEffort implementationEffort;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProposalStatus status = ProposalStatus.SUBMITTED;
    
    @Column(name = "reviewed_by")
    private String reviewedBy;
    
    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;
    
    @Column(name = "review_comments", length = 1000)
    private String reviewComments;
    
    @Column(name = "implemented_at")
    private LocalDateTime implementedAt;
    
    @Column(name = "implementation_notes", length = 1000)
    private String implementationNotes;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
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
