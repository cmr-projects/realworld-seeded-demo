# Upstream baseline

## Provenance

- Upstream repository: `raeperd/realworld-springboot-java`
- Upstream branch: `main`
- Pinned commit: `a6677507bc6905bc48c359bd9c68a11706990151`
- Commit date: 2026-08-20T22:37:54+09:00
- Commit subject: `Bump gradle-wrapper from 9.6.1 to 9.7.0 (#75)`
- Clean reference branch: `upstream-clean`

The clean branch points directly to the upstream commit and contains no demo changes.

## Runtime and architecture

- Java 25, Spring Boot 4.1.0, Gradle wrapper 9.7.0
- Spring MVC, Spring Security, Spring Data JPA, Hibernate, and in-memory H2
- SQL schema initialized from `src/main/resources/schema.sql`
- JWT authentication through an `Authorization: Token <jwt>` header and HMAC-SHA256 serialization
- Registration, login, profiles, article reads, and tag reads are public; remaining routes require authentication
- Controllers map HTTP payloads, domain services own transactions and policy calls, and Spring Data repositories own persistence
- `Article.author` records article ownership; comments belong to an article and an author; favorites and follows are user relationships
- Upstream article update and deletion enforce article ownership

## Verification

- Clean upstream build and tests: passed
- Packaged application startup on port 8080: passed
- Registration and login: passed
- Authenticated current-user lookup: passed
- Authenticated article create, read, update, and delete: passed

## Existing security context

The upstream contains security-relevant implementation choices that are not demo seeds, including a source-code JWT signing secret and a disabled-CSRF CodeQL alert. Baseline and seeded CodeQL results are recorded separately in `docs/CODEQL_RESULTS.md`.
