# Backend Development Guide

## Table of Contents
- [Project Structure](#project-structure)
- [Technology Stack](#technology-stack)
- [Development Setup](#development-setup)
- [Architecture Overview](#architecture-overview)
- [Database Design](#database-design)
- [API Development](#api-development)
- [Security Implementation](#security-implementation)
- [Testing Strategy](#testing-strategy)
- [Performance Optimization](#performance-optimization)
- [Monitoring and Logging](#monitoring-and-logging)

## Project Structure

```
Backend/
├── src/main/java/com/Clientin/Clientin/
│   ├── ClientinApplication.java        # Main application entry point
│   ├── controller/                     # REST API controllers
│   │   ├── ClientController.java
│   │   ├── EmployeeProfileController.java
│   │   ├── FeedbackController.java
│   │   ├── GoalController.java
│   │   ├── InsightController.java
│   │   ├── NotificationPreferenceController.java
│   │   ├── SubscriptionController.java
│   │   └── UserController.java
│   ├── dto/                           # Data Transfer Objects
│   │   ├── ClientDTO.java
│   │   ├── EmployeeProfileDTO.java
│   │   ├── FeedbackDTO.java
│   │   ├── GoalDTO.java
│   │   ├── InsightDTO.java
│   │   ├── NotificationPreferenceDTO.java
│   │   ├── SubscriptionDTO.java
│   │   └── UserDTO.java
│   ├── entity/                        # JPA entities
│   │   ├── Client.java
│   │   ├── EmployeeProfile.java
│   │   ├── Feedback.java
│   │   ├── Goal.java
│   │   ├── Insight.java
│   │   ├── NotificationPreference.java
│   │   ├── Subscription.java
│   │   └── User.java
│   ├── mapper/                        # Entity-DTO mappers
│   │   ├── ClientMapper.java
│   │   ├── EmployeeProfileMapper.java
│   │   ├── FeedbackMapper.java
│   │   ├── GoalMapper.java
│   │   ├── InsightMapper.java
│   │   ├── NotificationPreferenceMapper.java
│   │   ├── SubscriptionMapper.java
│   │   └── UserMapper.java
│   ├── repository/                    # Data repositories
│   │   ├── ClientRepository.java
│   │   ├── EmployeeProfileRepository.java
│   │   ├── FeedbackRepository.java
│   │   ├── GoalRepository.java
│   │   ├── InsightRepository.java
│   │   ├── NotificationPreferenceRepository.java
│   │   ├── SubscriptionRepository.java
│   │   └── UserRepository.java
│   ├── service/                       # Business logic interfaces
│   │   ├── ClientService.java
│   │   ├── EmployeeProfileService.java
│   │   ├── FeedbackService.java
│   │   ├── GoalService.java
│   │   ├── InsightService.java
│   │   ├── NotificationPreferenceService.java
│   │   ├── SubscriptionService.java
│   │   └── UserService.java
│   └── service/impl/                  # Service implementations
│       ├── ClientServiceImpl.java
│       ├── EmployeeProfileServiceImpl.java
│       ├── FeedbackServiceImpl.java
│       ├── GoalServiceImpl.java
│       ├── InsightServiceImpl.java
│       ├── NotificationPreferenceServiceImpl.java
│       ├── SubscriptionServiceImpl.java
│       └── UserServiceImpl.java
├── src/main/resources/
│   ├── application.properties         # Configuration
│   ├── application-dev.properties     # Development config
│   ├── application-prod.properties    # Production config
│   └── logback-spring.xml            # Logging configuration
├── src/test/java/                     # Test files
├── pom.xml                           # Maven dependencies
└── Dockerfile                        # Docker configuration
```

## Technology Stack

### Core Framework
- **Spring Boot 3.5.4**: Main application framework
- **Java 17**: Programming language
- **Maven**: Build tool and dependency management

### Data Layer
- **Spring Data JPA**: Object-relational mapping
- **Hibernate**: JPA implementation
- **PostgreSQL/MySQL**: Primary database
- **H2**: In-memory database for testing

### Security
- **Spring Security**: Authentication and authorization
- **Spring Session JDBC**: Session management
- **BCrypt**: Password hashing

### API & Validation
- **Spring Web**: REST API development
- **Jakarta Validation**: Input validation
- **Jackson**: JSON serialization/deserialization

### Monitoring & Observability
- **Spring Boot Actuator**: Health checks and metrics
- **SLF4J + Logback**: Logging framework
- **Micrometer**: Metrics collection

### Testing
- **JUnit 5**: Unit testing framework
- **Mockito**: Mocking framework
- **TestContainers**: Integration testing
- **Spring Boot Test**: Spring-specific testing utilities

## Development Setup

### Prerequisites
```bash
# Check Java version
java -version  # Should be 17 or higher

# Check Maven version
mvn -version   # Should be 3.6 or higher
```

### Environment Setup

1. **Clone and navigate to backend**:
```bash
cd Backend
```

2. **Database setup** (PostgreSQL example):
```sql
-- Connect to PostgreSQL as superuser
psql -U postgres

-- Create database and user
CREATE DATABASE clientin_dev;
CREATE USER clientin_user WITH PASSWORD 'dev_password';
GRANT ALL PRIVILEGES ON DATABASE clientin_dev TO clientin_user;
```

3. **Configure application properties**:
```properties
# src/main/resources/application-dev.properties
spring.profiles.active=dev

# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/clientin_dev
spring.datasource.username=clientin_user
spring.datasource.password=dev_password
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Logging
logging.level.com.Clientin.Clientin=DEBUG
logging.level.org.springframework.web=DEBUG
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

4. **Run the application**:
```bash
# Development mode
./mvnw spring-boot:run -Dspring.profiles.active=dev

# Or with debug
./mvnw spring-boot:run -Dspring.profiles.active=dev -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005
```

## Architecture Overview

### Layered Architecture

```
┌─────────────────────────────────────┐
│           Controller Layer          │  ← REST endpoints, request handling
├─────────────────────────────────────┤
│            Service Layer            │  ← Business logic, transactions
├─────────────────────────────────────┤
│          Repository Layer           │  ← Data access, queries
├─────────────────────────────────────┤
│            Entity Layer             │  ← Database entities, mappings
└─────────────────────────────────────┘
```

### Component Relationships

```java
@RestController
public class UserController {
    private final UserService userService;  // Interface dependency
}

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
}

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // Custom queries
}
```

### Design Patterns Used

1. **Repository Pattern**: Data access abstraction
2. **Service Layer Pattern**: Business logic encapsulation
3. **DTO Pattern**: Data transfer and API contracts
4. **Mapper Pattern**: Entity-DTO conversion
5. **Dependency Injection**: Loose coupling
6. **Builder Pattern**: Complex object creation

## Database Design

### Entity Relationships

```java
@Entity
@Table(name = "users")
public class User {
    @Id
    private String id;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Enumerated(EnumType.STRING)
    private Role role;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Subscription> subscriptions;
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private EmployeeProfile employeeProfile;
    
    // Getters, setters, constructors
}

@Entity
@Table(name = "employee_profiles")
public class EmployeeProfile {
    @Id
    private String id;
    
    @Column(nullable = false)
    private String name;
    
    private String position;
    private String department;
    
    @Column(name = "hire_date")
    private LocalDate hireDate;
    
    @Column(name = "performance_score")
    private BigDecimal performanceScore;
    
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    // Getters, setters, constructors
}

@Entity
@Table(name = "clients")
public class Client {
    @Id
    private String id;
    
    private String name;
    private String email;
    
    @Column(name = "is_anonymous")
    private Boolean isAnonymous = false;
    
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Feedback> feedbacks;
    
    // Getters, setters, constructors
}

@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    private String id;
    
    @Column(nullable = false)
    private Integer rating;
    
    @Column(columnDefinition = "TEXT")
    private String comment;
    
    @Enumerated(EnumType.STRING)
    private FeedbackType type;
    
    @Enumerated(EnumType.STRING)
    private FeedbackStatus status;
    
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private EmployeeProfile employee;
    
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    // Getters, setters, constructors
}
```

### Database Schema

```sql
-- Users table
CREATE TABLE users (
    id VARCHAR(255) PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Employee profiles table
CREATE TABLE employee_profiles (
    id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) UNIQUE REFERENCES users(id),
    name VARCHAR(255) NOT NULL,
    position VARCHAR(255),
    department VARCHAR(255),
    hire_date DATE,
    performance_score DECIMAL(3,2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Clients table
CREATE TABLE clients (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    is_anonymous BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Feedback table
CREATE TABLE feedback (
    id VARCHAR(255) PRIMARY KEY,
    client_id VARCHAR(255) REFERENCES clients(id),
    employee_id VARCHAR(255) REFERENCES employee_profiles(id),
    rating INTEGER NOT NULL CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    type VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Subscriptions table
CREATE TABLE subscriptions (
    id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) REFERENCES users(id),
    plan VARCHAR(100) NOT NULL,
    status VARCHAR(50) NOT NULL,
    start_date DATE,
    end_date DATE,
    payment_method VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Goals table
CREATE TABLE goals (
    id VARCHAR(255) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    target_value DECIMAL(10,2),
    current_value DECIMAL(10,2) DEFAULT 0,
    deadline DATE,
    status VARCHAR(50) NOT NULL,
    created_by VARCHAR(255) REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insights table
CREATE TABLE insights (
    id VARCHAR(255) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    type VARCHAR(50) NOT NULL,
    data JSONB,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Notification preferences table
CREATE TABLE notification_preferences (
    id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) UNIQUE REFERENCES users(id),
    email_enabled BOOLEAN DEFAULT TRUE,
    sms_enabled BOOLEAN DEFAULT FALSE,
    push_enabled BOOLEAN DEFAULT TRUE,
    feedback_alerts BOOLEAN DEFAULT TRUE,
    report_alerts BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Indexes for performance
CREATE INDEX idx_feedback_client_id ON feedback(client_id);
CREATE INDEX idx_feedback_employee_id ON feedback(employee_id);
CREATE INDEX idx_feedback_created_at ON feedback(created_at);
CREATE INDEX idx_employee_profiles_user_id ON employee_profiles(user_id);
CREATE INDEX idx_subscriptions_user_id ON subscriptions(user_id);
```

## API Development

### Controller Implementation

```java
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = {"http://localhost:3000"})
public class UserController {
    
    private final UserService userService;
    
    @GetMapping
    public ResponseEntity<Page<UserDTO>> getAll(
            @PageableDefault(size = 20, sort = "email") Pageable pageable) {
        log.info("Fetching users with pageable: {}", pageable);
        Page<UserDTO> users = userService.findAll(pageable);
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable String id) {
        log.info("Fetching user with ID: {}", id);
        return userService.findById(id)
                .map(user -> ResponseEntity.ok(user))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO userDTO) {
        log.info("Creating new user with email: {}", userDTO.getEmail());
        UserDTO created = userService.create(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(
            @PathVariable String id,
            @Valid @RequestBody UserDTO userDTO) {
        log.info("Updating user with ID: {}", id);
        try {
            UserDTO updated = userService.update(id, userDTO);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> partialUpdate(
            @PathVariable String id,
            @RequestBody UserDTO userDTO) {
        log.info("Partial update for user ID: {}", id);
        try {
            UserDTO updated = userService.partialUpdate(id, userDTO);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.info("Deleting user with ID: {}", id);
        try {
            userService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<UserDTO>> search(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String role,
            @PageableDefault(size = 20) Pageable pageable) {
        
        Specification<User> spec = Specification.where(null);
        
        if (email != null && !email.isEmpty()) {
            spec = spec.and((root, query, cb) -> 
                cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%"));
        }
        
        if (role != null && !role.isEmpty()) {
            spec = spec.and((root, query, cb) -> 
                cb.equal(root.get("role"), Role.valueOf(role.toUpperCase())));
        }
        
        Page<UserDTO> users = userService.search(spec, pageable);
        return ResponseEntity.ok(users);
    }
    
    @PostMapping("/bulk")
    public ResponseEntity<List<UserDTO>> bulkCreate(@Valid @RequestBody List<UserDTO> users) {
        log.info("Bulk creating {} users", users.size());
        List<UserDTO> created = userService.bulkCreate(users);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @DeleteMapping("/bulk")
    public ResponseEntity<Void> bulkDelete(@RequestBody List<String> ids) {
        log.info("Bulk deleting {} users", ids.size());
        userService.bulkDelete(ids);
        return ResponseEntity.noContent().build();
    }
}
```

### Service Implementation

```java
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public UserDTO create(UserDTO dto) {
        log.debug("Creating new user with email: {}", dto.getEmail());
        
        // Validate email uniqueness
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("User with email " + dto.getEmail() + " already exists");
        }
        
        try {
            User entity = userMapper.toEntity(dto);
            
            // Hash password if provided
            if (entity.getPassword() != null) {
                entity.setPassword(passwordEncoder.encode(entity.getPassword()));
            }
            
            // Generate ID if not provided
            if (entity.getId() == null) {
                entity.setId(UUID.randomUUID().toString());
            }
            
            User saved = userRepository.save(entity);
            return userMapper.toDTO(saved);
        } catch (Exception e) {
            log.error("Error creating user: {}", e.getMessage(), e);
            throw new RuntimeException("Error creating user", e);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO> findById(String id) {
        log.debug("Fetching user with ID: {}", id);
        return userRepository.findById(id)
                .map(userMapper::toDTO);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Pageable pageable) {
        log.debug("Fetching paged users with pageable: {}", pageable);
        return userRepository.findAll(pageable)
                .map(userMapper::toDTO);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        log.debug("Fetching all users");
        return userMapper.toDTOList(userRepository.findAll());
    }
    
    @Override
    public UserDTO update(String id, UserDTO dto) {
        log.debug("Updating user with ID: {}", id);
        return userRepository.findById(id)
                .map(existingEntity -> {
                    // Preserve original password if not provided in update
                    String originalPassword = existingEntity.getPassword();
                    
                    userMapper.partialUpdate(dto, existingEntity);
                    
                    // Hash new password if provided
                    if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
                        existingEntity.setPassword(passwordEncoder.encode(dto.getPassword()));
                    } else {
                        existingEntity.setPassword(originalPassword);
                    }
                    
                    return userMapper.toDTO(userRepository.save(existingEntity));
                })
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }
    
    @Override
    public UserDTO partialUpdate(String id, UserDTO dto) {
        log.debug("Partial update for user ID: {}", id);
        return userRepository.findById(id)
                .map(entity -> {
                    userMapper.partialUpdate(dto, entity);
                    
                    // Hash password if provided
                    if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
                        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
                    }
                    
                    return userMapper.toDTO(userRepository.save(entity));
                })
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }
    
    @Override
    public void delete(String id) {
        log.debug("Deleting user with ID: {}", id);
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<UserDTO> search(Specification<User> spec, Pageable pageable) {
        log.debug("Searching users with specification");
        return userRepository.findAll(spec, pageable)
                .map(userMapper::toDTO);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean exists(String id) {
        return userRepository.existsById(id);
    }
    
    @Override
    public List<UserDTO> bulkCreate(List<UserDTO> dtos) {
        log.debug("Bulk creating {} users", dtos.size());
        
        // Validate all emails are unique
        Set<String> emails = dtos.stream()
                .map(UserDTO::getEmail)
                .collect(Collectors.toSet());
        
        List<String> existingEmails = userRepository.findExistingEmails(emails);
        if (!existingEmails.isEmpty()) {
            throw new DuplicateResourceException("Users with emails already exist: " + existingEmails);
        }
        
        List<User> entities = userMapper.toEntityList(dtos);
        
        // Process each entity
        entities.forEach(entity -> {
            if (entity.getId() == null) {
                entity.setId(UUID.randomUUID().toString());
            }
            if (entity.getPassword() != null) {
                entity.setPassword(passwordEncoder.encode(entity.getPassword()));
            }
        });
        
        List<User> saved = userRepository.saveAll(entities);
        return userMapper.toDTOList(saved);
    }
    
    @Override
    public void bulkDelete(List<String> ids) {
        log.debug("Bulk deleting {} users", ids.size());
        userRepository.deleteAllById(ids);
    }
}
```

### Repository Implementation

```java
@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    
    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    List<User> findByRole(Role role);
    
    @Query("SELECT u.email FROM User u WHERE u.email IN :emails")
    List<String> findExistingEmails(@Param("emails") Set<String> emails);
    
    @Query("SELECT u FROM User u WHERE u.createdAt >= :fromDate")
    List<User> findUsersCreatedAfter(@Param("fromDate") LocalDateTime fromDate);
    
    @Modifying
    @Query("UPDATE User u SET u.lastLoginAt = :loginTime WHERE u.id = :userId")
    void updateLastLoginTime(@Param("userId") String userId, @Param("loginTime") LocalDateTime loginTime);
    
    // Custom query methods for complex searches
    @Query("""
        SELECT u FROM User u 
        WHERE (:email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%')))
        AND (:role IS NULL OR u.role = :role)
        AND (:fromDate IS NULL OR u.createdAt >= :fromDate)
        """)
    Page<User> findUsersWithFilters(
            @Param("email") String email,
            @Param("role") Role role,
            @Param("fromDate") LocalDateTime fromDate,
            Pageable pageable);
}
```

### DTO and Mapper Implementation

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    
    private String id;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    @Size(min = 8, message = "Password should have at least 8 characters")
    private String password;
    
    @NotNull(message = "Role is required")
    private Role role;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLoginAt;
    
    // Profile information
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Boolean isActive;
    
    // Relationships
    private List<SubscriptionDTO> subscriptions;
    private EmployeeProfileDTO employeeProfile;
}

@Component
@RequiredArgsConstructor
public class UserMapper {
    
    public User toEntity(UserDTO dto) {
        if (dto == null) return null;
        
        return User.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(dto.getRole())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .phoneNumber(dto.getPhoneNumber())
                .isActive(dto.getIsActive() != null ? dto.getIsActive() : true)
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .lastLoginAt(dto.getLastLoginAt())
                .build();
    }
    
    public UserDTO toDTO(User entity) {
        if (entity == null) return null;
        
        return UserDTO.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                // Never expose password in DTO
                .role(entity.getRole())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .phoneNumber(entity.getPhoneNumber())
                .isActive(entity.getIsActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .lastLoginAt(entity.getLastLoginAt())
                .build();
    }
    
    public List<UserDTO> toDTOList(List<User> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<User> toEntityList(List<UserDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
    
    public void partialUpdate(UserDTO dto, User entity) {
        if (dto == null || entity == null) return;
        
        if (dto.getEmail() != null) {
            entity.setEmail(dto.getEmail());
        }
        if (dto.getRole() != null) {
            entity.setRole(dto.getRole());
        }
        if (dto.getFirstName() != null) {
            entity.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            entity.setLastName(dto.getLastName());
        }
        if (dto.getPhoneNumber() != null) {
            entity.setPhoneNumber(dto.getPhoneNumber());
        }
        if (dto.getIsActive() != null) {
            entity.setIsActive(dto.getIsActive());
        }
        
        entity.setUpdatedAt(LocalDateTime.now());
    }
}
```

## Security Implementation

### Spring Security Configuration

```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final UserDetailsService userDetailsService;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
    
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringRequestMatchers("/api/v1/auth/**"))
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false))
            .authorizeHttpRequests(authz -> authz
                // Public endpoints
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/actuator/health").permitAll()
                .requestMatchers("/api/v1/public/**").permitAll()
                
                // Admin only endpoints
                .requestMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                
                // Manager and above
                .requestMatchers("/api/v1/insights/**").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers("/api/v1/reports/**").hasAnyRole("ADMIN", "MANAGER")
                
                // Authenticated users
                .requestMatchers("/api/v1/**").authenticated()
                
                .anyRequest().authenticated())
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler))
            .logout(logout -> logout
                .logoutUrl("/api/v1/auth/logout")
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.setStatus(HttpServletResponse.SC_OK);
                }))
            .cors(cors -> cors.configurationSource(corsConfigurationSource()));
        
        return http.build();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:3000", "https://*.yourdomain.com"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }
}
```

### Authentication Service

```java
@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    
    public UserDTO authenticate(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
                )
            );
            
            User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));
            
            // Update last login time
            user.setLastLoginAt(LocalDateTime.now());
            userRepository.save(user);
            
            return userMapper.toDTO(user);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }
    
    public UserDTO register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("User with email already exists");
        }
        
        User user = User.builder()
            .id(UUID.randomUUID().toString())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.USER)
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .isActive(true)
            .createdAt(LocalDateTime.now())
            .build();
        
        User saved = userRepository.save(user);
        return userMapper.toDTO(saved);
    }
    
    public void changePassword(String userId, ChangePasswordRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
        
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new BadCredentialsException("Current password is incorrect");
        }
        
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }
}
```

### Method-Level Security

```java
@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    
    @PostMapping("/users")
    @PreAuthorize("hasAuthority('USER_CREATE')")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        // Implementation
    }
    
    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAuthority('USER_DELETE') and @userService.exists(#id)")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        // Implementation
    }
}

@Service
public class EmployeeService {
    
    @PreAuthorize("hasRole('MANAGER') or @employeeService.isOwner(authentication.name, #employeeId)")
    public EmployeeProfileDTO updateEmployee(String employeeId, EmployeeProfileDTO dto) {
        // Implementation
    }
    
    @PostFilter("hasRole('ADMIN') or filterObject.department == authentication.principal.department")
    public List<EmployeeProfileDTO> getAllEmployees() {
        // Implementation
    }
}
```

## Testing Strategy

### Unit Testing

```java
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private UserMapper userMapper;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @InjectMocks
    private UserServiceImpl userService;
    
    @Test
    void createUser_ValidData_ReturnsUserDTO() {
        // Given
        UserDTO userDTO = UserDTO.builder()
            .email("test@example.com")
            .password("password123")
            .role(Role.USER)
            .build();
        
        User user = User.builder()
            .id("123")
            .email("test@example.com")
            .password("hashedPassword")
            .role(Role.USER)
            .build();
        
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(false);
        when(userMapper.toEntity(userDTO)).thenReturn(user);
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userDTO);
        
        // When
        UserDTO result = userService.create(userDTO);
        
        // Then
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("test@example.com");
        verify(userRepository).save(any(User.class));
    }
    
    @Test
    void createUser_DuplicateEmail_ThrowsException() {
        // Given
        UserDTO userDTO = UserDTO.builder()
            .email("test@example.com")
            .build();
        
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(true);
        
        // When & Then
        assertThatThrownBy(() -> userService.create(userDTO))
            .isInstanceOf(DuplicateResourceException.class)
            .hasMessageContaining("already exists");
    }
    
    @Test
    void findById_ExistingUser_ReturnsUserDTO() {
        // Given
        String userId = "123";
        User user = User.builder()
            .id(userId)
            .email("test@example.com")
            .build();
        UserDTO userDTO = UserDTO.builder()
            .id(userId)
            .email("test@example.com")
            .build();
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(userDTO);
        
        // When
        Optional<UserDTO> result = userService.findById(userId);
        
        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(userId);
    }
    
    @Test
    void delete_ExistingUser_DeletesUser() {
        // Given
        String userId = "123";
        when(userRepository.existsById(userId)).thenReturn(true);
        
        // When
        userService.delete(userId);
        
        // Then
        verify(userRepository).deleteById(userId);
    }
    
    @Test
    void delete_NonExistingUser_ThrowsException() {
        // Given
        String userId = "123";
        when(userRepository.existsById(userId)).thenReturn(false);
        
        // When & Then
        assertThatThrownBy(() -> userService.delete(userId))
            .isInstanceOf(EntityNotFoundException.class);
    }
}
```

### Integration Testing

```java
@SpringBootTest
@Testcontainers
@Transactional
@ActiveProfiles("test")
class UserServiceIntegrationTest {
    
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("clientin_test")
            .withUsername("test")
            .withPassword("test");
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
    
    @Test
    void createAndFindUser_Integration_Success() {
        // Given
        UserDTO userDTO = UserDTO.builder()
            .email("integration@test.com")
            .password("password123")
            .role(Role.USER)
            .firstName("John")
            .lastName("Doe")
            .build();
        
        // When
        UserDTO created = userService.create(userDTO);
        Optional<UserDTO> found = userService.findById(created.getId());
        
        // Then
        assertThat(created).isNotNull();
        assertThat(created.getId()).isNotNull();
        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("integration@test.com");
        assertThat(found.get().getPassword()).isNull(); // Password should not be returned
    }
    
    @Test
    void bulkOperations_Integration_Success() {
        // Given
        List<UserDTO> users = Arrays.asList(
            UserDTO.builder().email("bulk1@test.com").password("pass123").role(Role.USER).build(),
            UserDTO.builder().email("bulk2@test.com").password("pass123").role(Role.USER).build(),
            UserDTO.builder().email("bulk3@test.com").password("pass123").role(Role.USER).build()
        );
        
        // When
        List<UserDTO> created = userService.bulkCreate(users);
        List<String> ids = created.stream().map(UserDTO::getId).collect(Collectors.toList());
        
        // Then
        assertThat(created).hasSize(3);
        assertThat(userRepository.findAllById(ids)).hasSize(3);
        
        // Cleanup
        userService.bulkDelete(ids);
        assertThat(userRepository.findAllById(ids)).isEmpty();
    }
}
```

### Controller Testing

```java
@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserService userService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void createUser_ValidData_ReturnsCreated() throws Exception {
        // Given
        UserDTO userDTO = UserDTO.builder()
            .email("test@example.com")
            .role(Role.USER)
            .build();
        
        UserDTO created = UserDTO.builder()
            .id("123")
            .email("test@example.com")
            .role(Role.USER)
            .build();
        
        when(userService.create(any(UserDTO.class))).thenReturn(created);
        
        // When & Then
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value("123"))
            .andExpect(jsonPath("$.email").value("test@example.com"));
    }
    
    @Test
    @WithMockUser(roles = "USER")
    void deleteUser_InsufficientPermissions_ReturnsForbidden() throws Exception {
        mockMvc.perform(delete("/api/v1/users/123"))
            .andExpect(status().isForbidden());
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void getUser_ExistingUser_ReturnsUser() throws Exception {
        // Given
        UserDTO userDTO = UserDTO.builder()
            .id("123")
            .email("test@example.com")
            .role(Role.USER)
            .build();
        
        when(userService.findById("123")).thenReturn(Optional.of(userDTO));
        
        // When & Then
        mockMvc.perform(get("/api/v1/users/123"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value("123"))
            .andExpected(jsonPath("$.email").value("test@example.com"));
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void getUser_NonExistingUser_ReturnsNotFound() throws Exception {
        when(userService.findById("999")).thenReturn(Optional.empty());
        
        mockMvc.perform(get("/api/v1/users/999"))
            .andExpect(status().isNotFound());
    }
}
```

## Performance Optimization

### Database Optimization

```java
// Entity optimization with proper indexing
@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_users_email", columnList = "email"),
    @Index(name = "idx_users_role", columnList = "role"),
    @Index(name = "idx_users_created_at", columnList = "created_at")
})
public class User {
    // Entity fields
}

// Query optimization
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    
    // Use @Query for complex queries instead of method names
    @Query("SELECT u FROM User u WHERE u.role = :role AND u.isActive = true")
    List<User> findActiveUsersByRole(@Param("role") Role role);
    
    // Batch operations
    @Modifying
    @Query("UPDATE User u SET u.isActive = false WHERE u.id IN :ids")
    void deactivateUsers(@Param("ids") List<String> ids);
    
    // Pagination with count query optimization
    @Query(value = "SELECT u FROM User u WHERE u.department = :dept",
           countQuery = "SELECT count(u) FROM User u WHERE u.department = :dept")
    Page<User> findByDepartment(@Param("dept") String department, Pageable pageable);
}
```

### Caching Implementation

```java
@Configuration
@EnableCaching
public class CacheConfig {
    
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .recordStats());
        return cacheManager;
    }
}

@Service
@Transactional
public class UserServiceImpl implements UserService {
    
    @Override
    @Cacheable(value = "users", key = "#id")
    public Optional<UserDTO> findById(String id) {
        // Implementation
    }
    
    @Override
    @Cacheable(value = "usersByRole", key = "#role")
    public List<UserDTO> findByRole(Role role) {
        // Implementation
    }
    
    @Override
    @CacheEvict(value = {"users", "usersByRole"}, key = "#id")
    public UserDTO update(String id, UserDTO dto) {
        // Implementation
    }
    
    @Override
    @CacheEvict(value = {"users", "usersByRole"}, allEntries = true)
    public void bulkCreate(List<UserDTO> users) {
        // Implementation
    }
}
```

### Async Processing

```java
@Configuration
@EnableAsync
public class AsyncConfig {
    
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(8);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("async-");
        executor.initialize();
        return executor;
    }
}

@Service
public class NotificationService {
    
    @Async
    public CompletableFuture<Void> sendWelcomeEmail(UserDTO user) {
        // Send email implementation
        log.info("Sending welcome email to: {}", user.getEmail());
        return CompletableFuture.completedFuture(null);
    }
    
    @Async
    public CompletableFuture<Void> generateUserReport(String userId) {
        // Generate report implementation
        return CompletableFuture.completedFuture(null);
    }
}
```

## Monitoring and Logging

### Actuator Configuration

```properties
# application.properties
management.endpoints.web.exposure.include=health,info,metrics,prometheus,loggers
management.endpoint.health.show-details=always
management.endpoint.health.probes.enabled=true
management.health.db.enabled=true
management.health.redis.enabled=true

# Custom health indicators
management.health.custom.enabled=true

# Metrics
management.metrics.export.prometheus.enabled=true
management.metrics.distribution.percentiles-histogram.http.server.requests=true
```

### Custom Health Indicators

```java
@Component
public class DatabaseHealthIndicator implements HealthIndicator {
    
    private final UserRepository userRepository;
    
    @Override
    public Health health() {
        try {
            long userCount = userRepository.count();
            return Health.up()
                .withDetail("userCount", userCount)
                .withDetail("status", "Database connection is healthy")
                .build();
        } catch (Exception e) {
            return Health.down()
                .withDetail("error", e.getMessage())
                .build();
        }
    }
}

@Component
public class ExternalServiceHealthIndicator implements HealthIndicator {
    
    @Override
    public Health health() {
        // Check external service connectivity
        return Health.up()
            .withDetail("externalService", "Available")
            .build();
    }
}
```

### Logging Configuration

```xml
<!-- logback-spring.xml -->
<configuration>
    <springProfile name="!production">
        <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
            <encoder>
                <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
            </encoder>
        </appender>
        <root level="INFO">
            <appender-ref ref="CONSOLE" />
        </root>
    </springProfile>

    <springProfile name="production">
        <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
            <file>/var/log/clientin/application.log</file>
            <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
                <fileNamePattern>/var/log/clientin/application.%d{yyyy-MM-dd}.log</fileNamePattern>
                <maxHistory>30</maxHistory>
                <totalSizeCap>10GB</totalSizeCap>
            </rollingPolicy>
            <encoder class="net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder">
                <providers>
                    <timestamp/>
                    <logLevel/>
                    <loggerName/>
                    <message/>
                    <mdc/>
                    <arguments/>
                    <stackTrace/>
                </providers>
            </encoder>
        </appender>
        
        <appender name="ERROR_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
            <filter class="ch.qos.logback.classic.filter.LevelFilter">
                <level>ERROR</level>
                <onMatch>ACCEPT</onMatch>
                <onMismatch>DENY</onMismatch>
            </filter>
            <file>/var/log/clientin/error.log</file>
            <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
                <fileNamePattern>/var/log/clientin/error.%d{yyyy-MM-dd}.log</fileNamePattern>
                <maxHistory>90</maxHistory>
            </rollingPolicy>
            <encoder class="net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder">
                <providers>
                    <timestamp/>
                    <logLevel/>
                    <loggerName/>
                    <message/>
                    <mdc/>
                    <arguments/>
                    <stackTrace/>
                </providers>
            </encoder>
        </appender>
        
        <root level="INFO">
            <appender-ref ref="FILE" />
            <appender-ref ref="ERROR_FILE" />
        </root>
    </springProfile>

    <!-- Specific logger configurations -->
    <logger name="com.Clientin.Clientin" level="DEBUG" additivity="false">
        <appender-ref ref="CONSOLE" />
        <appender-ref ref="FILE" />
    </logger>
    
    <logger name="org.springframework.security" level="DEBUG" additivity="false">
        <appender-ref ref="CONSOLE" />
        <appender-ref ref="FILE" />
    </logger>
    
    <logger name="org.hibernate.SQL" level="DEBUG" additivity="false">
        <appender-ref ref="CONSOLE" />
    </logger>
</configuration>
```

### Request Logging Interceptor

```java
@Component
@Slf4j
public class RequestLoggingInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestId = UUID.randomUUID().toString().substring(0, 8);
        MDC.put("requestId", requestId);
        
        log.info("Request started: {} {} from {}",
            request.getMethod(),
            request.getRequestURI(),
            request.getRemoteAddr());
        
        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        long startTime = (Long) request.getAttribute("startTime");
        long duration = System.currentTimeMillis() - startTime;
        
        log.info("Request completed: {} {} - Status: {} - Duration: {}ms",
            request.getMethod(),
            request.getRequestURI(),
            response.getStatus(),
            duration);
        
        if (ex != null) {
            log.error("Request failed with exception", ex);
        }
        
        MDC.clear();
    }
}
```

This completes the comprehensive backend development guide for the Clientin application. The guide covers all aspects of Spring Boot development including project structure, architecture, database design, API development, security, testing, performance optimization, and monitoring.
