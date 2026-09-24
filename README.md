# Auth Service

Auth Service is a Spring Boot service for authentication-related APIs in the Stream platform.

This repository also acts as a base service codebase for other Stream backend services. It keeps shared service foundations such as Clean Architecture package boundaries, Docker, and temporary Kafka wiring in one place so future services can copy the structure consistently.

## Tech Stack

- Java 17+
- Spring Boot 3.5.16
- Spring Kafka
- Maven
- Docker / Docker Compose
- Apache Kafka 3.9.1 for local Docker development

## Architecture

```text
com.stream.authservice
  application/
    dto/          Response/request DTOs used by use cases and adapters
    usecase/      Application use cases
  domain/
    model/        Business models and rules without Spring dependencies
  infrastructure/
    web/          HTTP controllers and web adapters
    messaging/    Messaging adapters such as Kafka producers/consumers
  config/         Spring configuration and dependency wiring
```

Current request flow:

```text
GET /api/auth/health
  -> infrastructure.web.AuthHealthController
  -> application.usecase.GetAuthHealthUseCase
  -> domain.model.ServiceHealth
  -> application.dto.AuthHealthResponse
```

## Kafka Foundation

Kafka is configured as reusable service infrastructure, but this auth service does not publish or consume domain events yet.

Local default:

```text
spring.kafka.bootstrap-servers=localhost:9092
```

Docker default:

```text
KAFKA_BOOTSTRAP_SERVERS=kafka:19092
```

When adding real events in this or another service:

- Put Kafka producers/consumers under `infrastructure/messaging/kafka`.
- Keep business decisions in `domain` or `application/usecase`.
- Let adapters translate between Kafka messages and application use case calls.
- Avoid putting auth/business logic directly inside listener methods.

## Requirements

For local development:

- Java 17 or newer
- Maven 3.6.3 or newer
- Kafka running on `localhost:9092` when testing Kafka integration locally

For containerized development:

- Docker
- Docker Compose

## Run Locally

```bash
mvn spring-boot:run
```

The service starts on port `8080` by default.

## Run With Docker

Start Kafka and the service:

```bash
docker compose up --build
```

Stop all containers:

```bash
docker compose down
```

## Run Kafka Only

```bash
docker compose up kafka
```

Use this when running the Spring Boot app locally but Kafka in Docker.

## Test

```bash
mvn test
```

If Maven or Java 17 is not installed locally, run the test suite through Docker:

```bash
docker run --rm -v ${PWD}:/app -w /app maven:3.9.9-eclipse-temurin-17 mvn -B test
```

## API

### Health Check

```http
GET /api/auth/health
```

Response:

```json
{
  "service": "auth-service",
  "status": "UP"
}
```

## Project Files

- `pom.xml`: Maven project configuration and shared dependencies
- `Dockerfile`: multi-stage Docker build that runs tests and uses a Java 17 non-root runtime
- `docker-compose.yml`: local app + Kafka orchestration
- `src/main/resources/application.yml`: Spring application and Kafka client configuration

## Notes

This service does not implement register, login, JWT, persistence, Kafka producers, or Kafka consumers yet. Those should be added as separate use cases under `application/usecase`, with domain rules under `domain` and adapters under `infrastructure`.
