# Project Context

## Goal

This repository is a long-running interview practice project for Java backend development. It begins with REST fundamentals and grows into a simplified university application, review, ranking, allocation, and admission system.

## Learning contract

- One repository and one evolving application.
- One active Aufgabe at a time.
- The learner owns business implementation and business tests.
- The examiner provides the statement, necessary starter structure, seed data, and a minimal skeleton test.
- All data and institutions are fictional.

## Technical baseline

- Java 21
- Spring Boot 4.1.1
- Maven Wrapper
- Spring MVC, Validation, Data JPA
- H2 initially; PostgreSQL, Flyway, and Testcontainers in a later Aufgabe
- JUnit 5, Mockito, MockMvc
- OpenAPI / Swagger UI

## Current state

Current exercise: `docs/aufgaben/aufgabe-02-capacity-update`.

Aufgabe 01 is accepted and merged. The cumulative code contains the working study-program lookup, its DTO, service, controller, exception handling, and regression tests.

Aufgabe 02 asks the learner to implement `PUT /api/v1/study-programs/{id}/capacity` with numeric boundaries, immutable DTOs, validation, and a service-level transaction. The exercise statement and accepted Aufgabe 01 code are provided; the learner owns the new business implementation and tests.

