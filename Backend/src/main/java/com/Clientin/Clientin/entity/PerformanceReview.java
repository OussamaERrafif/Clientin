package com.Clientin.Clientin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "performance_reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceReview {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "employee_id", nullable = false)
    private String employeeId;
    
    @Column(name = "reviewer_id", nullable = false)
    private String reviewerId;
    
    @Column(name = "review_period_start", nullable = false)
    private LocalDate reviewPeriodStart;
    
    @Column(name = "review_period_end", nullable = false)
    private LocalDate reviewPeriodEnd;
    
    @Column(name = "overall_score", nullable = false, precision = 3, scale = 2)
    private BigDecimal overallScore;
    
    @Column(name = "technical_skills_score", precision = 3, scale = 2)
    private BigDecimal technicalSkillsScore;
    
    @Column(name = "communication_score", precision = 3, scale = 2)
    private BigDecimal communicationScore;
    
    @Column(name = "teamwork_score", precision = 3, scale = 2)
    private BigDecimal teamworkScore;
    
    @Column(name = "leadership_score", precision = 3, scale = 2)
    private BigDecimal leadershipScore;
    
    @Column(name = "strengths", columnDefinition = "TEXT")
    private String strengths;
    
    @Column(name = "areas_for_improvement", columnDefinition = "TEXT")
    private String areasForImprovement;
    
    @Column(name = "goals", columnDefinition = "TEXT")
    private String goals;
    
    @Column(name = "reviewer_comments", columnDefinition = "TEXT")
    private String reviewerComments;
    
    @Column(name = "employee_comments", columnDefinition = "TEXT")
    private String employeeComments;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewStatus status = ReviewStatus.DRAFT;
    
    @Column(name = "completed_at")
    private LocalDateTime completedAt;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    // Foreign key relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", insertable = false, updatable = false)
    private User employee;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id", insertable = false, updatable = false)
    private User reviewer;
    
    public enum ReviewStatus {
        DRAFT, IN_PROGRESS, COMPLETED, APPROVED
    }
}
