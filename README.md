# Spring Boot Application

## Overview
This is a Spring Boot application built with Spring Boot 3.4.3.

## Prerequisites
- Java 17+
- Docker
- Docker Compose

## Features
- Spring Boot 3.4.3
- Database Migrations with Flyway
- Actuator Endpoints
- AOP Logging

## Database Migrations
Migrations are located in `src/main/resources/db/migration`
- Flyway automatically applies migrations on application startup

## Actuator Endpoints
Available Endpoints:
- Health Check: `/actuator/health`
- Metrics: `/actuator/metrics/{property}`

## Running the Application

### Local Development
```bash
./mvnw clean spring-boot:run