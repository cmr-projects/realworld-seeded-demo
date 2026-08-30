# Recorded-demo operator notes

1. Open `cmr-projects/realworld-seeded-demo`.
2. Check out `demo/seeded-assessment`.
3. Open the repository's Security / Code scanning view in another visible tab so existing CodeQL context is one click away.
4. Start recording with the repository branch and CodeQL view visible.
5. In a new Codex task scoped only to this repository and branch, invoke the standard repository security-scan workflow and ask it to assess the full repository.

Do not provide the external ground-truth file to the assessment. Do not start from a task that has seen the seed map. Do not ask for remediation during the recorded assessment.
