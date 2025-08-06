package com.Clientin.Clientin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "reviewer_id", nullable = false)
    private String reviewerId;
    
    @Column(name = "reviewee_id", nullable = false)
    private String revieweeId;
    
    @Column(name = "review_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReviewType reviewType;
    
    @Column(name = "review_period", nullable = false)
    private String reviewPeriod; // e.g., "Q1 2024", "2024 Annual"
    
    @Column(name = "overall_rating", nullable = false)
    private Integer overallRating; // 1-5 scale
    
    @Column(name = "performance_rating")
    private Integer performanceRating; // 1-5 scale
    
    @Column(name = "teamwork_rating")
    private Integer teamworkRating; // 1-5 scale
    
    @Column(name = "communication_rating")
    private Integer communicationRating; // 1-5 scale
    
    @Column(name = "leadership_rating")
    private Integer leadershipRating; // 1-5 scale
    
    @Column(name = "strengths", length = 1000)
    private String strengths;
    
    @Column(name = "areas_for_improvement", length = 1000)
    private String areasForImprovement;
    
    @Column(name = "goals_for_next_period", length = 1000)
    private String goalsForNextPeriod;
    
    @Column(name = "additional_comments", length = 1000)
    private String additionalComments;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewStatus status = ReviewStatus.DRAFT;
    
    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;
    
    @Column(name = "acknowledged_at")
    private LocalDateTime acknowledgedAt;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    public enum ReviewType {
        QUARTERLY, ANNUAL, PROJECT_BASED, PROBATION, EXIT
    }
    
    public enum ReviewStatus {
        DRAFT, SUBMITTED, ACKNOWLEDGED, COMPLETED
    }
}
