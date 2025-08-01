package com.Clientin.Clientin.dto;

import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;
import com.Clientin.Clientin.entity.Training;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String description;
    private Training.TrainingType trainingType;
    private Integer durationHours;
    private String contentUrl;
    private String prerequisites;
    private String learningObjectives;
    private Training.TrainingStatus status;
    private Integer maxParticipants;
    private Integer passingScore;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}