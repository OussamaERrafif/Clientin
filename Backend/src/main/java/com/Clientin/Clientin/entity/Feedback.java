package com.Clientin.Clientin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "feedbacks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "client_id", nullable = false)
    private String clientId;
    
    @Column(name = "employee_id")
    private String employeeId;
    
    @Column(nullable = false)
    private Integer rating; // 1 to 5
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sentiment sentiment;
    
    @Column(columnDefinition = "TEXT")
    private String comment;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Source source;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    // Foreign key relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    private Client client;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", insertable = false, updatable = false)
    private User employee;
    
    public enum Sentiment {
        POSITIVE, NEGATIVE, NEUTRAL
    }
    
    public enum Source {
        NFC, WEB, MOBILE
    }
}
