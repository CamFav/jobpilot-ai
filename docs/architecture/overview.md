# Architecture overview

## Target shape

The target is a modular monolith. A single Spring Boot deployment will host bounded business modules behind explicit package and API boundaries. This reduces operational overhead for a personal-first product while retaining a clear path to extraction if proven necessary.

Planned modules are intentionally indicative rather than a final package design:

- **identity and profile** — user identity, candidate profile, multiple search profiles, preferences, consent;
- **opportunities** — offers, sources, qualification, saved searches;
- **applications** — application lifecycle, documents, review and approval states;
- **recommendations** — relevance scoring and explanation;
- **automation** — approved workflow orchestration and audit records;
- **integrations** — AI providers, n8n, and external job sources;
- **shared kernel** — small, stable cross-cutting primitives only.

## Interfaces and data

- Mobile clients will use versioned REST endpoints described with OpenAPI.
- A future Angular client will consume the same public application API where appropriate.
- PostgreSQL will be the transactional store; Flyway will version schema changes.
- Python document processing will be invoked through a deliberately defined interface when introduced; do not let the backend depend on ad-hoc scripts.
- n8n orchestrates approved business workflows; core domain rules remain in the backend.
- AI providers remain behind an application-owned adapter so prompts, models, costs, retries, and provider changes are controllable.
- Recommendation results will retain the signals, missing criteria, and confidence/explanation needed to avoid treating an absent experience requirement as an automatic exclusion.
- Document metadata will record language (`fr` or `en`) and its related offer and search profile; the storage implementation remains a deferred decision.

## Security baseline

- Treat CVs, application documents, and prompts as sensitive user data.
- Keep credentials outside Git and inject them through local or deployment configuration.
- Design ownership and authorization checks at module boundaries from the first persisted user-facing data.
- Log audit-relevant actions without leaking document contents or secrets.
- Require human approval before application submission or another material external action.

## Deferred decisions

Authentication provider, exact authorization model, API versioning convention, document-storage provider, eventing mechanism, and deployment topology need product and operational requirements before they are selected. See the ADRs for current choices.
