# JobPilot AI

JobPilot AI is a personal-first, privacy-conscious assistant for job discovery, recommendations, application tailoring, and controlled submission automation. It is being prepared to evolve into a multi-user product without prematurely introducing distributed-system complexity.

## Status

The backend bootstrap is available in [`backend/`](backend/). It provides a technical ping endpoint, restricted Actuator health endpoint, PostgreSQL/Flyway configuration, and test scaffolding. Product modules, Flutter, Angular, workflows, and CI remain intentionally deferred.

## Planned stack

| Area | Planned technology |
| --- | --- |
| Mobile | Flutter / Dart (Android first, iOS later) |
| Web | Angular / TypeScript |
| Backend | Java 25 LTS / Spring Boot modular monolith |
| Persistence | PostgreSQL / Flyway |
| Business automation | n8n |
| AI | OpenAI and/or Claude, behind application boundaries |
| Document processing | Python |
| Local infrastructure | Docker Compose |
| CI/CD | GitHub Actions |

## Documentation

- [Product vision](docs/product/vision.md)
- [Search profiles design](docs/product/search-profiles-design.md)
- [Architecture overview](docs/architecture/overview.md)
- [Backend architecture guidelines](docs/architecture/backend-guidelines.md)
- [Conceptual, logical, and physical data model](docs/architecture/data-model/)
- [Architecture decisions](docs/decisions/)
- [Development guide](docs/development/README.md)
- [Coding standards and Definition of Done](docs/development/coding-standards.md)
- [Privacy by design](docs/security/privacy-by-design.md)
- [Security design](docs/security/security-design.md)
- [Initial backlog](docs/tasks/README.md)

## Working agreement

Read [AGENTS.md](AGENTS.md) before contributing with an AI agent. Changes must be scoped, self-reviewed (optionally with AI or human input), and made from a feature branch. No direct changes to `main`, pushes, merges, or deployments without explicit approval.

## Local configuration

Copy `.env.example` to `.env` for local services and replace placeholders locally. Never commit `.env` files or provider keys.

## Backend prerequisites

- Java 25 LTS.
- Docker Desktop for local PostgreSQL and Testcontainers integration tests.
- Internet access on first Gradle run to resolve dependencies and, on a fresh machine, download the Gradle Wrapper distribution.

The committed Gradle Wrapper uses Gradle 9.2.0. A system-wide Gradle installation is not required.

## Run locally on Windows PowerShell

```powershell
Copy-Item .env.example .env
docker compose up -d postgres
docker compose ps
Set-Location backend
.\gradlew.bat bootRun
```

With PostgreSQL healthy, verify:

```powershell
Invoke-RestMethod http://localhost:8080/api/v1/system/ping
Invoke-RestMethod http://localhost:8080/actuator/health
```

The ping response is `{"status":"ok"}`. Actuator exposes only `health` and `info`; health details are not exposed publicly.

## Build and test

From `backend/`:

```powershell
.\gradlew.bat test
.\gradlew.bat bootRun
```

The Testcontainers PostgreSQL test is skipped automatically when Docker is unavailable. To stop the local database without deleting its persistent volume:

```powershell
Set-Location ..
docker compose down
```

## Database and migrations

Spring reads `DATABASE_URL`, `DATABASE_USERNAME`, and `DATABASE_PASSWORD`. Docker Compose reads the `POSTGRES_*` variables in `.env`. Flyway is enabled and Hibernate runs in `validate` mode. No versioned migration is included yet because this bootstrap deliberately creates no domain or identity table; Flyway’s schema-history initialization is verified by the PostgreSQL integration test when Docker is available.
