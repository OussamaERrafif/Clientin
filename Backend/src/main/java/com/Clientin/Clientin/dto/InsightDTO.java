package com.Clientin.Clientin.dto;

import com.Clientin.Clientin.entity.Insight.Period;
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
public class InsightDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private Period period;
    private Long positiveCount;
    private Long negativeCount;
    private Double avgResponseTime;
    private Long totalFeedbacks;
    private LocalDateTime generatedAt;
}