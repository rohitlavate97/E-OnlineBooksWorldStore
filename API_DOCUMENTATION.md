# API Documentation

## Project Information
- **Version:** 0.0.1-SNAPSHOT
- **Spring Boot Version:** 3.5.5
- **Java Version:** 17

## Overview

This document provides comprehensive API documentation for the E-OnlineBooksWorldStore application. The API is built using Spring Boot and follows RESTful principles.

## 🚀 Interactive API Documentation

The application includes **Swagger/OpenAPI 3.0** integration for interactive API documentation and testing:

- **Swagger UI**: `http://localhost:7070/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:7070/v3/api-docs`
- **OpenAPI YAML**: `http://localhost:7070/v3/api-docs.yaml`

### Benefits of Swagger Integration
- **Interactive Testing**: Test API endpoints directly from the browser
- **Auto-generated Documentation**: Always up-to-date with your code
- **Request/Response Examples**: See exact data formats and schemas
- **API Schema**: Complete OpenAPI 3.0 specification
- **Developer Experience**: Easy API exploration and testing

## Base URL

```
http://localhost:7070
```

## Authentication

Currently, the API does not require authentication. All endpoints are publicly accessible.

## API Endpoints

### 1. User Registration

#### Register New User

**Endpoint:** `POST /userRegister`

**Description:** Creates a new user account in the system with input validation and proper error handling.

**Request Headers:**
```
Content-Type: application/json
Accept: application/json
```

**Request Body:**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "password": "securepassword123",
  "contactId": 12345
}
```

**Request Body Schema:**
| Field | Type | Required | Description |
|-------|------|----------|-------------|
| firstName | String | Yes | User's first name (2-50 characters) |
| lastName | String | Yes | User's last name (2-50 characters) |
| email | String | Yes | User's email address (must be valid format, cannot be null/empty) |
| password | String | Yes | User's password (minimum 8 characters, cannot be null/empty) |
| contactId | Long | No | Reference ID for contact information |

**Response Codes:**
| Status Code | Description |
|-------------|-------------|
| 201 | User successfully registered |
| 400 | Bad Request - Invalid input data |
| 500 | Internal Server Error |

**Success Response (201 Created):**
```json
{
  "statusCode": 201,
  "status": "Success",
  "message": "User Registered Successfully",
  "data": {
    "id": 12345,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "contactId": 12345,
    "createDate": "2024-01-15T10:30:00Z",
    "updatedDate": "2024-01-15T10:30:00Z"
  }
}
```

**Error Response (400 Bad Request):**
```json
{
  "statusCode": 400,
  "status": "Failed",
  "message": "Email and Password cannot be empty",
  "data": null
}
```

### 2. User Login

#### Authenticate User

**Endpoint:** `POST /login`

**Description:** Authenticates user credentials and provides access to the E-commerce online BookStore.

**Request Headers:**
```
Content-Type: application/json
Accept: application/json
```

**Request Body:**
```json
{
  "email": "john.doe@example.com",
  "password": "securepassword123"
}
```

**Request Body Schema:**
| Field | Type | Required | Description |
|-------|------|----------|-------------|
| email | String | Yes | Registered email address |
| password | String | Yes | User's password |

**Response Codes:**
| Status Code | Description |
|-------------|-------------|
| 201 | Login successful |
| 400 | Invalid credentials or missing data |
| 500 | Internal Server Error |

**Success Response (201 Created):**
```json
{
  "statusCode": 201,
  "status": "Success",
  "message": "User Login Successfully, welcome to E-commerce online BooksStore",
  "data": {
    "id": 12345,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "contactId": 12345
  }
}
```

**Error Response (400 Bad Request):**
```json
{
  "statusCode": 400,
  "status": "Failed",
  "message": "Invalid Email and Password",
  "data": null
}
```

## Data Models

### UserRegData

```java
public class UserRegData {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Long contactId;
    
    // Constructors, getters, and setters
}
```

### ResponseMessage

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage {
    private Integer statusCode;
    private String status;
    private String message;
    private Object data;
    private List<?> list;
    
    // Constructors for different response types
    public ResponseMessage(Integer statusCode, String status, String message);
    public ResponseMessage(Integer statusCode, String status, String message, Object data);
}
```

### UserRegister Entity

```java
@Entity
@Table(name="user_register")
public class UserRegister {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="first_name")
    private String firstName;
    
    @Column(name="last_name")
    private String lastName;
    
    @Column(name="email")
    private String email;
    
    @Column(name="password")
    private String password;
    
    @Column(name="contactId")
    private Long contactId;
    
    @CreationTimestamp
    @Column(name="created_date")
    private LocalDateTime createDate;
    
    @UpdateTimestamp
    @Column(name="updated_date")
    private LocalDateTime updatedDate;
}
```

## Error Handling

The API uses standard HTTP status codes and returns detailed error messages in a consistent format:

```json
{
  "statusCode": <HTTP_STATUS_CODE>,
  "status": "Failed",
  "message": "<Error Message>",
  "data": null
}
```

## Rate Limiting

Currently, there are no rate limits implemented on the API endpoints.

## API Versioning

The current API version is v1 (implicit in the base URL). Future versions will be explicitly versioned in the URL path.

## Best Practices

1. Always include the Content-Type header in requests
2. Handle all possible response status codes in your client
3. Implement proper error handling for failed requests
4. Store sensitive data securely
5. Use HTTPS in production environments

## Support

For API support or issues, please:
1. Check the Swagger documentation
2. Review this documentation thoroughly
3. Contact the development team if issues persist

---

**Last Updated:** January 2024
**Version:** 1.0.0
