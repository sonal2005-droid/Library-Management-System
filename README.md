# Library Management System

A production-style REST API built with Spring Boot.

## Features

### Core API
- Book CRUD operations
- Search and filtering
- Member management
- Borrow and return workflow
- Fine calculation (₹5/day)

### Security
- JWT Authentication
- Role-based Authorization (ADMIN/MEMBER)
- Protected endpoints

### Professional Features
- Global Exception Handling
- DTO Validation
- Layered Architecture
- Standardized Error Responses

### Documentation
- Swagger UI
- OpenAPI 3.1
- Spring Boot Actuator

## Tech Stack
- Java 17
- Spring Boot
- Spring Security
- JWT
- Maven
- MySQL
- Swagger/OpenAPI

## Architecture
Controller → Service → Repository → Database

## Run Locally
1. Clone repository
2. Configure MySQL
3. Run:
   mvn spring-boot:run
