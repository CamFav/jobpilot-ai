# ADR-0001: Adopt a modular monolith initially

- Status: accepted
- Date: 2026-10-16

## Context

JobPilot AI starts as a personal-use product but may become multi-user. It will include application tracking, recommendations, documents, AI integrations, and controlled automation. These capabilities need separation, but current scale and team size do not justify distributed deployment complexity.

## Decision

Build the backend as one Java 25 LTS / Spring Boot application with explicit domain modules, a single deployable unit, PostgreSQL persistence, Flyway migrations, and REST/OpenAPI contracts.

## Consequences

Positive:

- simpler local development, deployment, testing, and transactional consistency;
- clear domain seams for future extraction if a real operational need appears;
- lower infrastructure and coordination overhead.

Trade-offs:

- package/module boundaries must be actively protected;
- independent scaling and deployment are deferred;
- integrations must not become hidden cross-module coupling.

## Revisit when

Revisit this decision when measured load, independent release cadence, fault isolation, regulatory boundaries, or team ownership create a concrete need for separate deployables.
