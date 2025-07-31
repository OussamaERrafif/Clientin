# Exception Handling System Documentation

## Overview

This project includes a comprehensive exception handling system with custom exceptions, global error handling, and automatic logging integration. The system provides consistent error responses and detailed logging for all application errors.

## Exception Hierarchy

All custom exceptions extend the `BaseException` class, which provides:
- Consistent error codes
- HTTP status codes
- Timestamps
- Entity identification
- Additional details
- Integration with logging system

## Available Custom Exceptions

### 1. ResourceNotFoundException
**Usage**: When a requested resource is not found
```java
// By ID
throw new ResourceNotFoundException("User", userId);

// By field and value
throw new ResourceNotFoundException("User", "email", "user@example.com");

// With additional details
throw new ResourceNotFoundException("User", userId, "User was soft deleted");
```

### 2. ResourceAlreadyExistsException
**Usage**: When attempting to create a resource that already exists
```java
// Basic usage
throw new ResourceAlreadyExistsException("User", "email", "user@example.com");

// With additional details
throw new ResourceAlreadyExistsException("User", "email", "user@example.com", 
    "User registration attempted with existing email");
```

### 3. ValidationException
**Usage**: When validation fails
```java
// General validation error
throw new ValidationException("Invalid input data");

// Field-specific validation error
throw new ValidationException("User", "email", "Email format is invalid");

// With additional details
throw new ValidationException("User", "password", "Password too weak", 
    "Password must contain at least 8 characters with numbers and symbols");
```

### 4. UnauthorizedException
**Usage**: When user is not authenticated
```java
// General unauthorized access
throw new UnauthorizedException("Authentication required");

// Specific action unauthorized
throw new UnauthorizedException(userId, "UPDATE", "User profile");
```

### 5. ForbiddenException
**Usage**: When user doesn't have sufficient permissions
```java
// General forbidden access
throw new ForbiddenException("Insufficient permissions");

// Role-based forbidden access
throw new ForbiddenException(userId, userRole, requiredRole);
```

### 6. BusinessRuleException
**Usage**: When business rules are violated
```java
// Business rule violation
throw new BusinessRuleException("User", "account_deletion", 
    "Cannot delete user with active subscriptions");

// With additional details
throw new BusinessRuleException("Order", "payment_processing", 
    "Payment amount exceeds daily limit", 
    "User attempted to process payment of $5000, daily limit is $2000");
```

### 7. ServiceException
**Usage**: When service operations fail
```java
// Service operation failure
throw new ServiceException("User", "create", "Failed to create user account");

// With cause
throw new ServiceException("User", "update", "Database connection failed", originalException);
```

### 8. ExternalServiceException
**Usage**: When external service calls fail
```java
// Basic external service error
throw new ExternalServiceException("PaymentGateway", "processPayment", "Service unavailable");

// With HTTP status code
throw new ExternalServiceException("EmailService", "sendEmail", "Failed to send email", 500);

// With full details
throw new ExternalServiceException("SMSService", "sendSMS", "Rate limit exceeded", 
    "Service returned 429 status", 429, originalException);
```

### 9. DatabaseException
**Usage**: When database operations fail
```java
// Database operation failure
throw new DatabaseException("User", "save", "Constraint violation");

// With cause
throw new DatabaseException("Order", "delete", "Foreign key constraint", originalException);
```

### 10. FileOperationException
**Usage**: When file operations fail
```java
// File operation failure
throw new FileOperationException("user-avatar.jpg", "upload", "File size exceeds limit");

// With cause
throw new FileOperationException("report.pdf", "download", "File not accessible", originalException);
```

## Global Exception Handler

The `GlobalExceptionHandler` class automatically handles all exceptions and provides:

- **Consistent error responses** with standard `ErrorResponse` format
- **Automatic logging** of all exceptions
- **Validation error details** for request validation failures
- **HTTP status code mapping** based on exception type

### Error Response Format

All API errors return a consistent format:

```json
{
    "timestamp": "2024-01-15T10:30:00",
    "status": 404,
    "error": "Not Found",
    "message": "User with ID 'user123' not found",
    "path": "/api/v1/users/user123",
    "errorCode": "RESOURCE_NOT_FOUND",
    "entity": "User",
    "details": "Additional error details if available",
    "validationErrors": [
        {
            "field": "email",
            "rejectedValue": "invalid-email",
            "message": "Email format is invalid"
        }
    ]
}
```

## Usage Examples in Controllers

### Updated Controller Methods

```java
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final LogService logService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable String id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO dto) {
        // Check if user already exists
        if (userService.existsByEmail(dto.getEmail())) {
            throw new ResourceAlreadyExistsException("User", "email", dto.getEmail());
        }
        
        try {
            UserDTO created = userService.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            throw new ServiceException("User", "create", "Failed to create user", e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable String id, @Valid @RequestBody UserDTO dto) {
        try {
            UserDTO updated = userService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("User", id);
        } catch (Exception e) {
            throw new ServiceException("User", "update", "Failed to update user", e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        // Business rule validation
        if (userService.hasActiveSubscriptions(id)) {
            throw new BusinessRuleException("User", "account_deletion", 
                "Cannot delete user with active subscriptions");
        }
        
        try {
            userService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("User", id);
        } catch (Exception e) {
            throw new ServiceException("User", "delete", "Failed to delete user", e);
        }
    }
}
```

### Service Layer Examples

```java
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final LogService logService;

    @Override
    @Transactional
    public UserDTO create(UserDTO dto) {
        try {
            // Validation
            if (userRepository.existsByEmail(dto.getEmail())) {
                throw new ResourceAlreadyExistsException("User", "email", dto.getEmail());
            }

            // Business rule validation
            if (dto.getAge() < 18) {
                throw new BusinessRuleException("User", "age_requirement", 
                    "User must be at least 18 years old");
            }

            User entity = userMapper.toEntity(dto);
            User savedEntity = userRepository.save(entity);
            
            logService.logCreate("User", savedEntity.getId(), "system", 
                "User created successfully");
            
            return userMapper.toDTO(savedEntity);
        } catch (BaseException e) {
            // Re-throw custom exceptions
            throw e;
        } catch (Exception e) {
            // Wrap unexpected exceptions
            throw new ServiceException("User", "create", "Unexpected error during user creation", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO> findById(String id) {
        try {
            return userRepository.findById(id)
                    .map(userMapper::toDTO);
        } catch (Exception e) {
            throw new DatabaseException("User", "findById", "Failed to retrieve user", e);
        }
    }
}
```

## Best Practices

### 1. Use Specific Exceptions
Choose the most appropriate exception type for each scenario:
```java
// Good
throw new ResourceNotFoundException("User", userId);
throw new BusinessRuleException("Order", "payment_processing", "Payment failed");

// Avoid
throw new RuntimeException("Something went wrong");
```

### 2. Provide Meaningful Messages
Include context and actionable information:
```java
// Good
throw new ValidationException("User", "email", 
    "Email format is invalid. Expected format: user@domain.com");

// Avoid
throw new ValidationException("Invalid email");
```

### 3. Include Relevant Details
Add additional context when helpful:
```java
throw new ExternalServiceException("PaymentGateway", "processPayment", 
    "Payment processing failed", 
    "Gateway returned error: insufficient funds", 
    402, 
    originalException);
```

### 4. Handle Exceptions at Appropriate Levels
- **Controller**: Convert service exceptions to appropriate HTTP responses
- **Service**: Handle business logic errors and validation
- **Repository**: Handle data access errors

### 5. Log Important Context
The system automatically logs exceptions, but you can add additional context:
```java
try {
    // operation
} catch (Exception e) {
    logService.logError("User", userId, currentUserId, 
        "Failed to update user profile", 
        "Additional context: " + additionalInfo);
    throw new ServiceException("User", "update", "Profile update failed", e);
}
```

## Integration with Logging System

All exceptions are automatically logged with:
- Exception type and message
- Entity and operation context
- Stack trace (for debugging)
- Request path and user information
- Timestamp and correlation IDs

The global exception handler integrates with the logging system to ensure all errors are properly tracked and can be analyzed for system health monitoring.

## Testing Exception Handling

```java
@Test
public void testResourceNotFound() {
    assertThrows(ResourceNotFoundException.class, () -> {
        userController.getById("nonexistent-id");
    });
}

@Test
public void testValidationException() {
    UserDTO invalidUser = new UserDTO();
    invalidUser.setEmail("invalid-email");
    
    assertThrows(ValidationException.class, () -> {
        userService.create(invalidUser);
    });
}
```

This exception handling system provides robust error management with comprehensive logging, making it easier to debug issues and maintain the application.
