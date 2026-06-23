# Hotel API — AGENTS.md

## Quick start

```bash
# Backend (requires PostgreSQL on port 5433)
./mvnw spring-boot:run -Dspring-boot.run.profiles=local

# Frontend (separate terminal)
cd frontend && pnpm install --frozen-lockfile && pnpm run dev
```

## Build & test

```bash
./mvnw verify                          # compile + test + verify (CI uses this)
./mvnw test -Dtest=RoomServiceTest     # single test class
```

Tests use PostgreSQL (`@SpringBootTest` + `@ActiveProfiles("test")`) — you need a running instance or set `SPRING_DATASOURCE_URL`, `DB_USER`, `DB_PASSWORD`, `JWT_SECRET`.

Frontend (from `frontend/`):
```bash
pnpm run build    # tsc -b && vite build
pnpm run lint     # ESLint
pnpm run dev      # Vite dev server on :5173
```

## Architecture

- `com.richglez.hotel` — base package. Entrypoint: `HotelApiSpringbootApplication` with `@EnableJpaAuditing`.
- Module dirs: `auth/`, `rooms/`, `reservations/`, `users/`, `security/`, `common/`, `config/`, `exception/`.
- Each feature has `controller/`, `dto/`, `model/`, `repository/`, `service/`.
- Role model: `User` implements `UserDetails`. Roles are stored as `Roles` enum (ADMIN, MANAGER, RECEPTIONIST, GUEST). Authority string is `ROLE_<NAME>`.

## Auth & security

- JWT via `jjwt` (0.11.5). Filter: `JwtAuthFilter`. Secret via `JWT_SECRET` env var.
- Default admin seeded on startup: `admin@hotel.com` / `Admin1234!` (see `DataInitializer`).
- `@PreAuthorize` on controller methods. Public endpoints: `POST /api/auth/**`, `GET /api/rooms/**`.
- Swagger UI: disabled in prod; at `/swagger-ui.html` with `local` profile.

## DB & config

- `ddl-auto: validate` by default (use `local` profile or `DDL_AUTO` env for dev).
- DB on `localhost:5433` (Docker host mapping) — port 5433, not 5432.
- Profiles: `local` (swagger + overrides), `prod` (ddl-auto: update), `test` (ddl-auto: create-drop).
- Soft delete via `deletedAt` (LocalDateTime) on entities. Separate `.../permanent` DELETE endpoints.
- Lombok is configured as annotation processor.

## CI

GitHub Actions at `.github/workflows/ci.yml`:
1. Backend: PostgreSQL service → `./mvnw verify` → upload test reports + `.jar`.
2. Frontend: pnpm 11, Node 22 → `pnpm install --frozen-lockfile` → `pnpm run build` → `pnpm run lint`.
3. Dependency review on PRs only.

CD at `.github/workflows/cd.yml` is stubbed out (commented).

## Docker

```bash
docker compose up -d    # starts postgres (:5433), backend (:8080), frontend (:5173)
```

Postgres image is `postgres:16`. Backend uses multi-stage `Dockerfile` (maven build → jre). Frontend uses nginx to serve built assets.

## Misc

- No frontend test framework is set up yet (roadmap item).
- H2 is a runtime dependency but not used for tests (tests go through PostgreSQL).
- Frontend uses `@` path alias (configured in `vite.config.ts`).
- `HttpMethod.OPTIONS` → permitAll (CORS preflight).
