# Hotel API Spring Boot

A modern RESTful API for hotel reservation management built with Java and Spring Boot.

This project is focused on backend architecture, CRUD operations, DTO handling, JPA entity relationships, and scalable
API design following clean backend development practices.

---

## Features

* CRUD operations for:
    * Users (guests, admins, managers, receptionists)
    * Rooms
    * Reservations
* Authentication & authorization with JWT + Spring Security
* Role-based access control RBAC (ADMIN, MANAGER, RECEPTIONIST, GUEST)
* Room browsing (public access) and reservation management
* RESTful API architecture
* DTO pattern implementation
* Entity relationships using JPA
* Soft delete support
* PATCH endpoints for partial updates
* Input validation with `@Valid` and Jakarta Validation
* Global exception handling via `@ControllerAdvice`
* Layered architecture:
    * Controller
    * Service
    * Repository
    * DTO
    * Entity
* PostgreSQL database
* Docker & Docker Compose support
* Swagger / OpenAPI documentation
* React + TypeScript frontend

---

## Technologies Used

| **Tech**             | **Use**                                           |
|----------------------|---------------------------------------------------|
| Java 21              | Main programming language                         |
| Spring Boot 4.0.6    | Backend framework for building the REST API       |
| Maven                | Dependency management and project build tool      |
| Spring Web           | REST controllers and HTTP request handling        |
| Spring Data JPA      | Database abstraction and repository layer         |
| Spring Security      | Authentication & role-based authorization         |
| Hibernate            | ORM for entity persistence and JPA implementation |
| PostgreSQL           | Production database                               |
| JWT (jsonwebtoken)   | Token-based authentication                        |
| Lombok               | Reduces boilerplate code with annotations         |
| React                | Frontend framework for building UI                |
| Docker / Compose     | Containerization of app + database                |

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
│   │   ├── features/             # Feature modules (auth, rooms, reservations, users)
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

### User

Represents hotel users (guests, admins, managers, receptionists).

#### Fields

| Field    | Type            | Notes                        |
|----------|-----------------|------------------------------|
| id       | Long            | PK, auto-increment           |
| name     | String          | not null                     |
| lastName | String          | not null                     |
| email    | String          | not null, unique             |
| password | String          | BCrypt encrypted             |
| phone    | String          | nullable                     |
| role     | Roles (Enum)    | ADMIN, MANAGER, RECEPTIONIST, GUEST |
| createdAt   | LocalDateTime | auto-set                     |
| updatedAt   | LocalDateTime | auto-set                     |
| deletedAt   | LocalDateTime | nullable (soft delete)     |

### Room

Represents hotel rooms.

#### Fields

| Field       | Type              | Notes                        |
|-------------|-------------------|------------------------------|
| id          | Long              | PK, auto-increment           |
| roomNumber  | String            |                              |
| roomType    | RoomType (Enum)   | STANDARD, STANDARD_DOUBLE, SUITE, JUNIOR_SUITE, PRESIDENTIAL |
| price       | Double            |                              |
| available   | Boolean           |                              |
| capacity    | Integer           | Max guests                   |
| size        | Double            | Room area in m²              |
| name        | String            | Room display name            |
| description | String            | Visible to client            |
| createdAt   | LocalDateTime     | auto-set                     |
| updatedAt   | LocalDateTime     | auto-set                     |
| deletedAt   | LocalDateTime     | nullable (soft delete)       |

### Reservation

Represents room reservations made by users.

#### Fields

| Field    | Type            | Notes                        |
|----------|-----------------|------------------------------|
| id       | Long            | PK, auto-increment           |
| checkIn  | LocalDateTime   |                              |
| checkOut | LocalDateTime   |                              |
| user     | User            | @ManyToOne                   |
| room     | Room            | @ManyToOne                   |
| adults   | Integer         |                              |
| children | Integer         | default 0                    |
| createAt | LocalDateTime   | auto-set                     |
| updateAt | LocalDateTime   | auto-set                     |
| deletedAt| LocalDateTime   | nullable (soft delete)       |

### Relationships

```
User (1) ────< (N) Reservation (N) >──── (1) Room
```

### Enums

#### Roles

```java
public enum Roles {
    ADMIN,
    MANAGER,
    RECEPTIONIST,
    GUEST
}
```

#### RoomType

```java
public enum RoomType {
    STANDARD,
    STANDARD_DOUBLE,
    SUITE,
    JUNIOR_SUITE,
    PRESIDENTIAL
}
```

---

## API Endpoints

### Auth (public)

| **Method** | **Endpoint**            | **Description**           |
|------------|-------------------------|---------------------------|
| POST       | `/api/auth/register`    | Register a new user       |
| POST       | `/api/auth/login`       | Login, returns JWT token  |

### Users

| **Method** | **Endpoint**              | **Description**       |
|------------|---------------------------|-----------------------|
| GET        | `/api/users`              | Get all users         |
| GET        | `/api/users/{id}`         | Get user by ID        |
| PUT        | `/api/users/{id}`         | Full update user      |
| PATCH      | `/api/users/{id}`         | Partial update user   |
| DELETE     | `/api/users/{id}`         | Soft delete user      |

### Rooms

| **Method** | **Endpoint**                | **Description**       | **Auth**                     |
|------------|-----------------------------|-----------------------|------------------------------|
| GET        | `/api/rooms`                | Get all rooms         | Public                       |
| GET        | `/api/rooms/{id}`           | Get room by ID        | Public                       |
| POST       | `/api/rooms`                | Create room           | ADMIN                        |
| PUT        | `/api/rooms/{id}`           | Full update room      | ADMIN / MANAGER / RECEPTION  |
| PATCH      | `/api/rooms/{id}`           | Partial update room   | ADMIN / MANAGER / RECEPTION  |
| DELETE     | `/api/rooms/{id}`           | Soft delete room      | ADMIN / MANAGER              |
| DELETE     | `/api/rooms/{id}/permanent` | Hard delete room      | ADMIN / MANAGER              |

### Reservations

| **Method** | **Endpoint**                       | **Description**            | **Auth**                    |
|------------|------------------------------------|----------------------------|-----------------------------|
| GET        | `/api/reservations`                | Get all reservations       | ADMIN / MANAGER / RECEPTION |
| GET        | `/api/reservations/{id}`           | Get reservation by ID      | ADMIN / MANAGER / RECEPTION |
| POST       | `/api/reservations`                | Create reservation         | Authenticated users         |
| PUT        | `/api/reservations/{id}`           | Full update reservation    | Authenticated users         |
| PATCH      | `/api/reservations/{id}`           | Partial update reservation | Authenticated users         |
| DELETE     | `/api/reservations/{id}`           | Soft delete reservation    | Authenticated users         |
| DELETE     | `/api/reservations/{id}/permanent` | Hard delete reservation    | Authenticated users         |

--- 

## Example Requests

### Register a new user

#### POST `/api/auth/register`

```json
{
  "name": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "password": "securePassword123",
  "phone": "5551234567"
}
```

> Response: `{ "token": "eyJhbGciOiJIUzI1NiIs..." }`

---

### Login

#### POST `/api/auth/login`

```json
{
  "email": "john@example.com",
  "password": "securePassword123"
}
```

> Response: `{ "token": "eyJhbGciOiJIUzI1NiIs..." }`

---

### Create Room

#### POST `/api/rooms` (requires ADMIN role)

```json
{
  "roomNumber": "101",
  "roomType": "STANDARD",
  "price": 120.0,
  "available": true,
  "capacity": 2,
  "size": 25.5,
  "name": "Standard Room",
  "description": "Cozy room with city view"
}
```

---

### Create Reservation

#### POST `/api/reservations` (requires authentication)

```json
{
  "checkIn": "2026-05-12T20:41:33.440",
  "checkOut": "2026-05-14T20:41:33.440",
  "clientId": 1,
  "roomId": 1,
  "adults": 2,
  "children": 0
}
```

---

## Roadmap

Planned features for future development:

* [x] PostgreSQL database integration
* [x] Spring Security + JWT authentication
* [x] Role-based authorization
* [x] Global exception handling
* [x] Swagger / OpenAPI documentation
* [x] Frontend integration
* [x] Docker support
* [x] Unit and integration testing
* [x] Pagination and filtering
* [ ] Railway Deployment
* [ ] Configure Root Directory in Railway
* [ ] CD that completes CI/CD
* [ ] Setting Checkstyle (backend lintern) for backend lintern
* [ ] Vitest / Jest Integration for frontend testing

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
