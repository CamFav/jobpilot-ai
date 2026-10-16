# MCD — identity and search profiles

## Scope and status

This conceptual data model covers only the minimal identity boundary and search-profile module defined today. It is not a schema for opportunities, recommendations, applications, documents, or automation.

**Validated for the current design:** one user owns zero or more independent search profiles; a profile owns its repeatable configuration values.

**Proposed, to confirm at backend bootstrap:** the canonical identity entity is named `UserAccount`, uses an internal UUID, and exposes only a stable owner reference to the search-profiles module.

## Entities and associations

| Entity | Meaning | Identifier |
| --- | --- | --- |
| `UserAccount` | Minimal identity representation; credentials and provider-specific claims are outside this model. | `userId` |
| `SearchProfile` | One user’s independent job-search configuration. | `searchProfileId` |
| `TargetJobTitle` | A target title owned by one profile. | `(searchProfileId, position)` |
| `SkillKeyword` | A skill or keyword owned by one profile. | `(searchProfileId, position)` |
| `SearchLocation` | A geographic location owned by one profile. `REMOTE` is a work mode, not a location. | `(searchProfileId, position)` |
| `WorkModePreference` | A selected work mode (`ONSITE`, `HYBRID`, `REMOTE`). | `(searchProfileId, workMode)` |
| `ContractTypePreference` | A selected contract type. | `(searchProfileId, contractType)` |
| `CompensationPreference` | Optional compensation range owned by one profile. | `searchProfileId` |
| `ProfileCriterion` | A required condition or preferred criterion owned by one profile. | `criterionId` |

## Cardinalities

- A `UserAccount` owns **0..N** `SearchProfile`; each `SearchProfile` belongs to **exactly 1** `UserAccount`.
- A `SearchProfile` owns **1..N** `TargetJobTitle` and **1..N** `SearchLocation`.
- A `SearchProfile` owns **0..N** skills, work-mode preferences, contract-type preferences, and criteria.
- A `SearchProfile` owns **0..1** compensation preference.
- No relationship to future matching, application, or document entities is modeled here. A future module that needs profile context owns its own reference/snapshot and retention policy.

## Mermaid diagram

```mermaid
erDiagram
    USER_ACCOUNT ||--o{ SEARCH_PROFILE : owns
    SEARCH_PROFILE ||--|{ TARGET_JOB_TITLE : targets
    SEARCH_PROFILE ||--o{ SKILL_KEYWORD : includes
    SEARCH_PROFILE ||--|{ SEARCH_LOCATION : includes
    SEARCH_PROFILE ||--o{ WORK_MODE_PREFERENCE : selects
    SEARCH_PROFILE ||--o{ CONTRACT_TYPE_PREFERENCE : selects
    SEARCH_PROFILE ||--o| COMPENSATION_PREFERENCE : has
    SEARCH_PROFILE ||--o{ PROFILE_CRITERION : defines

    USER_ACCOUNT {
        uuid user_id PK
    }
    SEARCH_PROFILE {
        uuid search_profile_id PK
        uuid owner_user_id FK
        string name
        string status
    }
    TARGET_JOB_TITLE {
        smallint position PK
        string value
    }
    SEARCH_LOCATION {
        smallint position PK
        string label
        string country_code
    }
    PROFILE_CRITERION {
        uuid criterion_id PK
        string importance
        string description
    }
```

## Business interpretation

`REQUIRED` and `PREFERRED` describe different user intent. A required criterion is a declared non-negotiable condition; a preferred criterion can improve relevance but must not automatically reject an opportunity. The MCD deliberately does not define a future match-result association or scoring algorithm.
