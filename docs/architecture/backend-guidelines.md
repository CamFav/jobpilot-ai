# Backend architecture guidelines

## Purpose and scope

These guidelines apply when the Java 25 / Spring Boot backend is created. They implement the modular-monolith decision in [ADR-0001](../decisions/0001-modular-monolith.md) through pragmatic Clean Architecture and pragmatic domain-driven design (DDD), without requiring a framework, a separate deployable, or a layer that has no current value.

The goal is a codebase that remains understandable, testable, and safe to change as JobPilot AI grows from personal use to multiple users.

Pragmatic DDD means modeling the language and rules that matter to a business module, using bounded modules as ownership boundaries, and introducing tactical patterns such as aggregates or domain events only when they clarify consistency or collaboration. It does not require a full DDD pattern catalogue.

## Modular monolith

The backend is one deployable Spring Boot application and one transactional system. It is divided into business modules with explicit ownership and deliberately narrow interactions.

Initial module names are indicative and must be confirmed by the first user journeys:

- `identityprofile` — identity, candidate data, search profiles, preferences, consent;
- `opportunities` — offers, sources, qualification, saved searches;
- `applications` — applications, documents, review and approval states;
- `recommendations` — scoring, explanations, uncertainty from incomplete criteria;
- `automation` — approved workflow orchestration and audit records;
- `integrations` — provider-specific adapters for AI, n8n, sources, and document processing.

The application must not become a technical-layer monolith such as one global `controller`, `service`, and `repository` package. Package business behavior with the module that owns it.

## Recommended module shape

Use only the parts needed by a module. A small module may have a domain model and one application use case; it does not need every directory below.

```text
com.jobpilot.<module>/
  domain/            # Business concepts, invariants, domain services, domain events
  application/       # Use cases, commands/queries, transaction boundaries, ports
  api/               # REST controllers, request/response mapping, API error translation
  infrastructure/    # Persistence, Spring configuration, provider and messaging adapters
```

Guidelines:

- `domain` must not depend on Spring, JPA, HTTP, SQL, or a provider SDK.
- `application` orchestrates domain behavior and declares ports for external needs. It may depend on its own domain and on stable contracts exposed by another module.
- `api` translates HTTP and OpenAPI concerns to application use cases. It contains no business decisions.
- `infrastructure` implements application ports and contains persistence mappings, provider clients, and framework configuration.
- Place a type in the lowest layer that can own it. Avoid duplicate representations unless the boundary needs a different representation.

## Dependency direction

```text
API adapter ──────────────┐
Infrastructure adapter ───┼──> application ───> domain
                           └──> module public contract
```

Domain and application logic must not import controllers, repository implementations, JPA entities, generated OpenAPI classes, or provider SDK types. Framework annotations in an infrastructure adapter are acceptable; adding them to domain objects needs a documented reason.

## Module boundaries

- Each module owns its business rules and persisted data.
- Access another module through a small, application-facing contract, for example a query or command interface exposed by that module.
- Do not call another module's repository, use its persistence entity, or mutate its tables directly.
- Use synchronous calls for simple in-process collaboration. Introduce domain events only when an outcome can be handled independently or eventual consistency is explicitly acceptable.
- Keep cross-module transactions rare and visible. If a workflow needs coordination, model the orchestration in the owning application use case.
- Keep the shared kernel limited to stable technical primitives or universally shared concepts. It must not contain module-specific services or persistence models.

## Ports and adapters

Create a port when application logic needs an external capability whose implementation may vary, be slow, fail, or need isolation in tests. Typical ports include repositories, document storage, AI generation, n8n triggers, job-source clients, clocks, and ID generation.

Do not add an interface for a single pure in-memory collaborator merely because it might be replaced someday. A direct dependency is preferred until a real boundary exists.

Provider-specific request, response, exception, and SDK types stop in the infrastructure adapter. Translate them to application/domain concepts at the boundary.

## API, persistence, and transactions

- REST endpoints are adapters and must conform to the published OpenAPI contract when one exists.
- Use request/response DTOs at the API boundary; do not expose persistence entities.
- Put transaction boundaries around application use cases, not controllers or domain entities.
- Flyway is the only mechanism for production schema evolution once persistence exists. A migration accompanies every schema change.
- Preserve ownership and authorization checks before reading or mutating user-facing data.

## Exceptions and pragmatic trade-offs

These rules are defaults, not architecture theatre. A simpler implementation is acceptable when a stricter pattern adds indirection without a meaningful boundary, test benefit, or anticipated change.

Examples of valid exceptions:

- a simple module uses one application service directly rather than command/query abstractions;
- a value object remains close to one use case until it has proven domain-wide meaning;
- a synchronous in-process call replaces an event when atomicity and simplicity are required;
- an integration adapter is initially small and local until a second provider justifies a generalized abstraction.

Record the exception and its reason in the task/change record or pull request when one exists. Create an ADR only for a durable, cross-module, or difficult-to-reverse decision. Revisit an exception when coupling, duplication, failure handling, or team ownership makes it costly.
