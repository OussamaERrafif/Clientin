package com.Clientin.Clientin.dto;

import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;
import com.Clientin.Clientin.entity.EmailTemplate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailTemplateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String templateName;
    private EmailTemplate.TemplateType templateType;
    private String subject;
    private String htmlContent;
    private String textContent;
    private String variables;
    private Boolean active;
    private Integer version;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}