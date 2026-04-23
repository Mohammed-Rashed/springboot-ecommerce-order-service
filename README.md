# Order Service

Spring Boot service for creating and reading customer orders in the ecommerce system.

The service persists orders and order items in PostgreSQL, manages schema changes with Flyway, exposes REST endpoints under `/api/v1/orders`, and is configured for Kafka connectivity.

## Tech Stack

- Java 21
- Spring Boot 4.0.5
- Spring Web MVC
- Spring Data JPA
- PostgreSQL
- Flyway
- Apache Kafka
- Maven Wrapper
- Lombok

## Project Structure

```text
src/main/java/com/rashed/ecommerce/orderservice
├── common
│   ├── exception
│   └── response
└── order
    ├── controller
    ├── dto
    ├── entity
    ├── mapper
    ├── repository
    └── service
```

## Prerequisites

Install or start these services before running the application:

- JDK 21
- PostgreSQL on `localhost:5433`
- Kafka on `localhost:9092`

The default database configuration is:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5433/order_service
    username: postgres
    password: 123456
```

Create the database if it does not already exist:

```sql
CREATE DATABASE order_service;
```

Flyway runs migrations from `src/main/resources/db/migration` when the application starts.

## Configuration

Main configuration lives in:

```text
src/main/resources/application.yaml
```

Default settings:

- Server port: `8085`
- Application name: `order-service`
- PostgreSQL database: `order_service`
- Kafka bootstrap server: `localhost:9092`
- Kafka consumer group: `order-service-group`
- Flyway migrations: enabled

For local overrides, update `application.yaml` or pass Spring properties through environment variables or JVM arguments.

## Run Locally

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

On macOS/Linux:

```bash
./mvnw spring-boot:run
```

The API will be available at:

```text
http://localhost:8085
```

## Build and Test

Run tests:

```bash
./mvnw test
```

Build the application:

```bash
./mvnw clean package
```

Run the packaged jar:

```bash
java -jar target/order-service-0.0.1-SNAPSHOT.jar
```

## API Endpoints

### Create Order

```http
POST /api/v1/orders
Content-Type: application/json
```

Request body:

```json
{
  "customerId": 1,
  "items": [
    {
      "productId": 101,
      "productName": "Wireless Mouse",
      "quantity": 2,
      "unitPrice": 19.99
    },
    {
      "productId": 102,
      "productName": "Keyboard",
      "quantity": 1,
      "unitPrice": 49.99
    }
  ]
}
```

Example curl:

```bash
curl -X POST http://localhost:8085/api/v1/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": 1,
    "items": [
      {
        "productId": 101,
        "productName": "Wireless Mouse",
        "quantity": 2,
        "unitPrice": 19.99
      }
    ]
  }'
```

Response fields:

```json
{
  "id": 1,
  "customerId": 1,
  "status": "PENDING",
  "totalAmount": 39.98,
  "items": [
    {
      "id": 1,
      "productId": 101,
      "productName": "Wireless Mouse",
      "quantity": 2,
      "unitPrice": 19.99
    }
  ],
  "createdAt": "2026-04-23T14:30:00"
}
```

### Get Order by ID

```http
GET /api/v1/orders/{id}
```

Example:

```bash
curl http://localhost:8085/api/v1/orders/1
```

### Get Customer Orders

```http
GET /api/v1/orders/customer/{customerId}
```

Example:

```bash
curl http://localhost:8085/api/v1/orders/customer/1
```

Orders are returned by creation time in descending order.

## Validation Rules

Create order requests must include:

- `customerId`: required
- `items`: required and not empty
- `productId`: required
- `productName`: required and not blank
- `quantity`: required and greater than `0`
- `unitPrice`: required and at least `0.01`

## Database Schema

Flyway creates:

- `orders`
- `order_items`

Important indexes:

- `idx_orders_customer_id`
- `idx_order_items_order_id`
- `idx_order_items_product_id`

Order statuses currently supported:

- `PENDING`
- `CONFIRMED`
- `REJECTED`

New orders are created with `PENDING` status.

## Useful Actuator Endpoint

Spring Boot Actuator is included. After the service starts, health can be checked with:

```bash
curl http://localhost:8085/actuator/health
```
