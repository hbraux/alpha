# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

**alpha** — a 2D horizontal shooter written in Kotlin using [LibGDX](https://libgdx.com/).

- **Language:** Kotlin 2.0.21, targeting JVM 17
- **Game framework:** LibGDX 1.12.1 (desktop backend via LWJGL3)
- **Build system:** Maven

## Commands

```bash
# Build
mvn compile

# Run tests
mvn test

# Run a single test class
mvn test -Dtest=MyTestClass

# Package
mvn package
```

## Architecture

LibGDX games follow a lifecycle managed by `ApplicationListener` (or its subclass `Game`). The typical structure:

- **Entry point** — creates an `Lwjgl3Application` with a config and an `ApplicationListener` implementation.
- **Screens** — `Screen` implementations handle per-state logic (menu, gameplay, game-over). The active screen receives `render(delta)` calls each frame.
- **Game loop** — `render(delta)` drives update + draw each frame; `delta` is seconds since last frame.
- **Assets** — loaded via `AssetManager` or directly; must be disposed in `dispose()`.
- **Input** — polled via `Gdx.input` or handled via `InputProcessor`.

LibGDX is not thread-safe; all OpenGL calls must happen on the render thread.
