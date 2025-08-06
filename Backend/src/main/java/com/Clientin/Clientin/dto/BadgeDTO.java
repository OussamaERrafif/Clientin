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
public class BadgeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String description;
    private BadgeType badgeType;
    private String iconUrl;
    private Integer pointsValue;
    private BadgeLevel level;
    private String criteria;
    private Boolean isActive;
    private String createdBy;
    private LocalDateTime createdAt;
    
    public enum BadgeType {
        PERFORMANCE, ATTENDANCE, TEAMWORK, INNOVATION, LEADERSHIP, CUSTOMER_SERVICE, TRAINING
    }
    
    public enum BadgeLevel {
        BRONZE, SILVER, GOLD, PLATINUM, DIAMOND
    }
}