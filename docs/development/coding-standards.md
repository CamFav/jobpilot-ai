# Coding standards and Definition of Done

## Purpose

These standards guide future application code. They complement [AGENTS.md](../../AGENTS.md), the [backend architecture guidelines](../architecture/backend-guidelines.md), and the existing product and security rules. They do not require a particular library before the relevant application is bootstrapped.

## Code conventions

- Follow `.editorconfig`; use UTF-8, LF line endings, a final newline, and four spaces for Java.
- Name types and methods for the business intent they express. Prefer specific names over vague terms such as `Manager`, `Helper`, `Utils`, `Data`, or `Common`.
- Keep public APIs small and purposeful. Validate input at boundaries and make failures actionable without exposing sensitive data.
- Make nullability, mutability, error handling, authorization, and transaction scope explicit where they affect behavior.
- Favor immutable values and side-effect-free domain logic where practical. Do not force immutability when it obscures a simple state transition.
- Avoid hidden global state, duplicated business rules, long parameter lists, boolean mode flags, and catch-all exception handling.
- Comments explain intent, constraints, trade-offs, or surprising behavior. Delete comments that only restate the code.

## Testing standards

- Test observable behavior, business rules, and regression risks. Every meaningful test must assert an outcome.
- Unit-test domain rules and application use cases without Spring where possible.
- Use integration tests for persistence mappings, migrations, Spring wiring, HTTP contracts, and provider adapters when introduced.
- Test authorization, approval checkpoints, sensitive-data handling, and recommendation uncertainty where the feature can affect a user or an external action.
- Cover happy paths, relevant failures, and edge cases proportionately to risk. Do not pursue coverage percentages as a substitute for useful tests.
- Make tests deterministic: control time, IDs, randomness, network calls, and provider responses through real boundaries or test doubles.
- A bug fix includes a regression test when practical. If it is not practical, explain why in the pull request.

## Code review standards

Reviewers verify that a change:

- solves the stated problem within its declared scope;
- respects module ownership, dependency direction, and API/data boundaries;
- has understandable names, focused responsibilities, and no speculative abstraction;
- protects secrets, personal documents, authorization, and human approval gates;
- has proportionate tests and honest verification results;
- updates OpenAPI, Flyway migrations, ADRs, or documentation when applicable.

Review comments should distinguish blocking correctness, security, and maintainability concerns from optional suggestions.

## Continuous learning

Every important feature should leave the developer able to explain the concepts used, the technical choices made, and the tests that demonstrate the expected behavior. Capture the essential reasoning in the pull request, relevant code comments, or documentation at the level needed to support future maintenance; do not turn routine changes into tutorials.

## Definition of Done

A change is ready for review only when all applicable items are true:

- [ ] The issue or task, expected behavior, and non-goals are clear.
- [ ] Relevant product, architecture, ADR, and security documentation has been read.
- [ ] The change is limited to the agreed scope and follows module boundaries.
- [ ] Code is readable, has no unnecessary abstraction, and handles relevant validation and failures.
- [ ] Appropriate automated tests were added or updated, and the relevant available checks were run.
- [ ] Test/check outcomes are recorded truthfully; unavailable checks are identified with a reason.
- [ ] API contract, database migration, configuration example, and documentation changes are included when applicable.
- [ ] No secrets, personal data, build artefacts, or local configuration files are included.
- [ ] Security, authorization, audit, and human-approval impacts were assessed where relevant.
- [ ] Residual risks, exceptions, and follow-up work are visible in the pull request.
- [ ] For an important feature, the concepts used, technical choices, and relevant tests are understandable from the pull request, code, or documentation.
- [ ] A human reviewer has approved the change before merge or deployment. Any push still requires the explicit user validation required by `AGENTS.md`.

## Planned automated controls

No build or CI is created until it can execute meaningful checks. When the backend is bootstrapped, its Gradle build should provide a single local command set that CI can reuse.

| Control | Intent | Adoption condition |
| --- | --- | --- |
| Unit and integration tests | Verify business behavior, persistence, API contracts, and integrations | With the first executable backend module |
| Formatter check | Make code formatting deterministic | Choose one formatter during Gradle bootstrap and enforce it locally and in CI |
| Static analysis | Detect common correctness, style, and maintainability defects | Select a small compatible toolset after the first code slice; avoid overlapping tools without a clear benefit |
| Architecture tests | Guard module boundaries and forbidden dependency directions | Add when at least two backend modules make the rules executable |
| Dependency verification | Detect vulnerable or unexpected dependencies and constrain resolution | Configure dependency locking/verification and vulnerability review once dependencies exist |
| Secret scanning | Prevent credential commits | Enable repository/CI secret scanning before the first integration credentials are used |

The selected tools, versions, thresholds, and exceptions must be documented in the backend bootstrap pull request. A check must fail on a meaningful violation; do not add status-only CI steps.

## When to make an exception

Apply SOLID, DRY, KISS, and YAGNI to improve change safety, not as independent goals. An exception is appropriate when strict application adds more complexity than it removes.

Examples:

- keep a small duplication local while the two cases have not yet proven to share a stable abstraction;
- use a direct collaborator instead of a port for pure local behavior;
- keep a simple transaction synchronous instead of introducing events and eventual consistency;
- defer a shared abstraction until there are at least two real consumers with the same stable need.

Document the reason, alternatives considered, and revisit trigger in the pull request. Escalate to an ADR if the exception changes a cross-module rule, security boundary, or long-lived technology choice.
