# Deployment Guide

## 🚀 Overview

This guide covers deploying the E-OnlineBooksWorldStore application to various environments, from development to production.

## 📋 Prerequisites

### Required Software
- **Java 17** or higher
- **Maven 3.6+**
- **MySQL 8.0+**
- **Git** (for version control)
- **Docker** (optional, for containerized deployment)

### Production Requirements
- **Server**: Linux/Unix-based system (Ubuntu 20.04+ recommended)
- **Memory**: Minimum 2GB RAM, 4GB+ recommended
- **Storage**: Minimum 20GB free space
- **Network**: HTTPS support with SSL certificates

## 🔧 Environment Configuration

### Development Environment
```properties
# application-dev.properties
spring.profiles.active=dev
server.port=7070
spring.datasource.url=jdbc:mysql://localhost:3306/ebooksstore_dev
spring.datasource.username=dev_user
spring.datasource.password=dev_password
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
logging.level.com.onlineboostore=DEBUG
```

### Staging Environment
```properties
# application-staging.properties
spring.profiles.active=staging
server.port=8080
spring.datasource.url=jdbc:mysql://staging-db:3306/ebooksstore_staging
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
logging.level.com.onlineboostore=INFO
```

### Production Environment
```properties
# application-prod.properties
spring.profiles.active=prod
server.port=8080
spring.datasource.url=jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
logging.level.com.onlineboostore=WARN
logging.file.name=/var/log/ebooksstore/application.log
logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n
```

## 🏗️ Build Process

### 1. Local Build
```bash
# Clean and compile
mvn clean compile

# Run tests
mvn test

# Package application
mvn package -DskipTests
```

### 2. Production Build
```bash
# Build with production profile
mvn clean package -Pprod -DskipTests

# Verify JAR file
ls -la target/E-OnlineBooksWorldStore-*.jar
```

### 3. Build with Different Profiles
```bash
# Development build
mvn clean package -Pdev

# Staging build
mvn clean package -Pstaging

# Production build
mvn clean package -Pprod
```

## 🐳 Docker Deployment

### 1. Create Dockerfile
```dockerfile
# Use OpenJDK 17 slim image
FROM openjdk:17-jre-slim

# Set working directory
WORKDIR /app

# Copy JAR file
COPY target/E-OnlineBooksWorldStore-*.jar app.jar

# Create non-root user
RUN addgroup --system javauser && adduser --system --ingroup javauser javauser
USER javauser

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### 2. Build Docker Image
```bash
# Build image
docker build -t ebooksstore:latest .

# Tag for registry
docker tag ebooksstore:latest your-registry/ebooksstore:latest
```

### 3. Run Docker Container
```bash
# Run with environment variables
docker run -d \
  --name ebooksstore \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e DB_HOST=your-db-host \
  -e DB_USERNAME=your-username \
  -e DB_PASSWORD=your-password \
  ebooksstore:latest
```

### 4. Docker Compose
```yaml
# docker-compose.yml
version: '3.8'

services:
  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=prod
      - DB_HOST=db
      - DB_USERNAME=${DB_USERNAME}
      - DB_PASSWORD=${DB_PASSWORD}
    depends_on:
      - db
    restart: unless-stopped

  db:
    image: mysql:8.0
    environment:
      - MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD}
      - MYSQL_DATABASE=${MYSQL_DATABASE}
      - MYSQL_USER=${MYSQL_USER}
      - MYSQL_PASSWORD=${MYSQL_PASSWORD}
    volumes:
      - mysql_data:/var/lib/mysql
    ports:
      - "3306:3306"
    restart: unless-stopped

volumes:
  mysql_data:
```

## ☁️ Cloud Deployment

### AWS Deployment

#### 1. EC2 Deployment
```bash
# Connect to EC2 instance
ssh -i your-key.pem ubuntu@your-ec2-ip

# Install Java and Maven
sudo apt update
sudo apt install openjdk-17-jdk maven

# Clone repository
git clone https://github.com/rohitlavate97/E-OnlineBooksWorldStore.git
cd E-OnlineBooksWorldStore

# Build and run
mvn clean package -Pprod
java -jar target/E-OnlineBooksWorldStore-*.jar
```

#### 2. AWS RDS Setup
```sql
-- Create production database
CREATE DATABASE ebooksstore_prod;

-- Create user with limited privileges
CREATE USER 'ebooksstore_user'@'%' IDENTIFIED BY 'secure_password';
GRANT SELECT, INSERT, UPDATE, DELETE ON ebooksstore_prod.* TO 'ebooksstore_user'@'%';
FLUSH PRIVILEGES;
```

#### 3. AWS Elastic Beanstalk
```yaml
# .ebextensions/01_environment.config
option_settings:
  aws:elasticbeanstalk:application:environment:
    SPRING_PROFILES_ACTIVE: prod
    DB_HOST: your-rds-endpoint
    DB_USERNAME: your-username
    DB_PASSWORD: your-password
  aws:elasticbeanstalk:container:java:
    Xms: 512m
    Xmx: 1024m
```

### Google Cloud Platform

#### 1. App Engine
```yaml
# app.yaml
runtime: java17
service: ebooksstore

env_variables:
  SPRING_PROFILES_ACTIVE: "prod"
  DB_HOST: "your-cloud-sql-instance"
  DB_USERNAME: "your-username"
  DB_PASSWORD: "your-password"

automatic_scaling:
  target_cpu_utilization: 0.6
  min_instances: 1
  max_instances: 10
```

#### 2. Cloud Run
```bash
# Build and deploy
gcloud builds submit --tag gcr.io/your-project/ebooksstore
gcloud run deploy ebooksstore \
  --image gcr.io/your-project/ebooksstore \
  --platform managed \
  --region us-central1 \
  --allow-unauthenticated
```

## 🔒 Security Configuration

### 1. SSL/TLS Setup
```properties
# application-prod.properties
server.ssl.enabled=true
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=${KEYSTORE_PASSWORD}
server.ssl.key-store-type=PKCS12
server.ssl.key-alias=tomcat
```

### 2. Security Headers
```java
@Configuration
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .headers()
                .frameOptions().deny()
                .contentTypeOptions()
                .and()
                .httpStrictTransportSecurity()
                .and()
                .xssProtection()
                .and()
                .contentSecurityPolicy("default-src 'self'");
        
        return http.build();
    }
}
```

### 3. Environment Variables
```bash
# .env file (never commit to version control)
DB_HOST=your-database-host
DB_USERNAME=your-database-username
DB_PASSWORD=your-secure-password
KEYSTORE_PASSWORD=your-keystore-password
JWT_SECRET=your-jwt-secret-key
```

## 📊 Monitoring and Logging

### 1. Application Monitoring
```xml
<!-- pom.xml -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```

### 2. Logging Configuration
```xml
<!-- logback-spring.xml -->
<configuration>
    <springProfile name="prod">
        <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
            <file>/var/log/ebooksstore/application.log</file>
            <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
                <fileNamePattern>/var/log/ebooksstore/application.%d{yyyy-MM-dd}.log</fileNamePattern>
                <maxHistory>30</maxHistory>
            </rollingPolicy>
            <encoder>
                <pattern>%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n</pattern>
            </encoder>
        </appender>
        
        <root level="INFO">
            <appender-ref ref="FILE" />
        </root>
    </springProfile>
</configuration>
```

### 3. Health Checks
```java
@Component
public class DatabaseHealthIndicator implements HealthIndicator {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public Health health() {
        try {
            userRepository.count();
            return Health.up().build();
        } catch (Exception e) {
            return Health.down()
                .withException(e)
                .build();
        }
    }
}
```

## 🔄 CI/CD Pipeline

### GitHub Actions
```yaml
# .github/workflows/deploy.yml
name: Deploy to Production

on:
  push:
    branches: [ main ]

jobs:
  deploy:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v3
    
    - name: Set up JDK 17
      uses: actions/setup-java@v3
      with:
        java-version: '17'
        distribution: 'temurin'
    
    - name: Build with Maven
      run: mvn clean package -Pprod -DskipTests
    
    - name: Build Docker image
      run: docker build -t ebooksstore:${{ github.sha }} .
    
    - name: Deploy to server
      run: |
        # Add your deployment commands here
        echo "Deploying to production..."
```

### Jenkins Pipeline
```groovy
// Jenkinsfile
pipeline {
    agent any
    
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package -Pprod -DskipTests'
            }
        }
        
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        
        stage('Deploy') {
            steps {
                sh 'docker build -t ebooksstore:latest .'
                sh 'docker stop ebooksstore || true'
                sh 'docker rm ebooksstore || true'
                sh 'docker run -d --name ebooksstore -p 8080:8080 ebooksstore:latest'
            }
        }
    }
}
```

## 🚨 Troubleshooting

### Common Issues

#### 1. Port Already in Use
```bash
# Check what's using the port
sudo netstat -tulpn | grep :8080

# Kill the process
sudo kill -9 <PID>
```

#### 2. Database Connection Issues
```bash
# Test database connectivity
mysql -h your-host -u your-username -p -e "SELECT 1;"

# Check application logs
tail -f /var/log/ebooksstore/application.log
```

#### 3. Memory Issues
```bash
# Check memory usage
free -h

# Increase JVM memory
java -Xmx2g -Xms1g -jar app.jar
```

### Performance Tuning
```properties
# JVM tuning
JAVA_OPTS="-Xms1g -Xmx2g -XX:+UseG1GC -XX:MaxGCPauseMillis=200"

# Database connection pool
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=30000
```

## 📋 Deployment Checklist

### Pre-Deployment
- [ ] All tests passing
- [ ] Code review completed
- [ ] Security scan passed
- [ ] Performance testing completed
- [ ] Database migration scripts ready
- [ ] Rollback plan prepared

### Deployment
- [ ] Backup current version
- [ ] Deploy new version
- [ ] Verify health checks
- [ ] Run smoke tests
- [ ] Monitor application metrics
- [ ] Update documentation

### Post-Deployment
- [ ] Verify all functionality
- [ ] Monitor error rates
- [ ] Check performance metrics
- [ ] Update deployment log
- [ ] Notify stakeholders

## 📚 Additional Resources

- [Spring Boot Deployment](https://docs.spring.io/spring-boot/docs/current/reference/html/deployment.html)
- [Docker Best Practices](https://docs.docker.com/develop/dev-best-practices/)
- [AWS Deployment Guide](https://aws.amazon.com/getting-started/)
- [Google Cloud Deployment](https://cloud.google.com/docs/deployment)

---

**For deployment support, please refer to the main [README.md](README.md) or create an issue in the repository.**
