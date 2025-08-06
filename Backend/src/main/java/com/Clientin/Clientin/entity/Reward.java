package com.Clientin.Clientin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "rewards")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reward {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "employee_id", nullable = false)
    private String employeeId;
    
    @Column(name = "reward_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private RewardType rewardType;
    
    @Column(nullable = false, length = 200)
    private String title;
    
    @Column(length = 1000)
    private String description;
    
    @Column(name = "points_value")
    private Integer pointsValue;
    
    @Column(name = "monetary_value", precision = 10, scale = 2)
    private BigDecimal monetaryValue;
    
    @Column(name = "awarded_by", nullable = false)
    private String awardedBy;
    
    @Column(name = "reason", length = 500)
    private String reason;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RewardStatus status = RewardStatus.ACTIVE;
    
    @Column(name = "badge_id")
    private String badgeId; // Reference to Badge entity if applicable
    
    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;
    
    @Column(name = "redeemed_at")
    private LocalDateTime redeemedAt;
    
    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private RewardCategory category;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
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
