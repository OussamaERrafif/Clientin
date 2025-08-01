package com.Clientin.Clientin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "analytics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Analytics {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MetricType metricType;
    
    @Column(name = "metric_key", nullable = false, length = 100)
    private String metricKey;
    
    @Column(name = "metric_value", nullable = false, precision = 19, scale = 2)
    private BigDecimal metricValue;
    
    @Column(name = "date_key", nullable = false)
    private LocalDate dateKey;
    
    @Column(name = "hour_key")
    private Integer hourKey;
    
    @Column(name = "employee_id")
    private String employeeId;
    
    @Column(name = "department", length = 100)
    private String department;
    
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    // Foreign key relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", insertable = false, updatable = false)
    private User employee;
    
    public enum MetricType {
        FEEDBACK_COUNT, FEEDBACK_RATING, RESPONSE_TIME, PERFORMANCE_SCORE, SATISFACTION_RATE
    }
}
