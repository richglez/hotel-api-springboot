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
| React               | Frontend framework for building UI                |
| Spring Web          | REST controllers and HTTP request handling        |
| Spring Data JPA     | Database abstraction and repository layer         |
| Hibernate           | ORM for entity persistence and JPA implementation |
| Lombok              | Reduces boilerplate code with annotations         |

---

## Project Structure

```bash
hotel-api-springboot/
│
├── .env                          # Environment variables
├── docker-compose.yml            # Docker Compose setup (PostgreSQL + app)
├── Dockerfile                    # Docker image for the backend
├── pom.xml                       # Maven project configuration
│
├── frontend/                     # React + TypeScript frontend
│   ├── src/
│   │   ├── api/                  # Axios API client
│   │   ├── app/router/           # App router
│   │   ├── context/              # React contexts
│   │   ├── features/             # Feature modules (auth, rooms, reservations, clients)
│   │   ├── pages/                # Page-level components (Home)
│   │   ├── router/               # Route definitions
│   │   ├── shared/               # Shared components (Navbar, Footer, UI)
│   │   └── styles/               # Global styles
│   └── ...config files
│
├── src/
│   ├── main/
│   │   ├── java/com/richglez/hotel/
│   │   │   │
│   │   │   ├── HotelApiSpringbootApplication.java   # Entry point
│   │   │   │
│   │   │   ├── auth/                                # Authentication module
│   │   │   │   ├── controller/AuthController.java   # Login & register endpoints
│   │   │   │   ├── dto/                             # LoginRequest, RegisterRequest, AuthResponse
│   │   │   │   └── service/AuthService.java         # Authentication logic
│   │   │   │
│   │   │   ├── common/                              # Shared enums & utilities
│   │   │   │   └── enums/                           # Roles, RoomType
│   │   │   │
│   │   │   ├── config/                              # App configuration
│   │   │   │   ├── CorsConfig.java                  # CORS configuration
│   │   │   │   ├── DataInitializer.java             # Seed data on startup
│   │   │   │   └── OpenApiConfig.java               # Swagger/OpenAPI config
│   │   │   │
│   │   │   ├── exception/                           # Global error handling
│   │   │   │   └── GlobalExceptionHandler.java      # @ControllerAdvice
│   │   │   │
│   │   │   ├── reservations/                        # Reservation module
│   │   │   │   ├── controller/                      # ReservationController
│   │   │   │   ├── dto/                             # Request/Response/Patch DTOs
│   │   │   │   ├── model/Reservation.java           # JPA entity
│   │   │   │   ├── repository/                      # ReservationRepository
│   │   │   │   └── service/                         # ReservationService
│   │   │   │
│   │   │   ├── rooms/                               # Room module
│   │   │   │   ├── controller/                      # RoomController
│   │   │   │   ├── dto/                             # Request/Response/Patch DTOs
│   │   │   │   ├── model/Room.java                  # JPA entity
│   │   │   │   ├── repository/                      # RoomRepository
│   │   │   │   └── service/                         # RoomService
│   │   │   │
│   │   │   ├── security/                            # JWT & Spring Security
│   │   │   │   ├── JwtAuthFilter.java               # Request filter
│   │   │   │   ├── JwtService.java                  # Token generation/validation
│   │   │   │   ├── SecurityConfig.java              # Security chain config
│   │   │   │   └── UserDetailsServiceImpl.java      # Loads user from DB
│   │   │   │
│   │   │   └── users/                               # User module
│   │   │       ├── controller/                      # UserController
│   │   │       ├── dto/                             # Request/Response/Patch DTOs
│   │   │       ├── model/User.java                  # JPA entity
│   │   │       ├── repository/                      # UserRepository
│   │   │       └── service/                         # UserService
│   │   │
│   │   └── resources/
│   │       ├── application.yml                      # Main config
│   │       ├── application-local.yml                # Local profile overrides
│   │       └── application-local.yml.example        # Template for local config
│   │
│   └── test/java/com/richglez/hotel/
│       └── HotelApiSpringbootApplicationTests.java  # Smoke test
│
├── docs/
│   └── architecture/
│       └── architecture.md          # Architecture documentation
│
└── target/                          # Compiled output (gitignored)
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

## Roadmap

Project Status planned features for future development:

* [x] PostgreSQL database integration
* [x] Spring Security + JWT authentication
* [x] Role-based authorization
* [x] Frontend integration
* [x] Docker support
* [x] Swagger/OpenAPI documentation
* [ ] Unit and integration testing
* [ ] Pagination and filtering
* [ ] Global exception handling

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
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
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

* [GitHub](https://github.com/richglez)
* [LinkedIn](https://www.linkedin.com/in/richglez-dev/)
