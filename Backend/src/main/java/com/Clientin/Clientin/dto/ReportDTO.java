package com.Clientin.Clientin.dto;

import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;
import com.Clientin.Clientin.entity.Report;
import com.Clientin.Clientin.entity.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String userId;
    private String reportName;
    private Report.ReportType reportType;
    private Report.ReportStatus status;
    private String parameters;
    private String filePath;
    private Long fileSize;
    private Report.ReportFormat format;
    private LocalDateTime scheduledFor;
    private LocalDateTime generatedAt;
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Relationships
    private User user;
}