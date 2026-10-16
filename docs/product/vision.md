# Product vision

## Problem

Searching for work fragments effort across job boards, saved offers, CV versions, cover letters, and follow-up tasks. Generic automation risks poor applications and loss of user control.

## Product intent

JobPilot AI helps a job seeker discover relevant opportunities, organize them, tailor application material, and automate approved actions with explicit human control.

## Initial scope

- Personal use by one job seeker.
- Maintain multiple search profiles, each with its own target roles, locations, preferences, and criteria.
- Import or capture job offers from user-approved sources.
- Rank and explain offer relevance using the selected search profile and available offer data. Missing or ambiguous experience criteria must reduce certainty or be disclosed; they must not silently exclude an otherwise relevant offer.
- Assist with CV and cover-letter tailoring in French and English.
- Track application status and next actions.
- Start every application workflow with human review and approval before any submission. Later automation remains controlled and opt-in.

## Non-goals for the first increment

- Fully autonomous mass application sending.
- Scraping or bypassing third-party platform restrictions.
- A public marketplace, recruiter portal, or multi-tenant administration UI.
- Designing a microservice platform before product needs justify it.

## Product principles

1. **User control first.** Irreversible or external actions require clear approval.
2. **Explain recommendations.** A recommendation should identify the relevant signals, limits, and uncertainty.
3. **Handle incomplete criteria fairly.** Missing experience requirements are an uncertainty to explain, not an automatic rejection.
4. **Protect sensitive data.** CVs, application documents, and provider credentials require least-privilege handling.
5. **Progressive automation.** Start with assisted workflows, then automate repeatable approved steps.
6. **Multi-user ready, not multi-user first.** Preserve ownership boundaries and authorization concepts without building unused tenancy features.

## Success signals to define before implementation

- A job seeker can use more than one search profile to capture, prioritize, and follow up on opportunities from one place.
- Recommendations remain explainable when experience criteria are absent or incomplete.
- Tailored French and English document drafts are reviewable and traceable to an offer and profile.
- Automation is auditable, opt-in, and does not submit before initial human approval.
- The architecture supports adding authenticated users without a structural rewrite.
