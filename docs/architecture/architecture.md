# DTO Architecture

This project follows the DTO (Data Transfer Object) pattern to separate API contracts from database entities.

## Request DTOs

Used for incoming API data.

Examples:

* `ClientRequest`
* `RoomRequest`
* `ReservationRequest`

## Response DTOs

Used for outgoing API responses.

Examples:

* `ClientResponse`
* `RoomResponse`
* `ReservationResponse`

## Soft Delete

Instead of permanently deleting records, entities can be marked as deleted using:

```java
private LocalDateTime deletedAt;
```

This allows future recovery and audit support.

---

```
Client (React/Postman)
        ↓
   JWT Token
        ↓
Spring Security Filter
        ↓
 Controllers
        ↓
 Services
        ↓
 Database
```
