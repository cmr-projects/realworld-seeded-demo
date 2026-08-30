# Recorded-demo operator notes

1. Create a fresh Codex project from the clean scan repository with `main` checked out.
2. Do not continue, fork, share, or hand off a preparation task into the scan project.
3. Open the repository's Security / Code scanning view in another visible tab so existing CodeQL context is one click away.
4. Start recording with the `main` branch and CodeQL view visible.
5. In a fresh Codex task scoped only to the selected codebase, submit:

   `Use $codex-security:deep-security-scan to run a deep security scan of this repository. Treat it as a normal application under active development. Independently identify and validate plausible security vulnerabilities, clearly distinguish confirmed findings from uncertainty, and do not remediate.`

Do not provide this control branch, the external ground-truth file, or preparation-task history to the assessment. Do not request remediation during the recorded scan.

The first action after recording starts is step 5: visibly submit the fresh assessment request.
