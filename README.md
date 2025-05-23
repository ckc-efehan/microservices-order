# Order Service

This microservice is part of an online shop system and is responsible for handling order processing. It communicates with other services like the Inventory Service to check product availability and publishes events to notify other services about order placement.

## Technologies

- Java 21
- Spring Boot 3.4.2
- Spring Cloud 2024.0.0
- Spring Data JPA
- MySQL 8.3.0
- Flyway for database migrations
- Kafka for event messaging
- Resilience4j for circuit breaking and retry patterns
- OpenAPI/Swagger for API documentation
- TestContainers for integration testing
- Docker and Docker Compose for containerization

## Architecture

This service follows a microservice architecture pattern:

1. **REST API Layer**: Exposes endpoints for order management
2. **Service Layer**: Contains business logic for order processing
3. **Data Access Layer**: Handles database operations
4. **Client Layer**: Communicates with other services (e.g., Inventory Service)
5. **Event Publishing**: Publishes events to Kafka topics

## API Endpoints

### Place Order
- **URL**: `/api/orders`
- **Method**: POST
- **Request Body**:
  ```json
  {
    "id": null,
    "orderNumber": null,
    "skuCode": "product-123",
    "price": 99.99,
    "quantity": 2,
    "userDetails": {
      "email": "user@example.com",
      "firstName": "John",
      "lastName": "Doe"
    }
  }
  ```
- **Response**: 
  - Status: 201 CREATED
  - Body: "Order placed successfully"

## Setup and Running

### Prerequisites
- Java 21
- Docker and Docker Compose

### Running with Docker
1. Clone the repository
2. Navigate to the project directory
3. Start the infrastructure services:
   ```
   docker-compose up -d
   ```
4. Build and run the application:
   ```
   ./mvnw spring-boot:run
   ```

### Database
The service uses MySQL for data persistence. The database schema is managed by Flyway migrations.

### Kafka Topics
- **order-placed**: Events published when an order is successfully placed

## Resilience Patterns

The service implements circuit breaker and retry patterns when communicating with the Inventory Service to ensure fault tolerance.

## Testing

The project includes unit and integration tests. Integration tests use TestContainers to spin up required infrastructure.

To run tests:
```
./mvnw test
```

## API Documentation

API documentation is available via Swagger UI at `/swagger-ui.html` when the application is running.
