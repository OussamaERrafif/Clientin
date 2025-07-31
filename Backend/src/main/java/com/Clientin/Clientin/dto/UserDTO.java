package com.Clientin.Clientin.dto;

import lombok.*;
import com.Clientin.Clientin.entity.User;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String email;
    private User.Role role;
    private String photoUrl;
    private User.Status status;
    private String passwordHash;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}