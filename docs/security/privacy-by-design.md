# Privacy by design

## Purpose and status

This document is a design checklist for JobPilot AI, not legal advice and not a completed compliance assessment. It must be reviewed with qualified legal/privacy advice before processing real user data or enabling providers that receive personal documents.

The GDPR principles include lawfulness, fairness, transparency, purpose limitation, data minimisation, and storage limitation. See [GDPR Article 5 on EUR-Lex](https://eur-lex.europa.eu/eli/reg/2016/679) and [CNIL guidance on minimisation](https://www.cnil.fr/fr/minimiser-les-donnees-collectees).

## Planned personal-data categories

| Category | Examples | Sensitivity/design treatment |
| --- | --- | --- |
| Account and identity | Internal user ID, authentication subject, contact address if later needed | Keep identity data minimal and separate from profile content. |
| Job-search preferences | Target roles, skills, locations, work modes, contracts, compensation preference, criteria | Can reveal professional situation or preferences; collect only what the feature needs. |
| Application and document data | CV, cover letters, work history, education, contact details | High sensitivity in practice; protect as confidential documents and limit access. |
| Provider/integration data | Requests and responses sent to AI, job sources, n8n, document processors | Minimise fields, record provider purpose, and prevent unnecessary retention. |
| Security and audit data | Authentication/security events, access decisions, minimal operational metadata | Do not log CV content, API keys, tokens, prompts, or full personal payloads. |

The product must not request special-category data unless a concrete, reviewed feature requires it. Users may nevertheless include sensitive or third-party information in free-text criteria or documents; controls must account for that possibility.

## Purpose limitation and data minimisation

Before collecting a field, document its feature purpose, recipient, retention trigger, and whether it is required or optional. Search profiles need only the preferences necessary to discover and rank opportunities; they do not need identity credentials, exact geolocation, or documents.

Do not reuse profile or document data for model training, marketing, unrelated analytics, or provider evaluation unless a separately reviewed purpose, lawful basis, user information, and safeguards permit it.

## Processing purposes and legal basis to determine

The controller must determine and document a lawful basis per processing purpose before production use. The table below is a design prompt, not a legal conclusion.

| Purpose | Data likely involved | Basis to determine with legal review |
| --- | --- | --- |
| Provide account, profile, job-search, and user-requested document features | Identity, search preferences, documents | Assess the appropriate basis for the user-requested service, including contractual necessity where applicable. |
| Secure service and prevent abuse | Account identifiers, minimal security logs | Assess the appropriate basis and proportionality, including legitimate interests where applicable. |
| Send a document or trigger automation after approval | Selected profile/application/document data | Assess the basis, information, and proof of the user’s approval for the particular action. |
| Use an AI provider to generate a user-requested draft or recommendation | Minimized prompt/document extracts | Assess basis, necessity, provider role, transfer safeguards, and user information for each provider/feature. |

Consent is not assumed as a universal basis simply because a product has a user interface. Determine it treatment by treatment with legal advice.

## Rights and product capabilities

Design product and support processes that can locate data by user and handle requests for access, rectification, export/portability where applicable, restriction/objection where applicable, and erasure. The exact scope, exceptions, identity verification process, and response deadlines require legal review.

- **Access/export:** produce an understandable export of profile data and, later, relevant documents/metadata without exposing other users or secrets.
- **Rectification:** let users correct profile and account data; preserve only the history justified by a separate purpose.
- **Erasure:** delete active data unless retention is necessary and documented. A request must propagate to owned stores and processors where applicable, subject to validated exceptions.
- **Transparency:** publish clear notices that identify purposes, categories, recipients/processors, retention criteria, transfers, and rights.

## Retention, deletion, and purge

Retention periods are not selected in this repository. For each category, define an event-based retention rule, review it before launch, communicate it transparently, and implement an observable purge process. The CNIL explains that data cannot be retained indefinitely and that the retention period must be set according to the processing purpose: [CNIL retention guidance](https://www.cnil.fr/fr/passer-laction/les-durees-de-conservation-des-donnees).

For search profiles, deletion removes configuration owned by that module. Future application or recommendation modules must own their own historical snapshot/reference and retention policy; profile deletion must not silently delete their records. Backups, logs, caches, and processors need documented deletion or expiry behavior as well.

## CVs and personal documents

- Store documents separately from ordinary profile fields, with per-user authorization and least-privilege access.
- Scan uploads, enforce type/size limits, generate server-side names, and avoid public object URLs.
- Do not place document text, email addresses, phone numbers, or full CV contents in application logs, analytics, error reports, or prompts unless strictly necessary and reviewed.
- Define document retention, download access, export, deletion, and provider-sharing behavior before enabling upload.

## AI providers, processors, and international transfers

For each AI, storage, workflow, document-processing, or analytics provider, maintain a processor inventory: service, purpose, categories, location/transfer path, contract/DPA status, subprocessors, retention, security controls, and deletion/export capability. Minimise prompts; prefer structured extracts over full documents when they satisfy the use case.

Transfers outside the EEA and processor contracts require case-specific assessment. The CNIL notes that transfers include communication, copying, or moving personal data to a third country and that guarantees must be documented; see [CNIL transfer guidance](https://www.cnil.fr/fr/transferts-de-donnees-hors-ue-derogations-pour-des-situations-particulieres). Processor arrangements also require review under the applicable contract requirements; see [CNIL guidance on controller/processor clauses](https://www.cnil.fr/fr/clauses-contractuelles-types-entre-responsable-de-traitement-et-sous-traitant).

## AIPD/DPIA assessment trigger

Before activating profiling, large-scale data, sensitive documents, systematic monitoring, automated decisions with significant effects, or novel AI flows, evaluate whether a Data Protection Impact Assessment (AIPD/DPIA) is required. A DPIA is required before processing likely to create a high risk to people’s rights and freedoms; it is also a useful design tool even where not mandatory. See [CNIL AIPD guidance](https://www.cnil.fr/fr/RGPD-analyse-impact-protection-des-donnees-aipd) and [EDPB DPIA information](https://www.edpb.europa.eu/topics/accountability-and-compliance-tools/data-protection-impact-assessment_en).

The initial assessment should examine profiling/recommendation logic, document sensitivity, data scale, vulnerable users if relevant, provider transfers, automated action, and the residual risk after safeguards. A legal/privacy professional must decide whether a formal AIPD, consultation, or other regulatory step is required.

## Compliance decisions requiring legal review

- controller/processor roles and applicable jurisdiction;
- privacy notice, lawful basis, and user-information wording for each purpose;
- retention schedule, backups, legal holds, and deletion evidence;
- data-subject request process and verified identity procedure;
- processor agreements, subprocessors, international transfers, and AI-provider terms;
- AIPD need and the safeguards for recommendation, document, and automation features;
- incident/breach response obligations and contact roles.
