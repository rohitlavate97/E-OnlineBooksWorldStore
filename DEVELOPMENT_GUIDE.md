# Development Guide

## Setup and Installation

### Prerequisites
- Java 17
- Maven 3.6.x or higher
- MySQL 8.0 or higher
- Your favorite IDE (IntelliJ IDEA, Eclipse, or VS Code)
- Git

### Project Dependencies
```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.5.5</version>
</parent>
```

### Local Development Setup

1. Clone the repository:
```bash
git clone <repository-url>
cd E-OnlineBooksWorldStore
```

2. Configure database:
   Create `application.properties` in `src/main/resources`:
```properties
spring.application.name=E-OnlineBooksWorldStore
server.port=7070
spring.datasource.url=jdbc:mysql://localhost:3306/ebooksstore?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

3. Build the project:
```bash
mvn clean install
```

4. Run the application:
```bash
mvn spring-boot:run
```

## Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── onlinebookstore/
│   │           ├── EOnlineBooksWorldStoreApplication.java
│   │           ├── SwaggerConfig.java
│   │           ├── controller/
│   │           │   └── UserRegistrationController.java
│   │           ├── entity/
│   │           │   └── UserRegister.java
│   │           ├── model/
│   │           │   ├── LoginModel.java
│   │           │   ├── ResponseMessage.java
│   │           │   └── UserRegData.java
│   │           ├── repository/
│   │           │   └── UserRepository.java
│   │           ├── service/
│   │           │   └── UserRegisterService.java
│   │           ├── serviceImpl/
│   │           │   └── UserRegisterServiceImpl.java
│   │           └── utility/
│   │               └── Constants.java
│   └── resources/
│       ├── application.properties
│       └── banner.txt
```

## Coding Standards

### General Guidelines
1. Follow Java naming conventions
2. Use meaningful names for classes, methods, and variables
3. Keep methods focused and single-responsibility
4. Write unit tests for new functionality
5. Document public APIs using Swagger annotations

### Code Formatting
- Use 4 spaces for indentation
- Maximum line length: 120 characters
- Use proper spacing around operators
- Group related methods together

### Best Practices
1. **Exception Handling**
   - Use custom exceptions for business logic
   - Always log exceptions with proper context
   - Return appropriate HTTP status codes

2. **API Design**
   - Follow RESTful principles
   - Use proper HTTP methods (GET, POST, PUT, DELETE)
   - Version your APIs
   - Document using Swagger annotations

3. **Security**
   - Sanitize input data
   - Use prepared statements
   - Hash passwords before storing
   - Implement proper authentication/authorization

4. **Testing**
   - Write unit tests for services
   - Write integration tests for controllers
   - Maintain test coverage above 80%

## Git Workflow

1. **Branch Naming**
   - Feature: `feature/description`
   - Bugfix: `fix/description`
   - Hotfix: `hotfix/description`

2. **Commit Messages**
   - Start with a verb (Add, Update, Fix, Remove)
   - Be descriptive but concise
   - Reference issue numbers

3. **Pull Request Process**
   - Create PR against develop branch
   - Add description of changes
   - Request review from team members
   - Address review comments
   - Squash commits before merging

## Building and Testing

### Build Commands
```bash
# Clean and install
mvn clean install

# Run tests
mvn test

# Run specific test class
mvn test -Dtest=TestClassName

# Generate test coverage report
mvn verify
```

### Testing Guidelines
1. Write unit tests for:
   - Service layer logic
   - Complex utility methods
   - Data transformations

2. Write integration tests for:
   - REST endpoints
   - Database operations
   - External service integrations

## Debugging

1. **Local Debugging**
   - Use IDE debugger
   - Enable debug logs in application.properties
   - Use actuator endpoints for monitoring

2. **Common Issues**
   - Database connection problems
   - Authentication issues
   - API response errors

## Documentation

1. Keep API documentation updated
2. Document configuration changes
3. Update README.md for major changes
4. Use proper JavaDoc comments

## Performance Considerations

1. Use caching where appropriate
2. Optimize database queries
3. Implement pagination for large datasets
4. Monitor memory usage

## Deployment

1. Build production-ready artifact
2. Update configuration for production
3. Follow security best practices
4. Set up monitoring and logging

## Support

For development support:
1. Check existing documentation
2. Review similar issues in issue tracker
3. Contact team lead for clarification
