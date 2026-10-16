# Instructions for AI agents

## Operating rules

1. Read the relevant documentation in `docs/` and this file before implementing a change. Start with `docs/product/vision.md`, `docs/architecture/overview.md`, and the applicable ADRs when the change affects product or architecture.
2. Plan before any non-trivial change. State the intended scope, assumptions, and verification approach before editing.
3. Keep changes limited to the requested scope. Do not refactor unrelated code or introduce applications, services, or infrastructure that were not requested.
4. Follow the repository conventions in `.editorconfig`, `.gitattributes`, and the development documentation.
5. Add or update tests appropriate to the change when executable code is introduced. Run the relevant available checks. Never claim a command or test passed unless it was actually run and its result observed.
6. Do not add secrets, credentials, personal data, generated local configuration, or real API keys to the repository. Use `.env.example` only for non-sensitive variable names and placeholders.
7. Do not modify `main` directly. Work on a dedicated branch. Do not push, merge, create releases, deploy, or make external changes without explicit user validation.
8. Do not install system software or change system configuration without explicit approval.

## Change completion report

At the end of a task, report:

- files added, modified, or deleted;
- commands and checks actually executed, with their results;
- any assumptions and residual risks;
- follow-up work or decisions needed from a human reviewer.

## Repository direction

JobPilot AI is planned as a modular monolith with a Java 25 LTS / Spring Boot backend, Flutter mobile application (Android first, then iOS), future Angular web application, PostgreSQL/Flyway persistence, n8n integrations, AI-provider integrations, Python document processing, and Docker Compose for local infrastructure. These applications are intentionally not scaffolded until a dedicated, approved task.

## Architecture and modularity rules

1. Follow the architecture overview and [backend guidelines](docs/architecture/backend-guidelines.md) for backend changes. Organize business code by functional domain, not by technical layer across the entire application.
2. Within a module, keep domain rules independent of HTTP, persistence, frameworks, and external providers. Dependencies point inward: API and infrastructure adapters depend on application and domain code, never the reverse.
3. Use ports and adapters only at real boundaries: databases, HTTP APIs, AI providers, n8n, document processing, clocks, or other replaceable external concerns. Do not create ports, interfaces, factories, or layers merely to satisfy a pattern.
4. Keep module coupling low. A module may use another module only through an explicitly documented application-facing contract; do not access another module's repositories, persistence entities, or internal packages.
5. Prefer a small, stable shared kernel. Move business concepts into a shared package only when they are genuinely shared and have a stable meaning; never use it as a convenience dumping ground.
6. Preserve domain ownership, authorization, auditability, and the human-approval rule at the relevant module boundary.

## Code quality rules

1. Apply SOLID, DRY, KISS, and YAGNI pragmatically. Favor readable, direct code over speculative reuse or generic frameworks.
2. Make names, data flow, validation, error handling, and transaction boundaries understandable from the code. Comment decisions and non-obvious constraints, not line-by-line mechanics.
3. Keep methods and classes focused; extract only when it clarifies a responsibility or enables meaningful reuse/testing.
4. Test business behavior and risk-prone integration boundaries. Do not add tests that only mirror implementation details or provide no meaningful assertion.
5. Follow the Definition of Done and review standards in [coding standards](docs/development/coding-standards.md). If an exception is justified, document its reason and scope in the pull request or an ADR where durable.
