EasyLinkBackEnd
Backend for EasyLink — a digital identity and smart sharing platform.
This service manages user authentication, Vibe profiles, and data sharing.

🧱 Architecture
Currently implemented as a monolith, combining:

auth-service: Handles authentication and JWT generation (RS256)

vibe-service: Embedded module responsible for Vibe profiles, subscriptions, and smart sharing features

vibe-service is developed with Clean Architecture and SOLID principles to enable future extraction into a standalone microservice.

🛠️ Tech Stack
Java 21

Spring Boot 3

Spring Security (JWT + RSA)

PostgreSQL

Flyway

Docker

MapStruct

Swagger/OpenAPI

✨ Features
Secure login/registration with RSA-based JWT tokens

Vibe profile creation with customizable fields (phone, email, links, etc.)

Business subscription system

QR-based smart sharing

Clean modular code with domain separation

RESTful API with Swagger docs

📁 Structure Overview
bash
Копировать
Редактировать
easylink-backend/
├── src/
│   ├── auth/             # Authentication logic and token handling
│   └── vibe/             # Vibe logic (Clean Architecture)
├── resources/
│   └── db/migration/     # Flyway migrations
├── Dockerfile
├── docker-compose.yml
└── README.md

🚀 Getting Started
bash
Копировать
Редактировать
docker-compose up --build
App: http://localhost:8080

Swagger: http://localhost:8080/swagger-ui.html

🔐 Security
JWT-based authentication (RS256)

Public/private RSA keys for signing and verifying tokens

Role-based authorization