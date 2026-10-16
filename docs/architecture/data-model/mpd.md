# MPD — proposed PostgreSQL design

## Status and scope

This is a proposed physical design for the current identity/search-profile scope. It is not executable SQL and no Flyway migration is created by this document. Values marked **proposed** require confirmation when the Spring Boot backend, authentication method, and first UI are selected.

## Minimal identity boundary

| Table | Proposed columns | Constraints and ownership |
| --- | --- | --- |
| `user_accounts` | `id uuid`, `external_subject varchar(255)`, timestamps | `id` PK; **proposed** unique `external_subject` once the identity provider is selected. Owned by the identity module. No passwords, tokens, or provider claims belong in search-profiles tables. |

## Search-profile tables

| Table | Principal columns | Key constraints |
| --- | --- | --- |
| `search_profiles` | `id uuid`, `owner_user_id uuid`, `name varchar(120)`, `normalized_name varchar(120)`, `status varchar(16)`, `version bigint`, `created_at timestamptz`, `updated_at timestamptz` | PK `id`; FK `owner_user_id -> user_accounts(id)`; UQ `(owner_user_id, normalized_name)`; check `status IN ('ACTIVE','PAUSED')`; check `version >= 0`. |
| `search_profile_target_titles` | `profile_id uuid`, `position smallint`, `value varchar(160)`, `normalized_value varchar(160)` | PK `(profile_id, position)`; FK with `ON DELETE CASCADE`; UQ `(profile_id, normalized_value)`; check `position >= 0`. |
| `search_profile_keywords` | `profile_id uuid`, `position smallint`, `value varchar(160)`, `normalized_value varchar(160)` | PK `(profile_id, position)`; FK with `ON DELETE CASCADE`; UQ `(profile_id, normalized_value)`; check `position >= 0`. |
| `search_profile_locations` | `profile_id uuid`, `position smallint`, `label varchar(160)`, `normalized_label varchar(160)`, `country_code char(2) null` | PK `(profile_id, position)`; FK with `ON DELETE CASCADE`; check country code format when present. `REMOTE` must not be stored here. |
| `search_profile_work_modes` | `profile_id uuid`, `work_mode varchar(16)` | PK `(profile_id, work_mode)`; FK with `ON DELETE CASCADE`; check allowed `ONSITE`, `HYBRID`, `REMOTE`. |
| `search_profile_contract_types` | `profile_id uuid`, `contract_type varchar(20)` | PK `(profile_id, contract_type)`; FK with `ON DELETE CASCADE`; check allowed MVP enum values. |
| `search_profile_compensation_preferences` | `profile_id uuid`, `minimum_amount numeric(12,2) null`, `maximum_amount numeric(12,2) null`, `currency char(3)`, `period varchar(10)` | PK/FK `profile_id`; check that at least one amount exists, every supplied amount is positive, and `minimum_amount <= maximum_amount` when both exist; check ISO-style uppercase currency and allowed period. |
| `search_profile_criteria` | `id uuid`, `profile_id uuid`, `position smallint`, `description varchar(500)`, `importance varchar(10)` | PK `id`; FK with `ON DELETE CASCADE`; UQ `(profile_id, position)`; check `importance IN ('REQUIRED','PREFERRED')`; check `position >= 0`. |

## Indexes

- `search_profiles(owner_user_id, status, updated_at DESC)` for owner-scoped lists.
- A unique expression index on `(profile_id, normalized_label, COALESCE(country_code, ''))` for locations, so a missing country code participates consistently in de-duplication.
- The unique constraints above also support duplicate detection per profile/owner.
- Do not add full-text, geospatial, vector, or matching indexes before an approved query pattern requires them.

## Integrity split

Database constraints protect relational and local value integrity. The aggregate/application layer protects rules spanning child tables, including “at least one title and one location”, collection size limits, normalization policy, and owner-scoped authorization. This avoids premature triggers while keeping critical checks close to their appropriate boundary.

## Deletion and retention boundary

`ON DELETE CASCADE` is limited to configuration rows owned by the profile. It must not be extended to future recommendation, application, document, or audit tables. When such a module is introduced, it must define whether it retains a snapshot, an optional reference, or a restricted archive, and must document its legal and product retention basis.

## Flyway implementation order

1. Create the canonical identity table or map an approved identity schema.
2. Create `search_profiles` and its owned tables in one migration, with foreign keys, checks, and indexes.
3. Add migrations only for validated new requirements; never modify a migration already shared with another environment.
