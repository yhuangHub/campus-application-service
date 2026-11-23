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

Current exercise: `docs/aufgaben/aufgabe-01-study-program-lookup`.

The starter contains the application entry point, `StudyProgram`, `StudyProgramRepository`, three H2 records, and one skeleton test. It intentionally contains no business DTO, Service, Controller, API exception handling, or business tests.

