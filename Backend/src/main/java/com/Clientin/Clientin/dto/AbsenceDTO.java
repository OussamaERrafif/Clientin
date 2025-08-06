package com.Clientin.Clientin.dto;

import lombok.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Absence request data transfer object")
public class AbsenceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier", example = "123e4567-e89b-12d3-a456-426614174000")
    private String id;
    
    @NotBlank(message = "Employee ID is required")
    @Schema(description = "Employee identifier", example = "emp123", required = true)
    private String employeeId;
    
    @NotNull(message = "Absence type is required")
    @Schema(description = "Type of absence", required = true)
    private AbsenceType absenceType;
    
    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date cannot be in the past")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Start date of absence", example = "2024-12-25", required = true)
    private LocalDate startDate;
    
    @NotNull(message = "End date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "End date of absence", example = "2024-12-31", required = true)
    private LocalDate endDate;
    
    @Positive(message = "Days count must be positive")
    @Schema(description = "Number of absence days", example = "5", required = true)
    private Integer daysCount;
    
    @Size(max = 500, message = "Reason cannot exceed 500 characters")
    @Schema(description = "Reason for absence", example = "Family vacation")
    private String reason;
    @Schema(description = "Absence status", example = "PENDING")
    private AbsenceStatus status;
    
    @Schema(description = "ID of approver", example = "mgr123")
    private String approvedBy;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Approval timestamp", example = "2024-12-20T10:30:00")
    private LocalDateTime approvedAt;
    
    @Schema(description = "Whether absence is paid", example = "true")
    private Boolean isPaid;
    
    @Size(max = 500, message = "Comments cannot exceed 500 characters")
    @Schema(description = "Additional comments", example = "Approved for vacation")
    private String comments;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Creation timestamp", example = "2024-12-15T09:00:00")
    private LocalDateTime createdAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Last update timestamp", example = "2024-12-20T10:30:00")
    private LocalDateTime updatedAt;
    
    @Schema(description = "Created by user", example = "emp123")
    private String createdBy;
    
    @Schema(description = "Last updated by user", example = "mgr123")
    private String updatedBy;
    
    // Custom validation method
    @AssertTrue(message = "End date must be after start date")
    private boolean isEndDateAfterStartDate() {
        if (startDate == null || endDate == null) {
            return true; // Let @NotNull handle null validation
        }
        return !endDate.isBefore(startDate);
    }
    
    public enum AbsenceType {
        VACATION, SICK_LEAVE, PERSONAL_LEAVE, MATERNITY_LEAVE, PATERNITY_LEAVE, EMERGENCY_LEAVE
    }
    
    public enum AbsenceStatus {
        PENDING, APPROVED, REJECTED, CANCELLED
    }
}