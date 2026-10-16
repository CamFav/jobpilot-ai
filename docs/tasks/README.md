# Initial backlog

This is a planning seed, not a substitute for GitHub Issues. Convert approved items into issues with acceptance criteria before implementation.

## Recommended next slices

1. Define the first user journey and information model: candidate profile, multiple search profiles, offer, recommendation explanation, document language, application, and approval states.
2. Bootstrap the Spring Boot modular monolith with Java 25 LTS, a Java-25-compatible Gradle wrapper, OpenAPI, PostgreSQL, Flyway, and test conventions.
3. Add local Docker Compose services for PostgreSQL and the backend only after their configuration is defined.
4. Bootstrap the Flutter Android application around the selected first user journey.
5. Establish GitHub Actions once there are meaningful build and test commands to run.

## Questions to resolve in issues

- Which job sources are permitted and prioritized for the first release?
- What exact actions require an explicit approval checkpoint?
- What recommendation wording and confidence treatment is appropriate when an offer omits experience criteria?
- Which French and English document templates and review rules are needed initially?
- Which identity/authentication approach is needed before multi-user access?
- Where will CVs and generated documents be stored, retained, and deleted?
- Which AI provider is primary, what budget applies, and what user data may be sent to it?
