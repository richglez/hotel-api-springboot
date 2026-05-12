# Hotel API Spring Boot

A modern RESTful API for hotel reservation management built with Java and Spring Boot.

This project is focused on backend architecture, CRUD operations, DTO handling, JPA entity relationships, and scalable
API design following clean backend development practices.

---

## Features

* CRUD operations for:
    * Clients
    * Rooms
    * Reservations
* The user can book in this system hotel simulation
* RESTful API architecture
* DTO pattern implementation
* Entity relationships using JPA
* Soft delete support
* PATCH endpoints for partial updates
* Validation handling
* Layered architecture:
    * Controller
    * Service
    * Repository
    * DTO
    * Entity

---

## Technologies Used

| **Tech**            | **Use**                                           |
|---------------------|---------------------------------------------------|
| Java 21             | Main programming language                         |
| Spring Boot (4.0.6) | Backend framework for building the REST API       |
| Maven               | Dependency management and project build tool      |
| Spring Web          | REST controllers and HTTP request handling        |
| Spring Data JPA     | Database abstraction and repository layer         |
| Hibernate           | ORM for entity persistence and JPA implementation |
| Lombok              | Reduces boilerplate code with annotations         |

---

## Project Structure

```bash
src/main/java/com/richglez/hotel
│
├── controller  # Handles HTTP requests and API endpoints
├── service     # Contains business logic and application rules
├── repository  # Handles database access using Spring Data JPA
├── dto         # Data Transfer Objects used for requests and responses
├── model       # JPA entities and domain models
└── config      # Application and custom configuration classes
```

---

## Entities

### Client

Represents hotel customers.

#### Fields

* id
* name
* email
* password
* phone

### Room

Represents hotel rooms.

#### Fields

* id
* roomNumber
* roomType
* price
* available
* createdAt
* updatedAt
* deletedAt

### Reservation

Represents room reservations made by clients.

#### Fields

* id
* checkIn
* checkOut
* client
* room
* deletedAt

### Relationships

```java

@ManyToOne
private Client client;

@ManyToOne
private Room room;
```

### RoomType (Enum)

```java
public enum RoomType {
    SINGLE,
    DOUBLE,
    SUITE
}
```

---

## API Endpoints

### Clients

| **Method** | **Endpoint**                  | **Description**       |
|------------|-------------------------------|-----------------------|
| GET        | `/api/clients`                | Get all clients       |
| GET        | `/api/clients/{id}`           | Get client by ID      |
| POST       | `/api/clients`                | Create client         |
| PUT        | `/api/clients/{id}`           | Update client         |
| PATCH      | `/api/clients/{id}`           | Partial update client |
| DELETE     | `/api/clients/{id}`           | Soft delete client    |
| DELETE     | `/api/clients/{id}/permanent` | Hard delete client    |

### Rooms

| **Method** | **Endpoint**                | **Description**     |
|------------|-----------------------------|---------------------|
| GET        | `/api/rooms`                | Get all rooms       |
| GET        | `/api/rooms/{id}`           | Get room by ID      |
| POST       | `/api/rooms`                | Create room         |
| PUT        | `/api/rooms/{id}`           | Update room         |
| PATCH      | `/api/rooms/{id}`           | Partial update room |
| DELETE     | `/api/rooms/{id}`           | Soft delete room    |
| DELETE     | `/api/rooms/{id}/permanent` | Hard delete room    |

### Reservations

| **Method** | **Endpoint**                       | **Description**            |
|------------|------------------------------------|----------------------------|
| GET        | `/api/reservations`                | Get all reservations       |
| GET        | `/api/reservations/{id}`           | Get reservation by ID      |
| POST       | `/api/reservations`                | Create reservation         |
| PUT        | `/api/reservations/{id}`           | Update reservation         |
| PATCH      | `/api/reservations/{id}`           | Partial update reservation |
| DELETE     | `/api/reservations/{id}`           | Soft delete reservation    |
| DELETE     | `/api/reservations/{id}/permanent` | Hard delete reservation    |

--- 

## Example Requests

### Create Client

#### POST `/api/clients`

```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "123456",
  "phone": "5551234567"
}
```

---

### Create Room

#### POST `/api/rooms`

```json
{
  "roomNumber": "101",
  "roomType": "SINGLE",
  "price": 120.0,
  "available": true
}
```

---

### Create Reservation

#### POST `/api/reservations`

```json
{
  "checkIn": "2026-05-12T20:41:33.440",
  "checkOut": "2026-05-14T20:41:33.440",
  "clientId": 1,
  "roomId": 1
}
```

---

## Future Improvements

Planned features for future development:

* MySQL database integration
* Spring Security + JWT authentication
* Role-based authorization
* Frontend integration
* Docker support
* Swagger/OpenAPI documentation
* Unit and integration testing
* Pagination and filtering
* Global exception handling

---

## Running the Project

### 1. Clone the repository

```bash
git clone https://github.com/yourusername/hotel-api-springboot.git
```

### 2. Enter the project folder

```bash
cd hotel-api-springboot
```

### 3. Run the application

```bash
./mvnw spring-boot:run
```

> Or run directly from IntelliJ IDEA.

---

## Learning Goals

This project was created to practice and improve backend development skills with Spring Boot, REST APIs, DTO
architecture, and JPA relationships.

---

## Documentation
[Architecture](docs/architecture/architecture.md)

---


## Author

Ricardo Gonzalez

GitHub: github.com/richglez
LinkedIn: in/richglez-dev