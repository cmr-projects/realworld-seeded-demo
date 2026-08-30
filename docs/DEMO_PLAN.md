# Controlled AppSec demonstration plan

## Purpose

This repository supports an evidence-first comparison of deterministic CodeQL signals and later agentic investigation. It is not designed to prove one approach superior to the other.

The repository contains ordinary-looking feature changes arranged across two pull requests and a combined assessment branch. The authoritative seed map is intentionally stored outside this repository so a fresh assessment receives only normal repository context.

## Repository layout

- `upstream-clean`: exact upstream source reference
- `demo-main`: demo documentation, reliable CI, and the contextual-triage scenario
- `pr/codeql-detectable`: feature work expected to produce useful data-flow signals
- `pr/reasoning-heavy`: feature work whose security properties depend on ownership and workflow context
- `demo/seeded-assessment`: combined branch prepared for the recorded assessment

Expected scanner behavior is treated as a hypothesis. Observed CodeQL behavior is recorded without altering valid feature scenarios merely to force a preferred narrative.

## Part 2 - recorded Codex Security evaluation (not executed)

1. Show the repository's CodeQL configuration and observed results.
2. Start a fresh standard Codex Security repository assessment on camera.
3. Use live output to select one finding for source-to-sink validation.
4. Check whether the assessment independently identifies an ownership or business-logic issue.
5. If time permits, investigate a CodeQL alert whose practical significance depends on a compensating control.
6. Clearly separate tool evidence, analyst inference, and unresolved uncertainty.

No Codex Security assessment, remediation, or comparative scoring is part of the repository-build phase.

## Later evaluation and remediation (out of scope)

A later workflow may score detection accuracy, attack-path accuracy, prerequisites, contextual triage, false positives, remediation quality, and human-review requirements. Any patch should require human approval, add a regression test, and be validated against the original security invariant.
