package com.Clientin.Clientin.dto;

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
public class PayslipDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String employeeId;
    private LocalDate payPeriod;
    private BigDecimal basicSalary;
    private Integer overtimeHours;
    private BigDecimal overtimeRate;
    private BigDecimal bonuses;
    private BigDecimal deductions;
    private BigDecimal taxDeductions;
    private BigDecimal socialSecurity;
    private BigDecimal grossPay;
    private BigDecimal netPay;
    private Integer workedDays;
    private Integer absenceDays;
    private PayslipStatus status;
    private String generatedBy;
    private LocalDateTime paidAt;
    private String notes;
    private LocalDateTime createdAt;
    
    public enum PayslipStatus {
        DRAFT, FINALIZED, PAID, CANCELLED
    }
}