package com.Clientin.Clientin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "payslips")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payslip {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "employee_id", nullable = false)
    private String employeeId;
    
    @Column(name = "pay_period", nullable = false)
    private LocalDate payPeriod;
    
    @Column(name = "basic_salary", nullable = false, precision = 10, scale = 2)
    private BigDecimal basicSalary;
    
    @Column(name = "overtime_hours")
    private Integer overtimeHours = 0;
    
    @Column(name = "overtime_rate", precision = 10, scale = 2)
    private BigDecimal overtimeRate = BigDecimal.ZERO;
    
    @Column(name = "bonuses", precision = 10, scale = 2)
    private BigDecimal bonuses = BigDecimal.ZERO;
    
    @Column(name = "deductions", precision = 10, scale = 2)
    private BigDecimal deductions = BigDecimal.ZERO;
    
    @Column(name = "tax_deductions", precision = 10, scale = 2)
    private BigDecimal taxDeductions = BigDecimal.ZERO;
    
    @Column(name = "social_security", precision = 10, scale = 2)
    private BigDecimal socialSecurity = BigDecimal.ZERO;
    
    @Column(name = "gross_pay", nullable = false, precision = 10, scale = 2)
    private BigDecimal grossPay;
    
    @Column(name = "net_pay", nullable = false, precision = 10, scale = 2)
    private BigDecimal netPay;
    
    @Column(name = "worked_days", nullable = false)
    private Integer workedDays;
    
    @Column(name = "absence_days")
    private Integer absenceDays = 0;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PayslipStatus status = PayslipStatus.DRAFT;
    
    @Column(name = "generated_by", nullable = false)
    private String generatedBy;
    
    @Column(name = "paid_at")
    private LocalDateTime paidAt;
    
    @Column(length = 500)
    private String notes;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    public enum PayslipStatus {
        DRAFT, FINALIZED, PAID, CANCELLED
    }
}
