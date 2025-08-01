package com.Clientin.Clientin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "email_templates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailTemplate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "template_name", nullable = false, unique = true, length = 100)
    private String templateName;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TemplateType templateType;
    
    @Column(name = "subject", nullable = false, length = 200)
    private String subject;
    
    @Column(name = "html_content", nullable = false, columnDefinition = "TEXT")
    private String htmlContent;
    
    @Column(name = "text_content", columnDefinition = "TEXT")
    private String textContent;
    
    @Column(name = "variables", columnDefinition = "TEXT")
    private String variables;
    
    @Column(name = "active", nullable = false)
    private Boolean active = true;
    
    @Column(name = "version", nullable = false)
    private Integer version = 1;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    public enum TemplateType {
        WELCOME, PASSWORD_RESET, FEEDBACK_NOTIFICATION, PERFORMANCE_REPORT, SUBSCRIPTION_REMINDER
    }
}
