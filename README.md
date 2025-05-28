# Subscription_based_Model
# Subscription Management Microservice

A Spring Boot microservice for managing SaaS subscriptions and plans.

## Features

- Plan Management (CRUD operations)
- Subscription Management (Create, Read, Update, Cancel)
- JWT-based Authentication
- Role-based Access Control
- Automatic Subscription Expiration Handling

## Tech Stack

- Java 17
- Spring Boot 3.2.3
- Spring Security
- MySQL
- JWT for Authentication
- Maven

## Prerequisites

- JDK 17 or later
- MySQL 8.0 or later
- Maven 3.6 or later

## Setup

1. Clone the repository:
```bash
git clone <repository-url>
cd subscription-service
```

2. Configure MySQL:
   - Create a MySQL database named `subscription_db`
   - Update `src/main/resources/application.properties` with your MySQL credentials

3. Build the project:
```bash
mvn clean install
```

4. Run the application:
```bash
mvn spring-boot:run
```

The service will start on `http://localhost:8080/api/v1`

## API Endpoints

### Plans

- `GET /plans` - List all active plans (Public)
- `GET /plans/{id}` - Get plan details (Public)
- `POST /plans` - Create a new plan (Admin only)
- `PUT /plans/{id}` - Update a plan (Admin only)
- `DELETE /plans/{id}` - Delete a plan (Admin only)

### Subscriptions

- `POST /subscriptions` - Create a new subscription
- `GET /subscriptions/{userId}` - Get user's active subscription
- `GET /subscriptions/{userId}/history` - Get user's subscription history
- `PUT /subscriptions/{userId}` - Update subscription (change plan)
- `DELETE /subscriptions/{userId}` - Cancel subscription

## Security

The service uses JWT for authentication. Include the JWT token in the Authorization header:

```
Authorization: Bearer <your-jwt-token>
```

## Error Handling

The service provides detailed error messages for:
- Invalid input
- Resource not found
- Unauthorized access
- Business rule violations

## Scheduled Tasks

- Daily check for expired subscriptions (runs at midnight)
- Automatic status updates for expired subscriptions

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request 
