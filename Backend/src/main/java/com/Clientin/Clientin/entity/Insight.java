package com.Clientin.Clientin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "insights")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Insight {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Period period;
    
    @Column(name = "positive_count", nullable = false)
    private Long positiveCount = 0L;
    
    @Column(name = "negative_count", nullable = false)
    private Long negativeCount = 0L;
    
    @Column(name = "avg_response_time", nullable = false)
    private Double avgResponseTime = 0.0;
    
    @Column(name = "total_feedbacks", nullable = false)
    private Long totalFeedbacks = 0L;
    
    @CreationTimestamp
    @Column(name = "generated_at", nullable = false, updatable = false)
    private LocalDateTime generatedAt;
    
    public enum Period {
        DAILY, WEEKLY, MONTHLY
    }
}
