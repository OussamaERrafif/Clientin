package com.Clientin.Clientin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "notification_preferences")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationPreference {
    
    @Id
    @Column(name = "user_id")
    private String userId;
    
    @Column(name = "email_alerts", nullable = false)
    private Boolean emailAlerts = true;
    
    @Column(name = "push_notifications", nullable = false)
    private Boolean pushNotifications = true;
    
    @Column(name = "weekly_summary", nullable = false)
    private Boolean weeklySummary = true;
    
    // Foreign key relationship
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
}
