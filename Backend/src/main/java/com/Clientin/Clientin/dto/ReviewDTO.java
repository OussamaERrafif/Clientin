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
public class ReviewDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String reviewerId;
    private String revieweeId;
    private ReviewType reviewType;
    private String reviewPeriod;
    private Integer overallRating;
    private Integer performanceRating;
    private Integer teamworkRating;
    private Integer communicationRating;
    private Integer leadershipRating;
    private String strengths;
    private String areasForImprovement;
    private String goalsForNextPeriod;
    private String additionalComments;
    private ReviewStatus status;
    private LocalDateTime submittedAt;
    private LocalDateTime acknowledgedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public enum ReviewType {
        QUARTERLY, ANNUAL, PROJECT_BASED, PROBATION, EXIT
    }
    
    public enum ReviewStatus {
        DRAFT, SUBMITTED, ACKNOWLEDGED, COMPLETED
    }
}