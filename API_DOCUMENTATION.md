# API Documentation

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

**Description:** Creates a new user account in the system.

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
| email | String | Yes | User's email address (must be valid format) |
| password | String | Yes | User's password (minimum 8 characters) |
| contactId | Long | No | Reference ID for contact information |

**Response:**

**Success Response (200 OK):**
```json
{
  "message": "User registered successfully",
  "userId": 12345,
  "timestamp": "2024-01-15T10:30:00Z"
}
```

**Error Response (400 Bad Request):**
```json
{
  "error": "Validation failed",
  "message": "Email is already registered",
  "timestamp": "2024-01-15T10:30:00Z",
  "details": [
    {
      "field": "email",
      "message": "Email must be unique"
    }
  ]
}
```

**Error Response (500 Internal Server Error):**
```json
{
  "error": "Internal server error",
  "message": "Failed to register user",
  "timestamp": "2024-01-15T10:30:00Z"
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

## Error Codes

| HTTP Status Code | Description | Common Causes |
|------------------|-------------|---------------|
| 200 | OK | Request successful |
| 201 | Created | Resource created successfully |
| 400 | Bad Request | Invalid request data or validation failed |
| 401 | Unauthorized | Authentication required (future implementation) |
| 403 | Forbidden | Access denied (future implementation) |
| 404 | Not Found | Resource not found |
| 409 | Conflict | Resource already exists (e.g., duplicate email) |
| 500 | Internal Server Error | Server-side error |

## Validation Rules

### User Registration Validation

- **firstName**: Required, 2-50 characters, alphanumeric and spaces only
- **lastName**: Required, 2-50 characters, alphanumeric and spaces only
- **email**: Required, valid email format, must be unique
- **password**: Required, minimum 8 characters, must contain at least one uppercase letter, one lowercase letter, and one number
- **contactId**: Optional, must be a positive integer if provided

## Rate Limiting

Currently, no rate limiting is implemented. Future versions may include:
- 100 requests per minute per IP address
- 1000 requests per hour per IP address

## Testing the API

### Using cURL

```bash
# Register a new user
curl -X POST http://localhost:7070/userRegister \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Jane",
    "lastName": "Smith",
    "email": "jane.smith@example.com",
    "password": "SecurePass123",
    "contactId": 67890
  }'
```

### Using Postman

1. Create a new POST request
2. Set URL to: `http://localhost:7070/userRegister`
3. Set Headers: `Content-Type: application/json`
4. Set Body (raw JSON):
```json
{
  "firstName": "Jane",
  "lastName": "Smith",
  "email": "jane.smith@example.com",
  "password": "SecurePass123",
  "contactId": 67890
}
```

### Using JavaScript/Fetch

```javascript
const userData = {
  firstName: "Jane",
  lastName: "Smith",
  email: "jane.smith@example.com",
  password: "SecurePass123",
  contactId: 67890
};

fetch('http://localhost:7070/userRegister', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
  },
  body: JSON.stringify(userData)
})
.then(response => response.json())
.then(data => console.log('Success:', data))
.catch((error) => console.error('Error:', error));
```

## Future API Endpoints

The following endpoints are planned for future releases:

### User Management
- `GET /users` - List all users
- `GET /users/{id}` - Get user by ID
- `PUT /users/{id}` - Update user
- `DELETE /users/{id}` - Delete user
- `POST /users/login` - User authentication

### Book Management
- `GET /books` - List all books
- `GET /books/{id}` - Get book by ID
- `POST /books` - Add new book
- `PUT /books/{id}` - Update book
- `DELETE /books/{id}` - Delete book

### Shopping Cart
- `GET /cart` - Get user's cart
- `POST /cart/items` - Add item to cart
- `PUT /cart/items/{id}` - Update cart item
- `DELETE /cart/items/{id}` - Remove item from cart

### Orders
- `GET /orders` - List user's orders
- `GET /orders/{id}` - Get order details
- `POST /orders` - Create new order
- `PUT /orders/{id}/status` - Update order status

## Support

For API support or questions:
1. Check the [Issues](https://github.com/rohitlavate97/E-OnlineBooksWorldStore/issues) page
2. Create a new issue with the "API" label
3. Contact the development team

---

**Last Updated:** January 2024
**Version:** 1.0.0
