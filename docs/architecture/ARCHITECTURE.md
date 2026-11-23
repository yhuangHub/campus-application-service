# Architecture

## Style

The application is a feature-oriented modular monolith. It stays as one deployable Spring Boot application while business capabilities are separated by packages.

```text
HTTP / Swagger
      |
      v
Controller + DTO + Validation
      |
      v
Application Service + Transaction
      |
      +----> Domain model
      |
      v
Repository -> Database
```

## Package evolution

```text
de.yhuang.campusapplication
├── studyprogram
├── applicant       (later)
├── application     (later)
├── document        (later)
├── review          (later)
├── ranking         (later)
├── admission       (later)
├── audit           (later)
└── shared          (only for truly shared infrastructure)
```

Each feature may contain `controller`, `dto`, `entity`, `repository`, `service`, and `exception` packages when the feature actually needs them. Empty future packages are not created in advance.

## Dependency rules

- Controller calls Service, never Repository directly.
- REST responses use DTOs, never JPA entities.
- Transaction boundaries belong to application services.
- Repository queries are responsible for database filtering; do not call `findAll()` merely to practise Stream filtering.
- Domain state changes should be expressed by meaningful methods when a business transition exists.
- One feature must not call another feature's Controller.
- `shared` must not become a general dumping ground.

## API conventions

- Base path: `/api/v1`
- JSON request and response bodies
- `200` query/update, `201` create, `204` delete
- `400` invalid request, `404` missing resource, `409` business conflict
- Error responses have stable `code` and `message` fields.
- DTOs may use Java records.
- Business timestamps use `Instant`; current time is obtained through an injected `Clock` when testability matters.

## Testing

- Unit tests for Java/domain and Service behaviour
- MockMvc Controller tests for HTTP contracts
- Repository tests for real JPA queries
- Spring Boot integration tests for persistence and transaction behaviour
- Every later Aufgabe keeps all accepted earlier tests green

