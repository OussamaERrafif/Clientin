package com.Clientin.Clientin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employee_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeProfile {
    
    @Id
    @Column(name = "user_id")
    private String userId;
    
    @Column(nullable = false, length = 100)
    private String position;
    
    @Column(nullable = false, length = 100)
    private String department;
    
    @Column(nullable = false)
    private Double salary;
    
    @Column(name = "leave_balance", nullable = false)
    private Integer leaveBalance = 0;
    
    @Column(name = "performance_score", nullable = false)
    private Double performanceScore = 0.0;
    
    @ElementCollection
    @CollectionTable(name = "employee_badges", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "badge")
    private List<String> badges = new ArrayList<>();
    
    @OneToMany(mappedBy = "employeeProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Goal> goals = new ArrayList<>();
    
    @Column(name = "training_progress", nullable = false)
    private Double trainingProgress = 0.0; // 0-100%
    
    // Foreign key relationship
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
}
