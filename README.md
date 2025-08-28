# E-OnlineBooksWorldStore

A Spring Boot-based online bookstore application that provides user registration and management functionality for an e-commerce platform.

## 🚀 Project Overview

E-OnlineBooksWorldStore is a web application built with Spring Boot that serves as the foundation for an online bookstore. The application currently supports user registration and authentication, with a scalable architecture designed for future expansion to include book catalog, shopping cart, and order management features.

## 🛠️ Technology Stack

- **Backend Framework**: Spring Boot 3.5.5
- **Java Version**: 17
- **Database**: MySQL 8.0+
- **ORM**: Spring Data JPA with Hibernate
- **Build Tool**: Maven
- **Lombok**: For reducing boilerplate code
- **DevTools**: For development convenience
- **API Documentation**: Swagger/OpenAPI 3.0 with SpringDoc

## 📋 Prerequisites

Before running this application, ensure you have the following installed:

- **Java 17** or higher
- **Maven 3.6+**
- **MySQL 8.0+**
- **Git** (for version control)

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/rohitlavate97/E-OnlineBooksWorldStore.git
cd E-OnlineBooksWorldStore
```

### 2. Database Setup

1. **Start MySQL Server**
   ```bash
   sudo systemctl start mysql
   # or
   sudo service mysql start
   ```

2. **Create Database** (optional - will be created automatically)
   ```sql
   CREATE DATABASE ebooksstore;
   ```

3. **Update Configuration**
   Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.username=your_mysql_username
   spring.datasource.password=your_mysql_password
   ```

### 3. Build and Run

```bash
# Clean and compile
mvn clean compile

# Run the application
mvn spring-boot:run

# Or use the Maven wrapper
./mvnw spring-boot:run
```

The application will start on `http://localhost:7070`

### 4. Access Swagger UI
Once the application is running, you can access the interactive API documentation at:
```
http://localhost:7070/swagger-ui.html
```

The Swagger UI provides:
- Interactive API testing interface
- Request/response examples
- API schema documentation
- Endpoint testing capabilities

## 📁 Project Structure

```
E-OnlineBooksWorldStore/
├── src/
│   ├── main/
│   │   ├── java/com/onlineboostore/
│   │   │   ├── controller/          # REST API controllers
│   │   │   ├── entity/              # JPA entities
│   │   │   ├── model/               # Data transfer objects
│   │   │   ├── repository/          # Data access layer
│   │   │   ├── service/             # Business logic layer
│   │   │   ├── serviceImpl/         # Service implementations
│   │   │   └── utility/             # Utility classes
│   │   └── resources/
│   │       ├── application.properties # Configuration
│   │       ├── static/              # Static resources
│   │       └── templates/           # HTML templates
│   └── test/                        # Test files
├── pom.xml                          # Maven configuration
├── README.md                        # This file
└── HELP.md                          # Spring Boot help
```

## 🔌 API Endpoints

### Interactive API Documentation
The application includes **Swagger/OpenAPI 3.0** documentation accessible at:
- **Swagger UI**: `http://localhost:7070/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:7070/v3/api-docs`
- **OpenAPI YAML**: `http://localhost:7070/v3/api-docs.yaml`

### User Registration

- **POST** `/userRegister`
  - **Description**: Register a new user with validation
  - **Request Body**: `UserRegData` object
  - **Response**: `ResponseMessage` with status, message, and data
  - **Validation**: Email and password are required and cannot be empty
  - **Status Codes**: 
    - `201` - User created successfully
    - `400` - Bad request (validation failed)
    - `500` - Internal server error

#### Request Body Structure
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "password": "securepassword123",
  "contactId": 12345
}
```

## 🗄️ Database Schema

### User Registration Table
- `id` - Primary key (auto-generated)
- `first_name` - User's first name
- `last_name` - User's last name
- `email` - User's email address (required, unique)
- `password` - User's password (required, Base64 encoded)
- `contact_id` - Reference to contact information
- `created_date` - Timestamp when record was created
- `updated_date` - Timestamp when record was last updated

### ResponseMessage Model
- `statusCode` - HTTP status code (200, 201, 400, 500)
- `status` - Status indicator ("Success", "Failed", "Failure")
- `message` - Human-readable response message
- `data` - Response data payload (can be null)
- `list` - List data payload (for collection responses)

## 🔧 Configuration

### Application Properties
- **Server Port**: 7070
- **Database**: MySQL with auto-creation
- **JPA**: Hibernate with auto-update schema
- **SQL Logging**: Enabled for development

### Environment Variables
- `SPRING_DATASOURCE_USERNAME` - MySQL username
- `SPRING_DATASOURCE_PASSWORD` - MySQL password

## 🧪 Testing

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=EOnlineBooksWorldStoreApplicationTests

# Run tests with coverage
mvn jacoco:report
```

## 🚀 Deployment

### Development
```bash
mvn spring-boot:run
```

### Production Build
```bash
mvn clean package
java -jar target/E-OnlineBooksWorldStore-0.0.1-SNAPSHOT.jar
```

### Docker (Future Enhancement)
```dockerfile
FROM openjdk:17-jre-slim
COPY target/E-OnlineBooksWorldStore-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 7070
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 🔮 Future Enhancements

- [ ] Book catalog management
- [ ] Shopping cart functionality
- [ ] Order processing system
- [ ] User authentication and authorization
- [ ] Payment gateway integration
- [ ] Admin dashboard
- [ ] Search and filtering capabilities
- [ ] User reviews and ratings
- [ ] Inventory management
- [ ] Email notifications

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request


## 👨‍💻 Author

**Rohit Lavate**
- GitHub: [@rohitlavate97](https://github.com/rohitlavate97)

## 📞 Support

If you have any questions or need support, please:
1. Check the [Issues](https://github.com/rohitlavate97/E-OnlineBooksWorldStore/issues) page
2. Create a new issue if your problem isn't already addressed
3. Contact the maintainer

## 🔄 Version History

- **v0.0.1-SNAPSHOT** - Initial release with user registration functionality

---

**Happy Coding! 📚✨**
