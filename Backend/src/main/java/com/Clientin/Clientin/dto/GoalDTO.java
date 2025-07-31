package com.Clientin.Clientin.dto;

import com.Clientin.Clientin.entity.EmployeeProfile;
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
public class GoalDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String description;
    private Double progress;
    private LocalDate dueDate;
    private String employeeProfileUserId;
    private EmployeeProfile employeeProfile;
}