package com.Clientin.Clientin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "badges")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Badge {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(length = 500)
    private String description;
    
    @Column(name = "badge_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private BadgeType badgeType;
    
    @Column(name = "icon_url")
    private String iconUrl;
    
    @Column(name = "points_value", nullable = false)
    private Integer pointsValue = 0;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BadgeLevel level = BadgeLevel.BRONZE;
    
    @Column(name = "criteria", length = 1000)
    private String criteria;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @Column(name = "created_by", nullable = false)
    private String createdBy;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    public enum BadgeType {
        PERFORMANCE, ATTENDANCE, TEAMWORK, INNOVATION, LEADERSHIP, CUSTOMER_SERVICE, TRAINING
    }
    
    public enum BadgeLevel {
        BRONZE, SILVER, GOLD, PLATINUM, DIAMOND
    }
}
