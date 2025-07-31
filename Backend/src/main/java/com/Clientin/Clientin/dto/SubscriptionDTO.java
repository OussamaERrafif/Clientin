package com.Clientin.Clientin.dto;

import com.Clientin.Clientin.entity.User;
import com.Clientin.Clientin.entity.Subscription.Plan;
import com.Clientin.Clientin.entity.Subscription.Status;
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
public class SubscriptionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String userId;
    private Plan plan;
    private Status status;
    private LocalDate startDate;
    private LocalDate endDate;
    private String paymentMethod;
    private User user;
}