# CodeQL observations

This file records actual GitHub CodeQL results. Predictions and authoritative ground truth are maintained outside the repository.

## Clean upstream baseline

- Commit: `a6677507bc6905bc48c359bd9c68a11706990151`
- CodeQL run: `33328281659`
- Open alerts: 3
  - `java/spring-disabled-csrf-protection` in `SecurityConfiguration.java` (high)
  - `actions/unpinned-tag` in the upstream build workflow (medium)
  - `actions/missing-workflow-permissions` in the upstream build workflow (medium)

The workflow-hygiene alerts were addressed by pinning actions and setting read-only permissions. The CSRF alert remains baseline context.

## Default branch

- Branch: `main`
- Commit: `98a588079c6064a5828a477204abb0241973ab85`
- CodeQL run: `33334549798`, completed successfully
- GitHub Actions build: `33334549786`, passed
- Open alerts:
  - alert 4: `java/ssrf` at the article-import URL stream open (critical)
  - alert 3: pre-existing `java/spring-disabled-csrf-protection` (high)
- No CodeQL alert was produced for article collaboration or the constrained workflow continuation.

## Pull request 7: Markdown article export

- Branch: `feature/article-markdown-export`
- Commit: `83a9649`
- CodeQL run: `33334561400`, completed successfully
- New alerts:
  - alert 5: `java/path-injection` at export-directory creation (high)
  - alert 6: `java/path-injection` at the Markdown file write (high)
- The PR CodeQL policy check fails because new high-severity alerts are present; build checks pass.

The two path alerts are separate sinks in one Markdown-export scenario.

## Pull request 6: comment editing

- Branch: `feature/comment-editing`
- Commit: `0bb8f63`
- CodeQL run: `33334550037`, completed successfully
- New seed-related alerts: 0
- CodeQL and build checks pass.

## Prediction discrepancies retained

- The constrained continuation was classified as uncertain and was not surfaced by CodeQL.
- The pre-existing disabled-CSRF alert is retained as the practical CHALLENGE example because its significance depends on token-header authentication, browser behavior, and deployment context.
- No valid scenario was weakened or reshaped to force a preferred scanner result.
