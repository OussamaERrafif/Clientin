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
public class EClientDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String companyName;
    private String contactPerson;
    private String email;
    private String phoneNumber;
    private String address;
    private String industry;
    private CompanySize companySize;
    private SubscriptionType subscriptionType;
    private ClientStatus status;
    private LocalDateTime contractStartDate;
    private LocalDateTime contractEndDate;
    private String assignedAccountManager;
    private String notes;
    private String logoUrl;
    private String websiteUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public enum CompanySize {
        STARTUP, SMALL, MEDIUM, LARGE, ENTERPRISE
    }
    
    public enum SubscriptionType {
        BASIC, PREMIUM, ENTERPRISE, CUSTOM
    }
    
    public enum ClientStatus {
        PROSPECT, ACTIVE, INACTIVE, SUSPENDED, CHURNED
    }
}