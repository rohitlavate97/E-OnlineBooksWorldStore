# Deployment Guide

## Overview
This guide provides instructions for deploying the E-OnlineBooksWorldStore application to various environments.

## Prerequisites
- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6.x or higher
- Server environment (Linux/Windows)
- SSL certificate for HTTPS

## Environment Setup

### 1. Database Setup
```sql
CREATE DATABASE ebooksstore;
CREATE USER 'ebooksstore_user'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON ebooksstore.* TO 'ebooksstore_user'@'localhost';
FLUSH PRIVILEGES;
```

### 2. Application Properties
Create environment-specific properties files:
- `application-dev.properties`
- `application-staging.properties`
- `application-prod.properties`

Example production configuration:
```properties
# Application Configuration
spring.application.name=E-OnlineBooksWorldStore
server.port=7070

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/ebooksstore?createDatabaseIfNotExist=true
spring.datasource.username=ebooksstore_user
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# Logging Configuration
logging.level.root=INFO
logging.file.name=/var/log/ebooksstore/application.log

# Security Configuration (if implemented)
#spring.security.user.name=admin
#spring.security.user.password=secure_password

# SSL Configuration (for production)
#server.ssl.key-store=/path/to/keystore.p12
#server.ssl.key-store-password=keystore_password
#server.ssl.keyStoreType=PKCS12
```

## Build Process

### 1. Create Production Build
```bash
# Clean and package
mvn clean package -DskipTests

# Create production build with specific profile
mvn clean package -P prod -DskipTests
```

### 2. Verify Build
```bash
java -jar target/E-OnlineBooksWorldStore-1.0.0.jar --version
```

## Deployment Steps

### 1. Manual Deployment

#### Pre-deployment Checklist
- [ ] Database backup
- [ ] Configuration files ready
- [ ] SSL certificates in place
- [ ] Firewall rules configured
- [ ] Adequate disk space

#### Deployment Process
1. Stop existing application:
```bash
sudo systemctl stop bookstore
```

2. Copy new JAR file:
```bash
sudo cp target/E-OnlineBooksWorldStore-1.0.0.jar /opt/bookstore/
```

3. Update configuration:
```bash
sudo cp application-prod.properties /opt/bookstore/config/
```

4. Start application:
```bash
sudo systemctl start bookstore
```

### 2. Docker Deployment

#### Build Docker Image
```bash
docker build -t bookstore:1.0.0 .
```

#### Run Container
```bash
docker run -d \
  --name bookstore \
  -p 7070:7070 \
  -v /opt/bookstore/config:/config \
  -v /opt/bookstore/logs:/logs \
  bookstore:1.0.0
```

### 3. Kubernetes Deployment

#### Apply Configurations
```bash
kubectl apply -f k8s/namespace.yaml
kubectl apply -f k8s/config.yaml
kubectl apply -f k8s/secrets.yaml
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml
```

## Monitoring & Maintenance

### 1. Health Checks
- Monitor application health endpoint: `/actuator/health`
- Check system metrics: `/actuator/metrics`
- Monitor memory usage: `/actuator/metrics/jvm.memory.used`

### 2. Logging
```bash
# View application logs
tail -f /var/log/bookstore/application.log

# Monitor error logs
grep ERROR /var/log/bookstore/application.log
```

### 3. Backup Procedures
```bash
# Database backup
mysqldump -u bookstore_user -p bookstore > backup.sql

# Configuration backup
cp -r /opt/bookstore/config /opt/backup/config
```

## Security Considerations

### 1. SSL Configuration
- Enable HTTPS only
- Configure SSL certificates
- Implement HTTP to HTTPS redirect

### 2. Database Security
- Use strong passwords
- Limit database user permissions
- Enable SSL for database connections

### 3. Application Security
- Enable CORS protection
- Set secure headers
- Implement rate limiting
- Enable audit logging

## Rollback Procedures

### 1. Application Rollback
```bash
# Stop current version
sudo systemctl stop bookstore

# Restore previous version
sudo cp /opt/backup/E-OnlineBooksWorldStore-previous.jar /opt/bookstore/current.jar

# Start previous version
sudo systemctl start bookstore
```

### 2. Database Rollback
```bash
# Restore database backup
mysql -u bookstore_user -p bookstore < backup.sql
```

## Troubleshooting

### Common Issues

1. **Application Won't Start**
   - Check logs in `/var/log/bookstore/`
   - Verify database connectivity
   - Check port availability

2. **Database Connection Issues**
   - Verify database credentials
   - Check network connectivity
   - Validate database permissions

3. **Memory Issues**
   - Check JVM heap settings
   - Monitor memory usage
   - Analyze garbage collection logs

## Performance Tuning

### 1. JVM Settings
```bash
JAVA_OPTS="-Xms2g -Xmx4g -XX:+UseG1GC"
```

### 2. Database Optimization
- Index frequently queried columns
- Optimize slow queries
- Configure connection pool

### 3. Application Settings
- Enable caching
- Configure thread pool size
- Optimize logging levels

## Support Contacts

- **Technical Issues:** tech-support@example.com
- **Database Issues:** dba@example.com
- **Security Issues:** security@example.com

## References

- [Spring Boot Deployment Guide](https://docs.spring.io/spring-boot/docs/current/reference/html/deployment.html)
- [MySQL Documentation](https://dev.mysql.com/doc/)
- [Docker Documentation](https://docs.docker.com/)
