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
public class RewardDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String employeeId;
    private RewardType rewardType;
    private String title;
    private String description;
    private Integer pointsValue;
    private BigDecimal monetaryValue;
    private String awardedBy;
    private String reason;
    private RewardStatus status;
    private String badgeId;
    private LocalDateTime expiryDate;
    private LocalDateTime redeemedAt;
    private RewardCategory category;
    private LocalDateTime createdAt;
    
    public enum RewardType {
        POINTS, MONETARY, BADGE, CERTIFICATE, GIFT_VOUCHER, EXTRA_LEAVE, RECOGNITION
    }
    
    public enum RewardStatus {
        ACTIVE, REDEEMED, EXPIRED, CANCELLED
    }
    
    public enum RewardCategory {
        PERFORMANCE, ACHIEVEMENT, MILESTONE, RECOGNITION, INCENTIVE, APPRECIATION
    }
}