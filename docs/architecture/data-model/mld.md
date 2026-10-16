# MLD — identity and search profiles

## Scope and notation

This logical model transforms the [MCD](mcd.md) into relations, keys, and ownership boundaries. Names are proposed for the first PostgreSQL implementation and must be aligned with the selected identity solution.

`PK` is a primary key; `FK` is a foreign key; `UQ` is a unique constraint.

## Validated logical relations

```text
USER_ACCOUNT(
  user_id PK
)

SEARCH_PROFILE(
  search_profile_id PK,
  owner_user_id FK -> USER_ACCOUNT.user_id,
  name,
  normalized_name,
  status,
  version,
  created_at,
  updated_at,
  UQ(owner_user_id, normalized_name)
)

SEARCH_PROFILE_TARGET_TITLE(
  search_profile_id PK/FK -> SEARCH_PROFILE.search_profile_id,
  position PK,
  value,
  normalized_value,
  UQ(search_profile_id, normalized_value)
)

SEARCH_PROFILE_KEYWORD(
  search_profile_id PK/FK -> SEARCH_PROFILE.search_profile_id,
  position PK,
  value,
  normalized_value,
  UQ(search_profile_id, normalized_value)
)

SEARCH_PROFILE_LOCATION(
  search_profile_id PK/FK -> SEARCH_PROFILE.search_profile_id,
  position PK,
  label,
  normalized_label,
  country_code
)

SEARCH_PROFILE_WORK_MODE(
  search_profile_id PK/FK -> SEARCH_PROFILE.search_profile_id,
  work_mode PK
)

SEARCH_PROFILE_CONTRACT_TYPE(
  search_profile_id PK/FK -> SEARCH_PROFILE.search_profile_id,
  contract_type PK
)

SEARCH_PROFILE_COMPENSATION_PREFERENCE(
  search_profile_id PK/FK -> SEARCH_PROFILE.search_profile_id,
  minimum_amount,
  maximum_amount,
  currency,
  period
)

SEARCH_PROFILE_CRITERION(
  criterion_id PK,
  search_profile_id FK -> SEARCH_PROFILE.search_profile_id,
  position,
  description,
  importance,
  UQ(search_profile_id, position)
)
```

## Ownership and deletion rules

- The identity module owns `USER_ACCOUNT` and authentication/credential details. The search-profiles module stores only `owner_user_id` and never owns or edits identity credentials.
- The search-profiles module owns all `SEARCH_PROFILE_*` relations. Its child rows may cascade when the profile itself is physically deleted.
- Location de-duplication is an application invariant in the MVP. The physical model proposes a PostgreSQL expression index so a missing country code is treated consistently.
- No future module may rely on database-level cascade from `SEARCH_PROFILE` to its own history. Future tables may carry a nullable profile reference and/or an immutable profile snapshot, under their own deletion and retention rule.
- Application authorization always scopes profile operations to the authenticated owner. A foreign key supports integrity but does not replace authorization.

## Items proposed for confirmation

- The final identity-table name and whether it is application-managed or mapped from an external identity provider.
- Whether normalized location uniqueness needs a separate administrative region or source identifier later; the MVP stores only a user-entered geographic label and optional country code.
- Whether a non-deleted historical reference needs a soft-delete state after dependent modules exist. Do not add that state before a concrete retention use case exists.
