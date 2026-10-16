# Security design

## Purpose and scope

This document defines pragmatic application-security principles for the planned modular monolith. It is not a substitute for threat modeling, security testing, incident response planning, or professional security review when the application processes real user data.

## Authentication and authorization

- Select an authentication solution during backend bootstrap; do not invent a custom credential system without a justified need.
- Derive the current user identity from a validated server-side authentication context.
- Never treat a user identifier in a path, query parameter, request body, header, mobile client, webhook, or token-like string as proof of ownership. Client-supplied identifiers select a resource; server-side authorization decides whether access is allowed.
- Enforce authorization in the application use case or policy boundary for every read and mutation. A controller-only check is insufficient because future entry points may bypass it.
- Default to `404 Not Found` for an owned resource that the caller cannot access when disclosure would reveal its existence.

## Data isolation and IDOR/BOLA prevention

Every repository query and update for user-owned data must be scoped by the authenticated owner, for example by `(profileId, ownerUserId)`, not by ID alone. Test both read and write attempts using another user’s identifier. Database foreign keys and UUIDs support integrity and reduce guessability, but neither replaces authorization.

Search profiles may reference their owner but must not read or modify identity credentials. Future modules retain their own data and must use explicitly documented contracts to request only the necessary profile context.

## Input, API, and error handling

- Validate request shape, length, enum values, numeric ranges, collection size, and content type at the API boundary; enforce invariant rules again in the domain/application layer.
- Allow-list known enum values and workflow transitions. Do not build SQL, file paths, URLs, provider prompts, or shell commands from untrusted input without suitable validation/encoding.
- Define request-size, pagination, and rate-limit policies before exposing endpoints publicly.
- Return consistent problem responses without stack traces, secrets, other users’ data, or internal provider details.
- Use TLS in deployed environments; define CORS, CSRF, session/token, and security-header policy with the chosen client/authentication architecture.

## Secrets and configuration

- Keep credentials, encryption keys, connection strings, and provider tokens outside Git; `.env.example` contains names and placeholders only.
- Inject secrets through an approved runtime/deployment secret mechanism. Restrict access, rotate as supported, and revoke exposed credentials promptly.
- Never log secrets or include them in tests, sample payloads, screenshots, exception messages, or support tickets.
- Separate local, test, and production configuration; production configuration must fail safely when required secrets are absent.

## File and document protection

- Treat all uploads as untrusted. Restrict file type and size, verify content rather than trusting filename/MIME alone, scan before use, and isolate processing.
- Generate storage object names server-side; prevent path traversal; serve files through authorization checks rather than public paths.
- Limit document access to the owner and narrowly authorized server-side processes. Encrypt data in transit and use provider/storage controls appropriate to sensitivity.
- Remove temporary files and derived artifacts on defined expiry; do not retain document text in logs or diagnostics.

## n8n and external webhooks

- Treat every webhook as an external boundary. Use authentication appropriate to the sender, signature verification where available, timestamp/replay protection, payload limits, and allow-listed event types.
- Store webhook secrets separately; rotate them and never expose them to clients or logs.
- Make webhook processing idempotent, record only minimal correlation metadata, and restrict workflows to approved actions.
- n8n may orchestrate approved workflows but must not become the sole authority for domain ownership, authorization, or the human-approval gate.

## Logging, monitoring, and audit

- Log security-relevant events with minimal metadata: event type, timestamp, actor/reference, resource type/reference, result, and correlation ID where needed.
- Redact or avoid personal document contents, prompts, tokens, passwords, API keys, session identifiers, and full request/response bodies.
- Keep audit trails separate from debug logs where retention and access needs differ. Define retention before production use.
- Avoid claiming that a log is tamper-proof unless the implementation provides and verifies that property.

## Human approval and automation

No first application submission or other material external action may occur solely because an automated workflow, AI provider, or webhook requests it. The backend must record and verify the applicable user approval immediately before the action; UI confirmation alone is not a sufficient long-term audit boundary.

## Security tests for implementation

- unauthenticated requests receive the expected rejection;
- another user cannot read, update, pause, reactivate, or delete a profile by changing its identifier (IDOR/BOLA tests);
- invalid payloads, oversized collections, invalid enums, and stale versions produce safe errors;
- foreign-key, ownership, and optimistic-lock constraints hold with real PostgreSQL integration tests;
- secrets and sensitive values are absent from logs and error responses;
- upload validation and webhook signature/replay behavior are tested when those features are introduced;
- automated submission attempts without recorded approval are rejected.

## Deferred security decisions

Authentication provider, session/token model, encryption/key-management architecture, document storage, malware scanning service, rate limits, CORS/CSRF policy, and incident-response runbook need concrete deployment and product requirements before selection. Record those choices in the relevant bootstrap/design task or ADR.
