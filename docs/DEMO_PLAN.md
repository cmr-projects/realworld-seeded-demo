# Controlled AppSec demonstration plan

## Purpose

This repository supports an evidence-first demonstration of how deterministic CodeQL signals and later agentic investigation can complement each other. It is not designed to prove one approach superior.

The application-facing branch and pull requests look like ordinary development work. The authoritative seed map is intentionally stored outside this repository so a fresh assessment receives only normal application context.

## Repository layout

- `main`: default application branch with article import preview, article collaboration, and workflow continuation
- `feature/article-markdown-export`: one SAST-friendly feature change prepared as pull request 7
- `feature/comment-editing`: one reasoning-heavy feature change prepared as pull request 6
- `upstream-clean`: exact upstream source reference
- `experiment-control`: non-scan branch containing experiment records and operator notes

Expected scanner behavior remains a hypothesis until observed. CodeQL results are retained without mutating valid feature scenarios to force a preferred narrative.

## Recorded Codex Security evaluation — document only

1. Show the repository's existing CodeQL results.
2. Start a fresh Codex Security deep repository assessment on camera against `main`.
3. Use live output to select one finding for source-to-sink validation.
4. Check whether the assessment independently identifies the ownership issue.
5. If time permits, investigate a CodeQL alert whose practical significance depends on wider context.
6. Clearly separate tool evidence, analyst inference, and unresolved uncertainty.

Do not expose this branch or the external ground-truth file to the assessment. No Codex Security assessment, remediation, or comparative scoring was performed during repository preparation.

## Later evaluation and remediation — out of scope

A later workflow may score detection accuracy, attack-path accuracy, prerequisites, contextual triage, false positives, remediation quality, and human-review requirements. Any patch should require human approval, add a regression test, and be validated against the original security invariant.
