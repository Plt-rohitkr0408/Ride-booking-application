# 🚕 Ride Booking Application - Microservices Platform

A scalable, fault-tolerant ride booking backend built with Spring Boot Microservices, similar to Ola/Uber.

### 🏗️ Architecture
[Auth Service] -> [User Service] + [Driver Service]
[API Gateway] -> [Ride Service] -> [Order Service] -> [Payment + Notification Service]
Service Discovery: Eureka | Async: Kafka | Cache: Redis

### 🛠️ Tech Stack
- Java 17, Spring Boot 3, Spring Cloud (Eureka, Gateway, OpenFeign, Load Balancer)
- Security: Spring Security + JWT + RBAC (USER, DRIVER, ADMIN)
- Async: Apache Kafka, Zookeeper for Order & Notification events
- Resilience: Resilience4j Circuit Breaker with Fallback
- DB: MySQL per service | Cache: Redis | Mail: Spring Mail
- Tools: Docker Compose, Swagger/OpenAPI, Maven

### ✨ Key Features
- Secure inter-service communication via OpenFeign Request Interceptor (JWT propagation)
- 100% secured User/Driver endpoints, only ADMIN can access all users/drivers
- Async order creation via Kafka, async email notifications (created/completed/cancelled)
- Circuit Breaker with fallback to Redis cache to ensure 99% availability
- Docker Compose for Kafka, Zookeeper, Redis - run all 6 services with one command

### 🚀 How to Run
docker-compose up -d
mvn spring-boot:run for each service

### 📸 Swagger Screenshots
[Add 3-4 screenshots here]

### 🔗 Author
Rohit Kumar Pandit - Java Backend Developer
LinkedIn: [your link]
