# Deployment Guide

## Overview
This guide provides comprehensive instructions for deploying the E-OnlineBooksWorldStore application in various environments.

## Environments

### 1. Development
- Local development environment
- Debugging enabled
- H2 in-memory database option available
- Hot reload enabled

### 2. Staging
- Pre-production testing
- Production-like configuration
- Separate database instance
- Limited resources

### 3. Production
- Live environment
- Optimized performance
- High availability setup
- Proper security measures

## Prerequisites
- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6.x or higher
- Server environment (Linux/Windows)
- SSL certificate for HTTPS
- Minimum 2GB RAM
- 10GB disk space

## Pre-deployment Checklist

### 1. Environment Verification
- [ ] Java version check
- [ ] Maven installation
- [ ] Database backup
- [ ] SSL certificates
- [ ] Firewall configuration
- [ ] Disk space verification
- [ ] System dependencies

### 2. Configuration Files
- [ ] Database credentials
- [ ] Application properties
- [ ] Logging configuration
- [ ] SSL configuration
- [ ] Environment variables

### 3. Security Measures
- [ ] SSL/TLS setup
- [ ] Firewall rules
- [ ] Database access restrictions
- [ ] Application user permissions
- [ ] Backup strategy

## Deployment Steps

### 1. Database Setup

```sql
-- Create Database
CREATE DATABASE ebooksstore;

-- Create User
CREATE USER 'ebooksstore_user'@'localhost' IDENTIFIED BY 'your_secure_password';

-- Grant Privileges
GRANT ALL PRIVILEGES ON ebooksstore.* TO 'ebooksstore_user'@'localhost';
FLUSH PRIVILEGES;

-- Verify
SHOW GRANTS FOR 'ebooksstore_user'@'localhost';
```

### 2. Application Properties

Create environment-specific properties:

#### Development (application-dev.properties)
```properties
# Application
spring.application.name=E-OnlineBooksWorldStore
server.port=7070

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/ebooksstore?createDatabaseIfNotExist=true
spring.datasource.username=ebooksstore_user
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Logging
logging.level.root=INFO
logging.level.com.onlinebookstore=DEBUG
```

#### Production (application-prod.properties)
```properties
# Application
spring.application.name=E-OnlineBooksWorldStore
server.port=7070

# Database
spring.datasource.url=jdbc:mysql://prod-db-server:3306/ebooksstore
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=false

# Logging
logging.level.root=WARN
logging.level.com.onlinebookstore=INFO
logging.file.name=/var/log/ebooksstore/application.log

# SSL Configuration
server.ssl.key-store=/etc/ssl/ebooksstore.p12
server.ssl.key-store-password=${SSL_PASSWORD}
server.ssl.keyStoreType=PKCS12
```

### 3. Build Process

```bash
# Clean and package
mvn clean package -DskipTests

# Build for specific environment
mvn clean package -P prod -DskipTests
```

### 4. Deployment Process

#### Manual Deployment

1. Stop existing application:
```bash
sudo systemctl stop ebooksstore
```

2. Backup current version:
```bash
cp /opt/ebooksstore/current/E-OnlineBooksWorldStore.jar /opt/ebooksstore/backup/E-OnlineBooksWorldStore-$(date +%Y%m%d).jar
```

3. Deploy new version:
```bash
cp target/E-OnlineBooksWorldStore-*.jar /opt/ebooksstore/current/E-OnlineBooksWorldStore.jar
```

4. Update configuration:
```bash
cp config/application-prod.properties /opt/ebooksstore/config/
```

5. Start application:
```bash
sudo systemctl start ebooksstore
```

#### Systemd Service Configuration

Create `/etc/systemd/system/ebooksstore.service`:
```ini
[Unit]
Description=E-Online Books World Store
After=mysql.service

[Service]
User=ebooksstore
ExecStart=/usr/bin/java -jar /opt/ebooksstore/current/E-OnlineBooksWorldStore.jar
EnvironmentFile=/opt/ebooksstore/config/env
WorkingDirectory=/opt/ebooksstore/current
SuccessExitStatus=143
TimeoutStopSec=10
Restart=on-failure
RestartSec=5

[Install]
WantedBy=multi-user.target
```

### 5. Post-deployment Verification

1. Check application status:
```bash
sudo systemctl status ebooksstore
```

2. Verify logs:
```bash
tail -f /var/log/ebooksstore/application.log
```

3. Test endpoints:
```bash
curl -k https://localhost:7070/actuator/health
```

## Monitoring

### 1. Application Health
- Configure Spring Boot Actuator endpoints
- Set up health checks
- Monitor memory usage
- Track response times

### 2. Logging
- Centralized logging setup
- Log rotation policy
- Error alerting
- Performance monitoring

### 3. Metrics
- JVM metrics
- Database connection pool
- API endpoint metrics
- Custom business metrics

## Backup Strategy

### 1. Database Backup
```bash
#!/bin/bash
TIMESTAMP=$(date +%Y%m%d_%H%M%S)
mysqldump -u ebooksstore_user -p ebooksstore > /backup/db/ebooksstore_${TIMESTAMP}.sql
```

### 2. Application Backup
```bash
#!/bin/bash
TIMESTAMP=$(date +%Y%m%d_%H%M%S)
tar -czf /backup/app/ebooksstore_${TIMESTAMP}.tar.gz /opt/ebooksstore/
```

## Rollback Procedure

### 1. Application Rollback
```bash
# Stop current version
sudo systemctl stop ebooksstore

# Restore previous version
cp /opt/ebooksstore/backup/E-OnlineBooksWorldStore-previous.jar /opt/ebooksstore/current/E-OnlineBooksWorldStore.jar

# Start service
sudo systemctl start ebooksstore
```

### 2. Database Rollback
```bash
mysql -u ebooksstore_user -p ebooksstore < /backup/db/ebooksstore_previous.sql
```

## Troubleshooting

### Common Issues

1. Application won't start
- Check Java version
- Verify database connectivity
- Check logs for errors
- Verify memory allocation

2. Database connection issues
- Check credentials
- Verify network connectivity
- Check database service status
- Verify connection pool settings

3. Performance issues
- Monitor memory usage
- Check database queries
- Verify connection pool size
- Review thread pool configuration

## Security Considerations

### 1. Application Security
- Enable HTTPS
- Configure CORS
- Set up rate limiting
- Implement authentication
- Enable security headers

### 2. Server Security
- Configure firewall
- Update system packages
- Set up monitoring
- Configure backup system
- Implement access controls

### 3. Database Security
- Restrict network access
- Use secure passwords
- Regular security updates
- Enable audit logging
- Implement backup strategy
