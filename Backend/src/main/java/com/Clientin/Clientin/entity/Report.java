package com.Clientin.Clientin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "user_id", nullable = false)
    private String userId;
    
    @Column(name = "report_name", nullable = false, length = 200)
    private String reportName;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportType reportType;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status = ReportStatus.PENDING;
    
    @Column(name = "parameters", columnDefinition = "TEXT")
    private String parameters;
    
    @Column(name = "file_path")
    private String filePath;
    
    @Column(name = "file_size")
    private Long fileSize;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportFormat format = ReportFormat.PDF;
    
    @Column(name = "scheduled_for")
    private LocalDateTime scheduledFor;
    
    @Column(name = "generated_at")
    private LocalDateTime generatedAt;
    
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    // Foreign key relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
    
    public enum ReportType {
        FEEDBACK_SUMMARY, EMPLOYEE_PERFORMANCE, ANALYTICS_DASHBOARD, CUSTOM
    }
    
    public enum ReportStatus {
        PENDING, GENERATING, COMPLETED, FAILED, EXPIRED
    }
    
    public enum ReportFormat {
        PDF, EXCEL, CSV, JSON
    }
}
