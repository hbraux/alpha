# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

**alpha** — a 2D horizontal shooter written in Kotlin using [LibGDX](https://libgdx.com/).

- **Language:** Kotlin 2.2.20, targeting JVM 17
- **Game framework:** LibGDX 1.12.1 (desktop backend via LWJGL3)
- **Build system:** Maven

## Documentation

All implementation details — architecture, entity behaviour, draw order, constants, assets — are documented in `doc/`. **Do not add implementation details to this file.** Read the relevant `doc/*.md` files before implementing or modifying any feature. Each file has a `## Requirements` section (the authoritative spec) and a `## Implementation` section (high-level notes on how it was built).

Two skills help maintain this documentation workflow:

- `/doc <description>` — turns a plain-language description into structured requirements in `doc/` without touching source code.
- `/build [concept]` — reads `## Requirements` from `doc/` files, implements them in source code, and writes implementation notes back into `## Implementation`.

## Commands

```bash
just          # list available goals
just build    # compile
just run      # compile and run

mvn test
mvn test -Dtest=MyTestClass
```

Sources are in `src/main/kotlin`, declared via `<sourceDirs>` inside the kotlin-maven-plugin (not via `<sourceDirectory>` — that was intentionally removed to stay idiomatic).

LibGDX is not thread-safe; all OpenGL/rendering calls must happen on the render thread.
