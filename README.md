# 🐾 PetService API

A Spring Boot-based RESTful web service for managing pet services, user accounts, and booking appointments. This project provides endpoints for CRUD operations on users, services, and bookings.

---

## 📋 Features

- **User Management**: Create, retrieve, update, and delete pet owners.
- **Service Catalog**: Manage a list of available pet services (e.g., grooming, walking).
- **Booking System**: Book, view, and cancel appointments between users and services.
- **DTO & Converter Pattern**: Clean separation between entity models and data transfer objects.
- **OpenAPI Documentation**: Swagger UI integrated for easy API testing.

---

## 📦 Technologies Used

- Java 21
- Spring Boot 3.4.5
- Spring Data JPA
- MySQL
- Lombok
- Springdoc OpenAPI 2.x
- JUnit 5 + Mockito

---

## ⚙️ Getting Started

### Prerequisites

- Java 21
- Maven
- MySQL

### Database Setup

Create a MySQL database named `petservice` and configure your connection in `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/petservice
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### Build & Run

```bash
mvn clean install
mvn spring-boot:run
```

---

## 📚 API Documentation

Once running, access the Swagger UI at:

```
http://localhost:8080/swagger-ui.html
```

---

## 🧪 Testing

The project includes unit tests using **JUnit 5** and **Mockito** for:

- Services
- Converters
- Controllers (partial)

To run the tests:

```bash
mvn test
```

---

## 🗂️ Project Structure

```
com.example.petservice
├── controller       # REST controllers
├── service          # Business logic
├── converter        # DTO ↔ Entity mappers
├── dto              # Data Transfer Objects
├── entity           # JPA entities
├── config           # Swagger and other config
└── repository       # Spring Data Repositories
```

---

## 🚀 Future Improvements

- Add authentication/authorization
- Add pagination and filtering to endpoints


