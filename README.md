# Client Management System
A microservices-based system for managing clients, authentication, analytics, and scoring. The architecture demonstrates Spring Boot microservices integrated via Spring Cloud Gateway, Kafka, gRPC, and JWT-based authentication.

# Overview

| Service               | Port        | Description                                                    |
| --------------------- | ----------- | -------------------------------------------------------------- |
| **API Gateway**       | 4004        | Routes and secures requests using JWT validation.              |
| **Auth Service**      | 4005        | Handles user login, JWT generation, and validation.            |
| **Client Service**    | 4000        | Manages client entities (CRUD). Sends Kafka events on updates. |
| **Analytics Service** | 4003        | Consumes Kafka events for player analytics.                    |
| **Billing Service**   | 4001        | Handles scoring via gRPC (sample science quiz scoring).        |


# Technologies Used
* Spring Boot 3.x
* Spring Cloud Gateway
* Spring Security (JWT)
* Spring Data JPA (PostgreSQL)
* Apache Kafka
* gRPC
* Docker & Docker Compose
* OpenAPI / Swagger 3
* Maven

# Prerequisites
* Docker and Docker Compose installed
* Java 23
* Maven 3.9+
* PostgreSQL running locally or in Docker

