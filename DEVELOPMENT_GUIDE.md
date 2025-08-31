# Development Guide

## Project Overview
E-OnlineBooksWorldStore is a Spring Boot application for managing an online bookstore platform.

## Development Environment Setup

### Required Tools
- **JDK 17**: Required for application development
- **Maven 3.6+**: Build and dependency management
- **MySQL 8.0+**: Primary database
- **IDE**: IntelliJ IDEA (recommended), Eclipse, or VS Code
- **Git**: Version control
- **Postman/Insomnia**: API testing

### IDE Setup

#### IntelliJ IDEA
1. Install Plugins:
   - Lombok
   - Spring Boot Assistant
   - SonarLint
   - Git Integration
   - Maven Helper

2. Configure:
   - Java 17 SDK
   - Enable annotation processing
   - Set project encoding to UTF-8

#### VS Code
1. Install Extensions:
   - Java Extension Pack
   - Spring Boot Extension Pack
   - Lombok Annotations Support
   - SonarLint
   - GitLens

### Local Development Setup

1. Clone repository:
```bash
git clone <repository-url>
cd E-OnlineBooksWorldStore
```

2. Configure application.properties:
```properties
# Application
spring.application.name=E-OnlineBooksWorldStore
server.port=7070

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/ebooksstore?createDatabaseIfNotExist=true
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Logging
logging.level.root=INFO
logging.level.com.onlinebookstore=DEBUG
```

3. Build and run:
```bash
mvn clean install
mvn spring-boot:run
```

## Project Structure

```
src/
├── main/
│   ├── java/com/onlinebookstore/
│   │   ├── controller/     # REST endpoints
│   │   ├── entity/        # JPA entities
│   │   ├── model/         # DTOs
│   │   ├── repository/    # Data access
│   │   ├── service/       # Business logic interfaces
│   │   ├── serviceImpl/   # Service implementations
│   │   └── utility/       # Helper classes
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── com/onlinebookstore/
            └── *Tests.java
```

## Coding Standards

### 1. Naming Conventions
- **Classes**: PascalCase (e.g., `UserController`)
- **Methods**: camelCase (e.g., `getUserById`)
- **Variables**: camelCase (e.g., `firstName`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `MAX_RETRY_COUNT`)
- **Packages**: lowercase (e.g., `com.onlinebookstore.controller`)

### 2. Code Style
```java
// Class template
@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    // Constructor injection
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Method template
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        try {
            UserResponse user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            log.error("User not found with id: {}", id, e);
            return ResponseEntity.notFound().build();
        }
    }
}
```

### 3. Best Practices
- Use constructor injection over field injection
- Keep methods small and focused
- Write comprehensive unit tests
- Use meaningful variable names
- Document public APIs
- Handle exceptions properly
- Use logging effectively

## Testing

### 1. Unit Testing
```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void whenGetUserById_thenReturnUser() {
        // Given
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        User result = userService.getUserById(userId);

        // Then
        assertNotNull(result);
        assertEquals(userId, result.getId());
        verify(userRepository).findById(userId);
    }
}
```

### 2. Integration Testing
```java
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenGetUser_thenReturnUser() throws Exception {
        mockMvc.perform(get("/api/v1/users/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1));
    }
}
```

## Error Handling

### 1. Global Exception Handler
```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
```

### 2. Custom Exceptions
```java
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
```

## Database Management

### 1. Entity Guidelines
```java
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
```

### 2. Repository Pattern
```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
```

## API Documentation

### 1. Swagger/OpenAPI
```java
@Operation(summary = "Get user by ID")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "User found"),
    @ApiResponse(responseCode = "404", description = "User not found")
})
@GetMapping("/{id}")
public ResponseEntity<UserResponse> getUser(
    @Parameter(description = "User ID") @PathVariable Long id
) {
    // Implementation
}
```

## Version Control

### 1. Git Workflow
1. Create feature branch
2. Make changes
3. Write tests
4. Update documentation
5. Create pull request
6. Code review
7. Merge to main

### 2. Commit Messages
```
feat: add user registration
fix: resolve email validation issue
docs: update API documentation
test: add user service tests
refactor: improve error handling
```

## Logging

### 1. Log Levels
- ERROR: Application errors
- WARN: Warning messages
- INFO: Important business events
- DEBUG: Detailed information
- TRACE: Very detailed debugging

### 2. Best Practices
```java
@Slf4j
public class UserService {
    public User createUser(UserRequest request) {
        log.info("Creating new user with email: {}", request.getEmail());
        try {
            // Implementation
        } catch (Exception e) {
            log.error("Failed to create user: {}", e.getMessage(), e);
            throw e;
        }
    }
}
```

## Security

### 1. Password Handling
```java
@Service
public class PasswordService {
    public String encodePassword(String rawPassword) {
        return Base64.getEncoder().encodeToString(rawPassword.getBytes());
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        String encodedInput = encodePassword(rawPassword);
        return encodedInput.equals(encodedPassword);
    }
}
```

### 2. Input Validation
```java
public class UserRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
}
```

## Performance Optimization

### 1. Database Optimization
- Use appropriate indexes
- Implement pagination
- Optimize queries
- Use caching when appropriate

### 2. Code Optimization
- Use proper data structures
- Implement caching
- Optimize database queries
- Use async operations when appropriate

## Troubleshooting

### Common Issues
1. Application won't start
   - Check application.properties
   - Verify database connection
   - Check port availability

2. Database connection issues
   - Verify credentials
   - Check database service
   - Validate connection string

3. Build failures
   - Clean Maven cache
   - Update dependencies
   - Check Java version

## Additional Resources

### Documentation
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Maven Documentation](https://maven.apache.org/guides/)
