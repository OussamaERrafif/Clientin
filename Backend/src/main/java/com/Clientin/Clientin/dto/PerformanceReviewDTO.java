package com.Clientin.Clientin.dto;

import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;
import com.Clientin.Clientin.entity.PerformanceReview;
import com.Clientin.Clientin.entity.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceReviewDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String employeeId;
    private String reviewerId;
    private LocalDate reviewPeriodStart;
    private LocalDate reviewPeriodEnd;
    private BigDecimal overallScore;
    private BigDecimal technicalSkillsScore;
    private BigDecimal communicationScore;
    private BigDecimal teamworkScore;
    private BigDecimal leadershipScore;
    private String strengths;
    private String areasForImprovement;
    private String goals;
    private String reviewerComments;
    private String employeeComments;
    private PerformanceReview.ReviewStatus status;
    private LocalDateTime completedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // Relationship handled via employeeId
    // Relationship handled via reviewerId
}