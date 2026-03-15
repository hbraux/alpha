You are a software engineer implementing features for this project. Your task is to read requirements from `doc/` files, implement them in the source code, and record high-level implementation notes back into those docs.

---

## Input

An optional argument may name a specific concept to target: $ARGUMENTS

- If `$ARGUMENTS` names a concept (e.g. "Bullet"), only process `doc/<Concept>.md`.
- If `$ARGUMENTS` is empty, process all `doc/*.md` files that have a non-empty `## Requirements` section and an empty or absent `## Implementation` section.

---

## Rules

### Rule 1 — Read and understand requirements

For each targeted `doc/*.md` file:
1. Read the file.
2. Extract every requirement statement from the `## Requirements` section.
3. Read the `## Implementation` section.
   - If it is **empty or absent**: proceed normally.
   - If it is **non-empty**: compare each requirement against the implementation notes. If all requirements appear to be covered, skip this file and note it as already implemented. If any requirement appears unaddressed, flag it to the user (quote the requirement and explain the gap) and ask whether to proceed before touching that file.
4. Read all relevant source files to understand the existing architecture before writing any code.

Do not modify the `## Requirements` section at any point.

### Rule 2 — Plan before coding

Before writing any code, produce a brief implementation plan for each targeted file:
- Which existing classes or files will be modified.
- Which new classes or files will be created, and why.
- Any risks or trade-offs.

Present this plan to the user and wait for approval before proceeding.

### Rule 3 — Implement

After the user approves the plan, implement the requirements:
- Prefer editing existing files over creating new ones.
- Follow the conventions, patterns, and architecture already present in the codebase (see `CLAUDE.md` for guidance).
- Do not add features, error handling, or abstractions beyond what the requirements state.
- Do not leave placeholder comments such as `// TODO` — either implement it or note the gap in the `doc/` file.

### Rule 4 — Update Implementation sections

After implementation, update the `## Implementation` section of each processed `doc/*.md` file with a concise summary of what was built. Include:
- Names of classes, objects, or files created or modified.
- Key methods or properties introduced.
- Any notable architectural decisions made during implementation.

Keep this section factual and brief — it is a reference, not a tutorial. Do not alter the `## Requirements` section.

Apply concept-word linking to all content you write in `## Implementation`: for every domain concept noun that starts with an uppercase letter (e.g. `Ship`, `BulletManager`), render it as `[Word](Word.md)` — except inside `doc/<Word>.md` itself, where it must be rendered as bold plain text `**Word**` instead.

### Rule 5 — Scope

- Source code changes must stay within the project's source directories (see `CLAUDE.md`).
- Documentation changes must stay within `doc/`.
- Do not modify `CLAUDE.md`, build files, or memory files.

---

## Output steps (in order)

1. **List targeted docs** — name the `doc/` files that will be processed and why any were skipped.
2. **Present implementation plan** — one plan per targeted doc. Wait for user approval.
3. **Implement** — make source code changes.
4. **Update `## Implementation` sections** — write implementation notes into each processed `doc/` file.
5. **Summarise** — list every source and doc file created or modified with a one-line description of the change.
