package com.Clientin.Clientin.dto;

import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;
import com.Clientin.Clientin.entity.Analytics;
import com.Clientin.Clientin.entity.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private Analytics.MetricType metricType;
    private String metricKey;
    private BigDecimal metricValue;
    private LocalDate dateKey;
    private Integer hourKey;
    private String employeeId;
    private String department;
    private String metadata;
    private LocalDateTime createdAt;
    
    // Relationships
    private User employee;
}