# CodeQL observations

This file records actual GitHub CodeQL results. Predictions are maintained outside the repository.

## Clean upstream baseline

- Branch analyzed: `main` at the pinned upstream commit
- Configuration: GitHub CodeQL default setup, Java/Kotlin and Actions, standard GitHub-hosted runner
- Query suite: extended
- Run: `33328281659`, completed successfully
- Open alerts: 3
  - `java/spring-disabled-csrf-protection` in `SecurityConfiguration.java` (high)
  - `actions/unpinned-tag` in the upstream build workflow (medium)
  - `actions/missing-workflow-permissions` in the upstream build workflow (medium)

The two workflow-hygiene alerts are addressed on `demo-main` by setting explicit read-only permissions and pinning actions to immutable commits. The CSRF alert remains baseline context: the application uses token-header authentication rather than browser session cookies, but the disabled control still warrants explicit deployment-context review.

## Seeded branches

Results will be recorded after each pull-request analysis completes. Differences from predictions will be retained rather than tuned away.
