# Logging System Documentation

## Overview

This project includes a comprehensive logging system that tracks all operations across controllers, services, and repositories. The logging system provides detailed information about user actions, system operations, errors, and performance metrics.

## Features

- **Automatic logging** through AspectJ aspects
- **Manual logging** through LogService
- **Annotation-based logging** with @Loggable
- **Comprehensive log data** including user info, HTTP details, execution time
- **Multiple log levels** (INFO, WARN, ERROR, DEBUG, TRACE)
- **Various action types** (CREATE, READ, UPDATE, DELETE, LOGIN, LOGOUT, etc.)
- **RESTful API** for log management and querying

## Components

### 1. Log Entity
The `Log` entity stores all log information including:
- Level (INFO, WARN, ERROR, DEBUG, TRACE)
- Action (CREATE, READ, UPDATE, DELETE, etc.)
- Entity name and ID
- User information
- HTTP request details
- Execution time
- Timestamps

### 2. LogService
Provides methods for creating and querying logs:
```java
@Autowired
private LogService logService;

// Simple logging
logService.logAction(Log.LogLevel.INFO, Log.LogAction.CREATE, "User", "userId", "adminId", "User created successfully");

// Convenience methods
logService.logCreate("User", "userId", "adminId", "User created");
logService.logUpdate("User", "userId", "adminId", "User updated");
logService.logDelete("User", "userId", "adminId", "User deleted");
logService.logError("User", "userId", "adminId", "Error occurred", "stack trace");
```

### 3. @Loggable Annotation
Use this annotation for fine-grained control over logging:
```java
@Loggable(
    action = Log.LogAction.CREATE,
    entity = "User",
    level = Log.LogLevel.INFO,
    message = "Creating new user",
    logParams = true,
    logReturn = true,
    logExecutionTime = true
)
public UserDTO createUser(@RequestBody UserDTO dto) {
    // method implementation
}
```

### 4. Automatic Logging Aspects
The system automatically logs:
- All controller method executions
- Service CRUD operations (create, update, delete, save)
- Repository save and delete operations
- Exceptions across the application

### 5. Log API Endpoints

#### Get All Logs
```
GET /api/v1/logs?page=0&size=20
```

#### Get Logs by Level
```
GET /api/v1/logs/level/ERROR
```

#### Get Logs by Action
```
GET /api/v1/logs/action/CREATE
```

#### Get Logs by Entity
```
GET /api/v1/logs/entity/User
```

#### Get Logs by User
```
GET /api/v1/logs/user/{userId}
```

#### Get Error Logs
```
GET /api/v1/logs/errors?page=0&size=20
```

#### Get Logs for Specific Entity Instance
```
GET /api/v1/logs/entity/User/{userId}?page=0&size=10
```

#### Get Logs by Date Range
```
GET /api/v1/logs/date-range?startDate=2024-01-01T00:00:00&endDate=2024-12-31T23:59:59&page=0&size=20
```

#### Get Statistics
```
GET /api/v1/logs/stats/errors/24h    # Error count in last 24 hours
GET /api/v1/logs/stats/warnings/24h # Warning count in last 24 hours
```

#### Create Manual Log
```
POST /api/v1/logs/manual
Content-Type: application/json

{
    "level": "INFO",
    "action": "PROCESS",
    "entity": "System",
    "message": "Manual log entry",
    "details": "Additional details"
}
```

## Implementation Examples

### Controller with Logging
```java
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final LogService logService;

    @PostMapping
    @Loggable(action = Log.LogAction.CREATE, entity = "User", logParams = true, logReturn = true)
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO dto) {
        logService.logAction(Log.LogLevel.INFO, Log.LogAction.CREATE, "User", null, "system", 
                           "Attempting to create new user: " + dto.getEmail());
        
        UserDTO created = userService.create(dto);
        
        logService.logCreate("User", created.getId(), "system", 
                           "User created successfully with email: " + created.getEmail());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    @Loggable(action = Log.LogAction.DELETE, entity = "User", logParams = true)
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            userService.delete(id);
            logService.logDelete("User", id, "system", "User deleted successfully");
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logService.logError("User", id, "system", "Failed to delete user: " + e.getMessage(), 
                              e.getStackTrace().toString());
            return ResponseEntity.notFound().build();
        }
    }
}
```

### Service with Logging
```java
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final LogService logService;

    @Override
    @Transactional
    @Loggable(action = Log.LogAction.CREATE, entity = "User", logExecutionTime = true)
    public UserDTO create(UserDTO dto) {
        try {
            logService.logAction(Log.LogLevel.INFO, Log.LogAction.CREATE, "User", null, "system", 
                               "Starting user creation process for email: " + dto.getEmail());
            
            User entity = userMapper.toEntity(dto);
            User savedEntity = userRepository.save(entity);
            UserDTO result = userMapper.toDTO(savedEntity);
            
            logService.logCreate("User", savedEntity.getId(), "system", 
                               "User created successfully");
            
            return result;
        } catch (Exception e) {
            logService.logError("User", null, "system", 
                              "Failed to create user: " + e.getMessage(),
                              e.getStackTrace().toString());
            throw e;
        }
    }
}
```

## Configuration

### Enable AspectJ
The system uses AspectJ for automatic logging. Make sure you have:

1. **Dependency** in `pom.xml`:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

2. **Configuration** class:
```java
@Configuration
@EnableAspectJAutoProxy
public class AspectConfig {
}
```

### Database
The Log entity will automatically create a `logs` table with all necessary columns. Make sure your database user has CREATE and INSERT permissions.

## Best Practices

1. **Use appropriate log levels**:
   - INFO: Normal operations
   - WARN: Potentially harmful situations
   - ERROR: Error events
   - DEBUG: Fine-grained informational events

2. **Include context**: Always provide meaningful messages and include relevant IDs

3. **Don't log sensitive data**: Avoid logging passwords, tokens, or personal information

4. **Use the @Loggable annotation** for important business operations

5. **Monitor log volume**: In high-traffic applications, consider log retention policies

6. **Use structured logging**: Include relevant entity names and IDs for easier searching

## Log Levels and Actions

### Log Levels
- `INFO`: General information about application flow
- `WARN`: Potentially harmful situations that don't stop execution
- `ERROR`: Error events that might still allow the application to continue
- `DEBUG`: Fine-grained informational events for debugging
- `TRACE`: Even finer-grained informational events than DEBUG

### Log Actions
- `CREATE`: Resource creation operations
- `READ`: Resource read/fetch operations
- `UPDATE`: Resource modification operations
- `DELETE`: Resource deletion operations
- `LOGIN`: User authentication operations
- `LOGOUT`: User logout operations
- `SEARCH`: Search operations
- `EXPORT`: Data export operations
- `IMPORT`: Data import operations
- `VALIDATE`: Validation operations
- `PROCESS`: General processing operations
- `SEND_EMAIL`: Email sending operations
- `SEND_NOTIFICATION`: Notification sending operations

## Troubleshooting

1. **Logs not appearing**: Check that AspectJ is properly configured and @EnableAspectJAutoProxy is present

2. **Performance issues**: Consider asynchronous logging for high-volume applications

3. **Database errors**: Ensure the logs table exists and the application has proper database permissions

4. **Memory issues**: Implement log cleanup/archival strategies for long-running applications
