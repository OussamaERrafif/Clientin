package com.Clientin.Clientin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "e_clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EClient {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "company_name", nullable = false, length = 200)
    private String companyName;
    
    @Column(name = "contact_person", nullable = false, length = 100)
    private String contactPerson;
    
    @Column(nullable = false, unique = true, length = 100)
    private String email;
    
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;
    
    @Column(length = 500)
    private String address;
    
    @Column(length = 100)
    private String industry;
    
    @Column(name = "company_size")
    @Enumerated(EnumType.STRING)
    private CompanySize companySize;
    
    @Column(name = "subscription_type")
    @Enumerated(EnumType.STRING)
    private SubscriptionType subscriptionType;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClientStatus status = ClientStatus.ACTIVE;
    
    @Column(name = "contract_start_date")
    private LocalDateTime contractStartDate;
    
    @Column(name = "contract_end_date")
    private LocalDateTime contractEndDate;
    
    @Column(name = "assigned_account_manager")
    private String assignedAccountManager;
    
    @Column(length = 1000)
    private String notes;
    
    @Column(name = "logo_url")
    private String logoUrl;
    
    @Column(name = "website_url")
    private String websiteUrl;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
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
