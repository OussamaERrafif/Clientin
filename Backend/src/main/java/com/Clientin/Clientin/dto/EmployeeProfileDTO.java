package com.Clientin.Clientin.dto;

import com.Clientin.Clientin.entity.Goal;
import com.Clientin.Clientin.entity.User;
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
public class EmployeeProfileDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;
    private String position;
    private String department;
    private Double salary;
    private Integer leaveBalance;
    private Double performanceScore;
    private List<String> badges;
    private List<Goal> goals;
    private Double trainingProgress;
    private User user;
}