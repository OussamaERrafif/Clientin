# Deployment Guide

## Table of Contents
- [Prerequisites](#prerequisites)
- [Development Environment](#development-environment)
- [Production Deployment](#production-deployment)
- [Docker Deployment](#docker-deployment)
- [Cloud Deployment](#cloud-deployment)
- [Environment Configuration](#environment-configuration)
- [Monitoring and Health Checks](#monitoring-and-health-checks)
- [Backup and Recovery](#backup-and-recovery)

## Prerequisites

### System Requirements
- **CPU**: Minimum 2 cores, Recommended 4+ cores
- **RAM**: Minimum 4GB, Recommended 8GB+
- **Storage**: Minimum 20GB, Recommended 50GB+
- **OS**: Linux (Ubuntu 20.04+), Windows 10+, macOS 10.15+

### Software Requirements
- **Java**: OpenJDK 17 or Oracle JDK 17
- **Node.js**: Version 18.x or 20.x
- **Database**: PostgreSQL 13+, MySQL 8.0+, or H2 (development)
- **Web Server**: Nginx (recommended for production)
- **Process Manager**: PM2 (for Node.js), systemd (for Java)

## Development Environment

### Local Development Setup

1. **Backend Development**:
```bash
cd Backend
./mvnw spring-boot:run -Dspring.profiles.active=dev
```

2. **Frontend Development**:
```bash
cd Front
pnpm dev
```

3. **Database Setup**:
```sql
-- PostgreSQL
CREATE DATABASE clientin_dev;
CREATE USER clientin_user WITH PASSWORD 'dev_password';
GRANT ALL PRIVILEGES ON DATABASE clientin_dev TO clientin_user;
```

### Development Configuration

**Backend** (`application-dev.properties`):
```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/clientin_dev
spring.datasource.username=clientin_user
spring.datasource.password=dev_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Logging
logging.level.com.Clientin.Clientin=DEBUG
logging.level.org.springframework.web=DEBUG

# Security (relaxed for development)
spring.security.csrf.enabled=false
```

**Frontend** (`.env.local`):
```env
NEXT_PUBLIC_API_URL=http://localhost:8080/api/v1
NEXT_PUBLIC_ENV=development
NEXT_PUBLIC_LOG_LEVEL=debug
```

## Production Deployment

### Manual Production Deployment

#### Backend Deployment

1. **Build the application**:
```bash
cd Backend
./mvnw clean package -Dmaven.test.skip=true
```

2. **Create application user**:
```bash
sudo useradd -r -s /bin/false clientin
sudo mkdir -p /opt/clientin
sudo chown clientin:clientin /opt/clientin
```

3. **Deploy the JAR file**:
```bash
sudo cp target/Clientin-0.0.1-SNAPSHOT.jar /opt/clientin/
sudo chown clientin:clientin /opt/clientin/Clientin-0.0.1-SNAPSHOT.jar
```

4. **Create systemd service** (`/etc/systemd/system/clientin-backend.service`):
```ini
[Unit]
Description=Clientin Backend Service
After=network.target

[Service]
Type=simple
User=clientin
WorkingDirectory=/opt/clientin
ExecStart=/usr/bin/java -jar Clientin-0.0.1-SNAPSHOT.jar
Restart=always
RestartSec=10
Environment=SPRING_PROFILES_ACTIVE=production

[Install]
WantedBy=multi-user.target
```

5. **Start and enable service**:
```bash
sudo systemctl daemon-reload
sudo systemctl enable clientin-backend
sudo systemctl start clientin-backend
```

#### Frontend Deployment

1. **Build the application**:
```bash
cd Front
pnpm build
```

2. **Install PM2 globally**:
```bash
npm install -g pm2
```

3. **Create PM2 ecosystem file** (`ecosystem.config.js`):
```javascript
module.exports = {
  apps: [{
    name: 'clientin-frontend',
    script: 'node_modules/next/dist/bin/next',
    args: 'start',
    cwd: '/opt/clientin/frontend',
    instances: 'max',
    exec_mode: 'cluster',
    env: {
      NODE_ENV: 'production',
      PORT: 3000,
      NEXT_PUBLIC_API_URL: 'https://api.yourdomain.com/api/v1'
    }
  }]
}
```

4. **Deploy and start**:
```bash
pm2 start ecosystem.config.js
pm2 save
pm2 startup
```

### Nginx Configuration

**Backend Proxy** (`/etc/nginx/sites-available/clientin-api`):
```nginx
server {
    listen 80;
    server_name api.yourdomain.com;

    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```

**Frontend** (`/etc/nginx/sites-available/clientin-frontend`):
```nginx
server {
    listen 80;
    server_name yourdomain.com www.yourdomain.com;

    location / {
        proxy_pass http://localhost:3000;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }

    # Static files caching
    location /_next/static/ {
        proxy_pass http://localhost:3000;
        add_header Cache-Control "public, max-age=31536000, immutable";
    }
}
```

## Docker Deployment

### Docker Compose for Production

```yaml
version: '3.8'

services:
  database:
    image: postgres:15-alpine
    restart: always
    environment:
      POSTGRES_DB: clientin_prod
      POSTGRES_USER: clientin_user
      POSTGRES_PASSWORD: ${DB_PASSWORD}
    volumes:
      - postgres_data:/var/lib/postgresql/data
      - ./init.sql:/docker-entrypoint-initdb.d/init.sql
    ports:
      - "5432:5432"
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U clientin_user"]
      interval: 30s
      timeout: 10s
      retries: 3

  backend:
    build:
      context: ./Backend
      dockerfile: Dockerfile.prod
    restart: always
    environment:
      SPRING_PROFILES_ACTIVE: production
      DB_HOST: database
      DB_PORT: 5432
      DB_NAME: clientin_prod
      DB_USER: clientin_user
      DB_PASSWORD: ${DB_PASSWORD}
      JWT_SECRET: ${JWT_SECRET}
    ports:
      - "8080:8080"
    depends_on:
      database:
        condition: service_healthy
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8080/actuator/health"]
      interval: 30s
      timeout: 10s
      retries: 3
    volumes:
      - app_logs:/app/logs

  frontend:
    build:
      context: ./Front
      dockerfile: Dockerfile.prod
    restart: always
    environment:
      NEXT_PUBLIC_API_URL: https://api.yourdomain.com/api/v1
      NODE_ENV: production
    ports:
      - "3000:3000"
    depends_on:
      backend:
        condition: service_healthy

  nginx:
    image: nginx:alpine
    restart: always
    ports:
      - "80:80"
      - "443:443"
    volumes:
      - ./nginx.conf:/etc/nginx/nginx.conf
      - ./ssl:/etc/nginx/ssl
    depends_on:
      - frontend
      - backend

  redis:
    image: redis:alpine
    restart: always
    ports:
      - "6379:6379"
    volumes:
      - redis_data:/data

volumes:
  postgres_data:
  redis_data:
  app_logs:
```

### Production Dockerfiles

**Backend Dockerfile** (`Backend/Dockerfile.prod`):
```dockerfile
# Build stage
FROM maven:3.9-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17-jdk-alpine
RUN addgroup -g 1001 -S appgroup && \
    adduser -S appuser -u 1001 -G appgroup

WORKDIR /app
COPY --from=build /app/target/Clientin-0.0.1-SNAPSHOT.jar app.jar
COPY --chown=appuser:appgroup entrypoint.sh ./

RUN chmod +x entrypoint.sh
USER appuser

EXPOSE 8080
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["./entrypoint.sh"]
```

**Frontend Dockerfile** (`Front/Dockerfile.prod`):
```dockerfile
# Dependencies stage
FROM node:18-alpine AS deps
WORKDIR /app
COPY package*.json ./
RUN npm ci --only=production

# Build stage
FROM node:18-alpine AS builder
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build

# Runtime stage
FROM node:18-alpine AS runner
RUN addgroup -g 1001 -S nodejs
RUN adduser -S nextjs -u 1001

WORKDIR /app
COPY --from=builder /app/public ./public
COPY --from=builder --chown=nextjs:nodejs /app/.next/standalone ./
COPY --from=builder --chown=nextjs:nodejs /app/.next/static ./.next/static

USER nextjs
EXPOSE 3000
ENV PORT 3000

CMD ["node", "server.js"]
```

### Docker Health Checks

**Backend Health Check Script** (`Backend/entrypoint.sh`):
```bash
#!/bin/sh
# Wait for database to be ready
until nc -z ${DB_HOST:-database} ${DB_PORT:-5432}; do
  echo "Waiting for database..."
  sleep 2
done

echo "Database is ready, starting application..."
exec java -jar app.jar
```

## Cloud Deployment

### AWS Deployment

#### Using AWS ECS

1. **Create ECS Cluster**:
```bash
aws ecs create-cluster --cluster-name clientin-cluster
```

2. **Create Task Definition** (`ecs-task-definition.json`):
```json
{
  "family": "clientin-app",
  "networkMode": "awsvpc",
  "requiresCompatibilities": ["FARGATE"],
  "cpu": "1024",
  "memory": "2048",
  "executionRoleArn": "arn:aws:iam::ACCOUNT:role/ecsTaskExecutionRole",
  "containerDefinitions": [
    {
      "name": "clientin-backend",
      "image": "your-ecr-repo/clientin-backend:latest",
      "portMappings": [
        {
          "containerPort": 8080,
          "protocol": "tcp"
        }
      ],
      "environment": [
        {
          "name": "SPRING_PROFILES_ACTIVE",
          "value": "production"
        }
      ],
      "logConfiguration": {
        "logDriver": "awslogs",
        "options": {
          "awslogs-group": "/ecs/clientin",
          "awslogs-region": "us-east-1",
          "awslogs-stream-prefix": "ecs"
        }
      }
    }
  ]
}
```

#### Using AWS Lambda (Serverless)

**Backend with AWS Lambda**:
1. Use AWS Lambda Web Adapter
2. Package Spring Boot application
3. Deploy using SAM or CDK

### Google Cloud Platform

#### Using Cloud Run

**Deploy Backend**:
```bash
# Build and push to GCR
gcloud builds submit --tag gcr.io/PROJECT-ID/clientin-backend

# Deploy to Cloud Run
gcloud run deploy clientin-backend \
  --image gcr.io/PROJECT-ID/clientin-backend \
  --platform managed \
  --region us-central1 \
  --allow-unauthenticated
```

**Deploy Frontend**:
```bash
# Build and deploy to Firebase Hosting
npm install -g firebase-tools
firebase login
firebase init hosting
npm run build
firebase deploy
```

### Azure Deployment

#### Using Azure Container Instances

```bash
# Create resource group
az group create --name clientin-rg --location eastus

# Deploy container
az container create \
  --resource-group clientin-rg \
  --name clientin-backend \
  --image your-registry/clientin-backend:latest \
  --port 8080 \
  --environment-variables SPRING_PROFILES_ACTIVE=production
```

## Environment Configuration

### Production Environment Variables

**Backend** (`.env.production`):
```bash
# Database Configuration
DB_HOST=your-db-host
DB_PORT=5432
DB_NAME=clientin_prod
DB_USER=clientin_user
DB_PASSWORD=secure_password

# Security
JWT_SECRET=your-jwt-secret-key
CORS_ALLOWED_ORIGINS=https://yourdomain.com

# External Services
SMTP_HOST=smtp.gmail.com
SMTP_PORT=587
SMTP_USER=your-email@gmail.com
SMTP_PASSWORD=your-app-password

# Monitoring
SENTRY_DSN=your-sentry-dsn
NEW_RELIC_LICENSE_KEY=your-newrelic-key

# Storage
AWS_ACCESS_KEY_ID=your-aws-access-key
AWS_SECRET_ACCESS_KEY=your-aws-secret-key
AWS_S3_BUCKET=your-s3-bucket
```

**Frontend** (`.env.production`):
```bash
NEXT_PUBLIC_API_URL=https://api.yourdomain.com/api/v1
NEXT_PUBLIC_APP_NAME=Clientin
NEXT_PUBLIC_SENTRY_DSN=your-frontend-sentry-dsn
NEXT_PUBLIC_GOOGLE_ANALYTICS_ID=your-ga-id
```

### SSL/TLS Configuration

**Let's Encrypt with Certbot**:
```bash
# Install certbot
sudo apt install certbot python3-certbot-nginx

# Get certificate
sudo certbot --nginx -d yourdomain.com -d www.yourdomain.com

# Auto-renewal
sudo crontab -e
# Add: 0 12 * * * /usr/bin/certbot renew --quiet
```

## Monitoring and Health Checks

### Application Monitoring

**Backend Health Endpoints**:
```properties
# application.properties
management.endpoints.web.exposure.include=health,info,metrics,prometheus
management.endpoint.health.show-details=always
management.health.db.enabled=true
management.health.redis.enabled=true
```

**Health Check URLs**:
- Backend: `http://localhost:8080/actuator/health`
- Frontend: Custom health check endpoint

### Logging Configuration

**Backend Logging** (`logback-spring.xml`):
```xml
<configuration>
    <springProfile name="production">
        <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
            <file>/var/log/clientin/application.log</file>
            <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
                <fileNamePattern>/var/log/clientin/application.%d{yyyy-MM-dd}.log</fileNamePattern>
                <maxHistory>30</maxHistory>
            </rollingPolicy>
            <encoder>
                <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
            </encoder>
        </appender>
        <root level="INFO">
            <appender-ref ref="FILE" />
        </root>
    </springProfile>
</configuration>
```

### Monitoring Stack

**Prometheus Configuration** (`prometheus.yml`):
```yaml
global:
  scrape_interval: 15s

scrape_configs:
  - job_name: 'clientin-backend'
    static_configs:
      - targets: ['localhost:8080']
    metrics_path: '/actuator/prometheus'
```

**Grafana Dashboard**: Import dashboard ID 4701 for Spring Boot monitoring

## Backup and Recovery

### Database Backup

**Automated PostgreSQL Backup**:
```bash
#!/bin/bash
# backup.sh
BACKUP_DIR="/backup/clientin"
DB_NAME="clientin_prod"
DB_USER="clientin_user"
DATE=$(date +%Y%m%d_%H%M%S)

mkdir -p $BACKUP_DIR
pg_dump -U $DB_USER -h localhost $DB_NAME | gzip > $BACKUP_DIR/backup_$DATE.sql.gz

# Keep only last 7 days
find $BACKUP_DIR -name "backup_*.sql.gz" -mtime +7 -delete
```

**Cron Job for Daily Backup**:
```bash
# Add to crontab
0 2 * * * /opt/scripts/backup.sh
```

### Application Data Backup

**File System Backup**:
```bash
#!/bin/bash
# Backup application files and logs
tar -czf /backup/clientin/app_backup_$(date +%Y%m%d).tar.gz \
  /opt/clientin \
  /var/log/clientin \
  --exclude="*.tmp"
```

### Disaster Recovery

**Recovery Procedure**:
1. Restore database from backup
2. Deploy application from version control
3. Restore configuration files
4. Verify all services are running
5. Run health checks

**Recovery Time Objective (RTO)**: < 4 hours
**Recovery Point Objective (RPO)**: < 1 hour

## Security Considerations

### Production Security Checklist

- [ ] Change default passwords
- [ ] Enable SSL/TLS
- [ ] Configure firewall rules
- [ ] Enable audit logging
- [ ] Regular security updates
- [ ] Database encryption at rest
- [ ] Secure session configuration
- [ ] Input validation and sanitization
- [ ] Rate limiting
- [ ] CORS configuration

### Network Security

**Firewall Rules** (UFW):
```bash
# Allow SSH
sudo ufw allow 22

# Allow HTTP/HTTPS
sudo ufw allow 80
sudo ufw allow 443

# Allow database (only from app server)
sudo ufw allow from APP_SERVER_IP to any port 5432

# Enable firewall
sudo ufw enable
```

## Performance Optimization

### Backend Optimization

1. **JVM Tuning**:
```bash
java -jar app.jar \
  -Xmx2g \
  -Xms1g \
  -XX:+UseG1GC \
  -XX:MaxGCPauseMillis=200
```

2. **Database Connection Pooling**:
```properties
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.idle-timeout=300000
```

### Frontend Optimization

1. **Next.js Configuration** (`next.config.js`):
```javascript
/** @type {import('next').NextConfig} */
const nextConfig = {
  compress: true,
  images: {
    domains: ['your-cdn-domain.com'],
  },
  experimental: {
    optimizeCss: true,
  },
}

module.exports = nextConfig
```

2. **CDN Configuration**: Use CloudFlare or AWS CloudFront for static assets

## Troubleshooting Production Issues

### Common Production Issues

1. **High Memory Usage**:
   - Check for memory leaks
   - Analyze heap dumps
   - Adjust JVM settings

2. **Database Connection Issues**:
   - Check connection pool settings
   - Monitor active connections
   - Verify database server health

3. **Slow Response Times**:
   - Enable SQL query logging
   - Check database indexes
   - Monitor API response times

### Debugging Tools

- **Backend**: JProfiler, VisualVM, Spring Boot Admin
- **Frontend**: Chrome DevTools, Lighthouse, Web Vitals
- **Database**: pgAdmin, query analyzers
- **Infrastructure**: htop, iostat, netstat

This completes the comprehensive deployment guide for the Clientin application.
