# API Documentation

## Authentication

All API endpoints require authentication. Include the session cookie or authorization header in your requests.

```http
Authorization: Bearer <token>
Cookie: JSESSIONID=<session-id>
```

## Common Response Codes

| Code | Description |
|------|------------|
| 200  | Success |
| 201  | Created |
| 204  | No Content |
| 400  | Bad Request |
| 401  | Unauthorized |
| 403  | Forbidden |
| 404  | Not Found |
| 500  | Internal Server Error |

## Pagination

All list endpoints support pagination using the following parameters:

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| page | integer | 0 | Page number (0-based) |
| size | integer | 20 | Number of items per page |
| sort | string | id,asc | Sort criteria (field,direction) |

Example:
```http
GET /api/v1/users?page=0&size=10&sort=email,desc
```

## Data Transfer Objects (DTOs)

### UserDTO
```json
{
  "id": "string",
  "email": "string",
  "role": "ADMIN|MANAGER|EMPLOYEE",
  "createdAt": "2024-01-01T12:00:00Z",
  "updatedAt": "2024-01-01T12:00:00Z"
}
```

### ClientDTO
```json
{
  "id": "string",
  "name": "string",
  "email": "string",
  "isAnonymous": boolean,
  "createdAt": "2024-01-01T12:00:00Z"
}
```

### EmployeeProfileDTO
```json
{
  "id": "string",
  "userId": "string",
  "name": "string",
  "position": "string",
  "department": "string",
  "hireDate": "2024-01-01",
  "performanceScore": 4.5
}
```

### SubscriptionDTO
```json
{
  "id": "string",
  "userId": "string",
  "plan": "BASIC|PREMIUM|ENTERPRISE",
  "status": "ACTIVE|INACTIVE|SUSPENDED",
  "startDate": "2024-01-01",
  "endDate": "2024-12-31",
  "paymentMethod": "CARD|BANK_TRANSFER|PAYPAL"
}
```

### InsightDTO
```json
{
  "id": "string",
  "title": "string",
  "description": "string",
  "type": "PERFORMANCE|FEEDBACK|TREND",
  "data": {},
  "createdAt": "2024-01-01T12:00:00Z"
}
```

### GoalDTO
```json
{
  "id": "string",
  "title": "string",
  "description": "string",
  "targetValue": 100,
  "currentValue": 75,
  "deadline": "2024-12-31",
  "status": "IN_PROGRESS|COMPLETED|OVERDUE"
}
```

### NotificationPreferenceDTO
```json
{
  "id": "string",
  "userId": "string",
  "emailEnabled": boolean,
  "smsEnabled": boolean,
  "pushEnabled": boolean,
  "feedbackAlerts": boolean,
  "reportAlerts": boolean
}
```

## Bulk Operations

### Bulk Create
```http
POST /api/v1/{resource}/bulk
Content-Type: application/json

[
  {
    // DTO object 1
  },
  {
    // DTO object 2
  }
]
```

### Bulk Delete
```http
DELETE /api/v1/{resource}/bulk
Content-Type: application/json

["id1", "id2", "id3"]
```

## Error Responses

### Validation Error
```json
{
  "status": "error",
  "message": "Validation failed",
  "errors": [
    {
      "field": "email",
      "message": "Email is required"
    }
  ],
  "timestamp": "2024-01-01T12:00:00Z"
}
```

### General Error
```json
{
  "status": "error",
  "message": "Error description",
  "code": "ERROR_CODE",
  "timestamp": "2024-01-01T12:00:00Z"
}
```

## Search and Filtering

### Search Parameters
Many endpoints support search using query parameters:

```http
GET /api/v1/users?search=john&email=example.com&role=ADMIN
```

### Advanced Search
For complex queries, use the search endpoint:

```http
POST /api/v1/{resource}/search
Content-Type: application/json

{
  "criteria": [
    {
      "field": "email",
      "operator": "CONTAINS",
      "value": "example"
    },
    {
      "field": "createdAt",
      "operator": "GREATER_THAN",
      "value": "2024-01-01"
    }
  ],
  "logic": "AND",
  "page": 0,
  "size": 20,
  "sort": "email,asc"
}
```

## Rate Limiting

API requests are rate-limited to prevent abuse:

- **Authenticated users**: 1000 requests per hour
- **Anonymous users**: 100 requests per hour

Rate limit headers are included in responses:
```http
X-RateLimit-Limit: 1000
X-RateLimit-Remaining: 999
X-RateLimit-Reset: 1640995200
```

## Webhook Integration

Subscribe to events using webhooks:

```http
POST /api/v1/webhooks
Content-Type: application/json

{
  "url": "https://your-app.com/webhook",
  "events": ["user.created", "feedback.received"],
  "secret": "your-webhook-secret"
}
```

### Webhook Events
- `user.created` - New user registration
- `user.updated` - User profile update
- `feedback.received` - New feedback submitted
- `employee.performance.updated` - Employee performance change
- `subscription.status.changed` - Subscription status update

## OpenAPI/Swagger Documentation

Interactive API documentation is available at:
```
http://localhost:8080/swagger-ui.html
```

OpenAPI specification:
```
http://localhost:8080/v3/api-docs
```
