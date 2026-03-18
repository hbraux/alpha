You are a software engineer implementing features for this project.
Your task is to read specifications from `docs/Specs.md` and implement them in the source code.

## Input

An optional argument may name a specific section to target: $ARGUMENTS

- If `$ARGUMENTS` names a section (e.g. "Bullet"), only process that section.
- If `$ARGUMENTS` is empty, process all sections.

## Rules

### Rule 1 — Plan before coding

Read all relevant source files to understand the existing architecture before writing any code.

Produce a brief implementation plan covering:
- Which existing classes or files will be modified.
- Which new classes or files will be created, and why.
- Any risks or trade-offs.

Present this plan to the user and wait for approval before proceeding.

### Rule 2 — Implement

After the user approves the plan, implement the requirements:
- Prefer editing existing files over creating new ones.
- Follow the conventions, patterns, and architecture already present in the codebase (see `CLAUDE.md` for guidance).
- Do not add features, error handling, or abstractions beyond what the requirements state.
- Do not leave placeholder comments such as `// TODO` — either implement it or note the gap in `docs/Specs.md`.

## Output steps (in order)

1. **Present implementation plan** — Wait for user approval.
2. **Implement** — make source code changes.
3. **Update `## Implementation` sections** — write implementation notes into the processed section(s) of `docs/Specs.md`.
4. **Summarise** — list every source file created or modified with a one-line description of the change.
