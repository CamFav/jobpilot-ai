# JobPilot AI

JobPilot AI is a personal-first, privacy-conscious assistant for job discovery, recommendations, application tailoring, and controlled submission automation. It is being prepared to evolve into a multi-user product without prematurely introducing distributed-system complexity.

## Status

The repository currently contains the project foundations and documentation only. Flutter, Angular, Spring Boot, database, workflow, and CI implementations are deliberately deferred to dedicated missions.

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
- [Architecture overview](docs/architecture/overview.md)
- [Architecture decisions](docs/decisions/)
- [Development guide](docs/development/README.md)
- [Initial backlog](docs/tasks/README.md)

## Working agreement

Read [AGENTS.md](AGENTS.md) before contributing with an AI agent. Changes must be scoped, reviewed by a human, and made from a feature branch. No direct changes to `main`, pushes, merges, or deployments without explicit approval.

## Local configuration

When local services are introduced, copy `.env.example` to `.env` and replace placeholders locally. Never commit `.env` files or provider keys.
