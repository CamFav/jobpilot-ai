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
