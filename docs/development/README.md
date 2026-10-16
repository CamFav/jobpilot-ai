# Development guide

## Workflow

1. Create a GitHub Issue or record a scoped task before implementation.
2. Read the product, architecture, and relevant ADR documentation.
3. Work on a branch; never commit directly to `main`.
4. Plan changes proportionately to risk, then implement the smallest coherent slice.
5. Run relevant tests and static checks that are available. Record unavailable checks honestly.
6. Open a pull request for human review; do not push, merge, or deploy without explicit authorization.

## Conventions

- Use Conventional Commits, for example `docs: add architecture overview` or `feat(applications): track review state`.
- Keep REST contracts documented in OpenAPI when API work begins.
- Target Java 25 LTS for the backend. When the backend is bootstrapped, use a Gradle version that officially supports running on Java 25 and configure a Java 25 toolchain.
- Do not assume the backend JDK is the Flutter Android build JDK. Select and document the Flutter, Android Gradle Plugin, Gradle, and Android toolchain combination when the mobile application is bootstrapped.
- Add a Flyway migration for every persistent schema change once the backend exists.
- Keep configuration in environment variables or deployment secret stores; `.env.example` documents names only.
- Write ADRs for durable architecture decisions.

## Standards

Use the [backend architecture guidelines](../architecture/backend-guidelines.md) for modularity and the [coding standards](coding-standards.md) for code, testing, review, Definition of Done, and future automated controls.

## AI-assisted development

Agents must follow [AGENTS.md](../../AGENTS.md). Useful initial reusable skills, to create only when recurring work proves their value, are:

- a **Spring module change** skill for module boundaries, OpenAPI, Flyway, and tests;
- a **Flutter feature slice** skill for state, API integration, and widget tests;
- a **secure configuration review** skill for secret scanning and environment-variable changes.

Avoid generic skills that merely repeat repository instructions or one-off tasks.

## Tooling status

Tool availability depends on the developer machine. The bootstrap establishes no build tool or CI workflow because no executable application exists yet.
