# API Documentation

## Project Information
- **Version:** 0.0.1-SNAPSHOT
- **Spring Boot Version:** 3.5.5
- **Java Version:** 17
- **Last Updated:** August 31, 2025

## Overview

This document provides comprehensive API documentation for the E-OnlineBooksWorldStore application. The API is built using Spring Boot and follows RESTful principles.

## 🚀 Interactive API Documentation

The application includes **Swagger/OpenAPI 3.0** integration for interactive API documentation and testing:

- **Swagger UI**: `http://localhost:7070/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:7070/v3/api-docs`
- **OpenAPI YAML**: `http://localhost:7070/v3/api-docs.yaml`

## Base URL

```
http://localhost:7070
```

## API Endpoints

### 1. User Management

#### Register New User
- **Endpoint:** `POST /userRegister`
- **Description:** Creates a new user account
- **Request Body:**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "password": "securepassword123",
  "contactId": 12345
}
```
- **Response:** 201 Created
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
    "createDate": "2025-08-31T10:30:00Z",
    "updatedDate": "2025-08-31T10:30:00Z"
  }
}
```

#### User Login
- **Endpoint:** `POST /login`
- **Description:** Authenticates user credentials
- **Request Body:**
```json
{
  "email": "john.doe@example.com",
  "password": "securepassword123"
}
```
- **Response:** 200 OK
```json
{
  "statusCode": 200,
  "status": "Success",
  "message": "Login Successful",
  "data": {
    "userId": 12345,
    "email": "john.doe@example.com"
  }
}
```

### 2. Customer Management

#### Create Customer
- **Endpoint:** `POST /customers`
- **Description:** Creates a new customer profile
- **Request Body:**
```json
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "phone": "+1234567890",
  "address": "123 Main St, City, Country"
}
```
- **Response:** 201 Created

#### Get Customer
- **Endpoint:** `GET /customers/{id}`
- **Description:** Retrieves customer information
- **Parameters:** 
  - `id` (path) - Customer ID
- **Response:** 200 OK

#### Update Customer
- **Endpoint:** `PUT /customers/{id}`
- **Description:** Updates customer information
- **Parameters:**
  - `id` (path) - Customer ID
- **Response:** 200 OK

#### Delete Customer
- **Endpoint:** `DELETE /customers/{id}`
- **Description:** Removes customer profile
- **Parameters:**
  - `id` (path) - Customer ID
- **Response:** 204 No Content

### 3. File Management

#### Upload File
- **Endpoint:** `POST /files/upload`
- **Description:** Uploads a file to the system
- **Request:** Multipart form data
- **Response:** 201 Created

#### Download File
- **Endpoint:** `GET /files/{id}`
- **Description:** Downloads a specific file
- **Parameters:**
  - `id` (path) - File ID
- **Response:** 200 OK

## Error Responses

### Common Error Codes
- **400 Bad Request** - Invalid input data
- **401 Unauthorized** - Authentication required
- **403 Forbidden** - Insufficient permissions
- **404 Not Found** - Resource not found
- **500 Internal Server Error** - Server-side error

### Error Response Format
```json
{
  "statusCode": 400,
  "status": "Error",
  "message": "Detailed error message",
  "errors": [
    "Validation error 1",
    "Validation error 2"
  ]
}
```

## Rate Limiting

- **Rate Limit:** 100 requests per minute per IP
- **Headers:**
  - `X-RateLimit-Limit`: Maximum requests allowed
  - `X-RateLimit-Remaining`: Remaining requests
  - `X-RateLimit-Reset`: Time until limit resets

## API Versioning

Current API version is v1, included in the base URL path:
```
http://localhost:7070/api/v1/
```

## Security

- Base64 encoding for passwords
- Input validation and sanitization
- CORS configuration
- Error handling with proper status codes

## Best Practices

1. Always include the `Accept` and `Content-Type` headers
2. Use HTTPS in production
3. Include proper error handling
4. Validate input data
5. Handle pagination for list endpoints

## Support

For API support or issues:
- Email: api-support@ebooksworldstore.com
- Documentation Issues: Create a GitHub issue
- API Status: Check status page
