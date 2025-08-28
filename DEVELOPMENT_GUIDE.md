# Development Guide

## 🚀 Getting Started for Developers

This guide will help you set up the development environment and start contributing to the E-OnlineBooksWorldStore project.

## 📋 Development Prerequisites

### Required Software
- **Java Development Kit (JDK) 17** or higher
- **Maven 3.6+**
- **MySQL 8.0+**
- **Git 2.20+**
- **IDE**: IntelliJ IDEA, Eclipse, or VS Code

### Recommended IDE Setup

#### IntelliJ IDEA
1. Install IntelliJ IDEA Community or Ultimate
2. Install Lombok plugin
3. Enable annotation processing in Settings → Build, Execution, Deployment → Compiler → Annotation Processors
4. Import project as Maven project

#### Eclipse
1. Install Eclipse IDE for Enterprise Java Developers
2. Install Lombok plugin
3. Import project as Maven project

#### VS Code
1. Install VS Code
2. Install Java Extension Pack
3. Install Spring Boot Extension Pack
4. Install Lombok Annotations Support

## 🔧 Environment Setup

### 1. Clone the Repository
```bash
git clone https://github.com/rohitlavate97/E-OnlineBooksWorldStore.git
cd E-OnlineBooksWorldStore
```

### 2. Database Setup
```bash
# Start MySQL service
sudo systemctl start mysql

# Access MySQL
mysql -u root -p

# Create database (optional - will be auto-created)
CREATE DATABASE ebooksstore;
```

### 3. Configuration
Update `src/main/resources/application.properties`:
```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 4. Build and Run
```bash
# Clean and compile
mvn clean compile

# Run the application
mvn spring-boot:run
```

## 🏗️ Project Architecture

### Layered Architecture
```
┌─────────────────────────────────────┐
│           Controller Layer          │ ← REST API endpoints
├─────────────────────────────────────┤
│            Service Layer            │ ← Business logic
├─────────────────────────────────────┤
│          Repository Layer           │ ← Data access
├─────────────────────────────────────┤
│            Entity Layer             │ ← Data models
└─────────────────────────────────────┘
```

### Package Structure
```
com.onlinebookstore/
├── controller/          # REST controllers
├── service/            # Service interfaces
├── serviceImpl/        # Service implementations
├── repository/         # Data access repositories
├── entity/             # JPA entities
├── model/              # DTOs and request/response models
└── utility/            # Utility classes
```

## 📝 Coding Standards

### Java Code Style
- Follow Java naming conventions
- Use meaningful variable and method names
- Maximum line length: 120 characters
- Indentation: 4 spaces (no tabs)

### Spring Boot Best Practices
- Use constructor injection instead of field injection
- Implement proper exception handling
- Use validation annotations for DTOs
- Follow REST API design principles

### Example Code Structure
```java
@RestController
public class UserRegistrationController {
    
    private final UserRegisterService userRegisterService;
    
    public UserRegistrationController(UserRegisterService userRegisterService) {
        this.userRegisterService = userRegisterService;
    }
    
    @PostMapping("/userRegister")
    public ResponseEntity<ResponseMessage> createUserRegistration(@RequestBody UserRegData userRegData) {
        try {
            // Input validation
            if (userRegData == null || userRegData.getEmail() == null || userRegData.getEmail().isBlank() ||
                userRegData.getPassword() == null || userRegData.getPassword().isBlank()) {
                return ResponseEntity.ok(new ResponseMessage(
                    HttpURLConnection.HTTP_BAD_REQUEST, 
                    Constants.FAILED, 
                    "Email and Password cannot be empty"
                ));
            }
            
            // Service call
            UserRegister user = userRegisterService.createUserRegService(userRegData);
            
            if (user != null) {
                return ResponseEntity.ok(new ResponseMessage(
                    HttpURLConnection.HTTP_CREATED, 
                    Constants.SUCCESS, 
                    "User Registered Successfully", 
                    user
                ));
            } else {
                return ResponseEntity.ok(new ResponseMessage(
                    HttpURLConnection.HTTP_BAD_REQUEST, 
                    Constants.FAILED, 
                    "User Registration not saved successfully"
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseMessage(
                HttpURLConnection.HTTP_INTERNAL_ERROR, 
                Constants.FAILURE, 
                "User Registration Failed: " + e.getMessage()
            ));
        }
    }
}
```

## 🧪 Testing

### Unit Testing
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=UserServiceTest

# Run tests with coverage
mvn jacoco:report
```

### Test Structure
```
src/test/java/
└── com/onlineboostore/
    ├── controller/      # Controller tests
    ├── service/         # Service tests
    ├── repository/      # Repository tests
    └── integration/     # Integration tests
```

### Example Test
```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserServiceImpl userService;
    
    @Test
    void createUser_ValidData_ReturnsUser() {
        // Given
        UserRequest request = new UserRequest("John", "Doe", "john@example.com");
        User user = new User(1L, "John", "Doe", "john@example.com");
        
        when(userRepository.save(any(User.class))).thenReturn(user);
        
        // When
        UserResponse response = userService.createUser(request);
        
        // Then
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getFirstName()).isEqualTo("John");
        verify(userRepository).save(any(User.class));
    }
}
```

## 🔄 Development Workflow

### 1. Feature Development
```bash
# Create feature branch
git checkout -b feature/user-authentication

# Make changes and commit
git add .
git commit -m "feat: add user authentication endpoint"

# Push to remote
git push origin feature/user-authentication
```

### 2. Pull Request Process
1. Create pull request from feature branch to main
2. Add description of changes
3. Request code review
4. Address review comments
5. Merge after approval

### 3. Commit Message Convention
```
type(scope): description

feat: add new feature
fix: bug fix
docs: documentation changes
style: code style changes
refactor: code refactoring
test: add or update tests
chore: maintenance tasks
```

## 🚀 Adding New Features

### 1. Create Entity
```java
@Entity
@Table(name = "books")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "author", nullable = false)
    private String author;
    
    @Column(name = "isbn", unique = true)
    private String isbn;
    
    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;
}
```

### 2. Create Response Model
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
    
    // Constructor for responses without data
    public ResponseMessage(Integer statusCode, String status, String message) {
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
    }
    
    // Constructor for responses with data
    public ResponseMessage(Integer statusCode, String status, String message, Object data) {
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
```

### 2. Create Repository
```java
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);
    List<Book> findByAuthorContainingIgnoreCase(String author);
    List<Book> findByTitleContainingIgnoreCase(String title);
}
```

### 3. Create Service
```java
public interface BookService {
    Book createBook(BookRequest request);
    Book getBookById(Long id);
    List<Book> getAllBooks();
    Book updateBook(Long id, BookRequest request);
    void deleteBook(Long id);
}
```

### 4. Create Controller
```java
@RestController
@RequestMapping("/api/v1/books")
@Validated
public class BookController {
    
    private final BookService bookService;
    
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    
    @PostMapping
    public ResponseEntity<ResponseMessage> createBook(@Valid @RequestBody BookRequest request) {
        try {
            Book book = bookService.createBook(request);
            if (book != null) {
                return ResponseEntity.ok(new ResponseMessage(
                    HttpURLConnection.HTTP_CREATED, 
                    Constants.SUCCESS, 
                    "Book created successfully", 
                    book
                ));
            } else {
                return ResponseEntity.ok(new ResponseMessage(
                    HttpURLConnection.HTTP_BAD_REQUEST, 
                    Constants.FAILED, 
                    "Failed to create book"
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseMessage(
                HttpURLConnection.HTTP_INTERNAL_ERROR, 
                Constants.FAILURE, 
                "Book creation failed: " + e.getMessage()
            ));
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> getBook(@PathVariable Long id) {
        try {
            Book book = bookService.getBookById(id);
            if (book != null) {
                return ResponseEntity.ok(new ResponseMessage(
                    HttpURLConnection.HTTP_OK, 
                    Constants.SUCCESS, 
                    "Book retrieved successfully", 
                    book
                ));
            } else {
                return ResponseEntity.ok(new ResponseMessage(
                    HttpURLConnection.HTTP_NOT_FOUND, 
                    Constants.FAILED, 
                    "Book not found"
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseMessage(
                HttpURLConnection.HTTP_INTERNAL_ERROR, 
                Constants.FAILURE, 
                "Failed to retrieve book: " + e.getMessage()
            ));
        }
    }
}
```

## 🔍 Debugging

### Logging
```java
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    
    public UserResponse createUser(UserRequest request) {
        log.info("Creating user with email: {}", request.getEmail());
        // ... implementation
        log.info("User created successfully with ID: {}", user.getId());
        return response;
    }
}
```

### Application Properties for Debugging
```properties
# Enable debug logging
logging.level.com.onlinebookstore=DEBUG
logging.level.org.springframework.web=DEBUG

# Show SQL queries
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Enable H2 console (for development)
spring.h2.console.enabled=true
```

## 📊 Performance Considerations

### Database Optimization
- Use appropriate indexes
- Implement pagination for large datasets
- Use lazy loading for relationships
- Implement caching where appropriate

### API Optimization
- Implement response compression
- Use DTOs to limit data transfer
- Implement proper error handling
- Add request/response logging

## 🔒 Security Best Practices

### Input Validation
```java
@Valid
public class UserRequest {
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;
    
    @Email(message = "Email must be valid")
    private String email;
}
```

## 🚀 Enhanced API Best Practices

### 1. Standardized Response Format
Always use the `ResponseMessage` model for consistent API responses:
```java
// Success response with data
return ResponseEntity.ok(new ResponseMessage(
    HttpURLConnection.HTTP_CREATED, 
    Constants.SUCCESS, 
    "Resource created successfully", 
    createdResource
));

// Error response without data
return ResponseEntity.ok(new ResponseMessage(
    HttpURLConnection.HTTP_BAD_REQUEST, 
    Constants.FAILED, 
    "Validation failed: required fields missing"
));
```

### 2. Input Validation
Implement validation at the controller level:
```java
@PostMapping
public ResponseEntity<ResponseMessage> createResource(@RequestBody ResourceRequest request) {
    try {
        // Validate required fields
        if (request == null || request.getName() == null || request.getName().isBlank()) {
            return ResponseEntity.ok(new ResponseMessage(
                HttpURLConnection.HTTP_BAD_REQUEST, 
                Constants.FAILED, 
                "Name cannot be empty"
            ));
        }
        
        // Process request
        Resource resource = service.createResource(request);
        
        if (resource != null) {
            return ResponseEntity.ok(new ResponseMessage(
                HttpURLConnection.HTTP_CREATED, 
                Constants.SUCCESS, 
                "Resource created successfully", 
                resource
            ));
        } else {
            return ResponseEntity.ok(new ResponseMessage(
                HttpURLConnection.HTTP_BAD_REQUEST, 
                Constants.FAILED, 
                "Failed to create resource"
            ));
        }
    } catch (Exception e) {
        return ResponseEntity.ok(new ResponseMessage(
            HttpURLConnection.HTTP_INTERNAL_ERROR, 
            Constants.FAILURE, 
            "Creation failed: " + e.getMessage()
        ));
    }
}
```

### 3. Service Layer Return Types
Return actual entities instead of strings for better data flow:
```java
// Good: Return entity
public UserRegister createUser(UserRegData userData) {
    // Implementation
    return savedUser;
}

// Avoid: Returning strings
public String createUser(UserRegData userData) {
    // Implementation
    return "User created successfully"; // Not recommended
}
```

### SQL Injection Prevention
- Use JPA repositories (already implemented)
- Avoid raw SQL queries
- Use parameterized queries if raw SQL is necessary

## 📚 Useful Resources

### Spring Boot Documentation
- [Spring Boot Reference](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Spring Web](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html)

### Java Resources
- [Java Documentation](https://docs.oracle.com/en/java/)
- [Effective Java](https://www.amazon.com/Effective-Java-Joshua-Bloch/dp/0134685997)

### Tools
- [Maven Documentation](https://maven.apache.org/guides/)
- [MySQL Documentation](https://dev.mysql.com/doc/)

## 🤝 Contributing

### Code Review Checklist
- [ ] Code follows project standards
- [ ] Tests are included and passing
- [ ] Documentation is updated
- [ ] No security vulnerabilities
- [ ] Performance considerations addressed

### Getting Help
1. Check existing documentation
2. Search existing issues
3. Create new issue with detailed description
4. Ask in project discussions

---

**Happy Coding! 🚀✨**

For questions or support, please refer to the main [README.md](README.md) or create an issue in the repository.
