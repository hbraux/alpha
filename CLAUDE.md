# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

**alpha** — a 2D horizontal shooter written in Kotlin using [LibGDX](https://libgdx.com/).

- **Language:** Kotlin 2.2.20, targeting JVM 17
- **Game framework:** LibGDX 1.12.1 (desktop backend via LWJGL3)
- **Build system:** Maven

## Commands

Prefer `just` over raw Maven:

```bash
just          # list available goals
just build    # compile
just run      # compile and run

# Tests
mvn test
mvn test -Dtest=MyTestClass
```

Sources live in `src/main/kotlin` (declared via `<sourceDirectory>` in `pom.xml`; the Kotlin Maven plugin picks this up automatically).

## Architecture

**Entry point:** `DesktopLauncher.kt` — creates an `Lwjgl3Application` with window config and hands off to `AlphaGame`.

**`AlphaGame`** extends `Game` and owns the shared `SpriteBatch`. On `create()` it transitions to `GameScreen`. Screen transitions go through `game.setScreen(...)`.

**`GameScreen`** is the main gameplay screen. Its `render(delta)` follows a strict `handleInput → update → draw` order each frame:
- `handleInput` — reads arrow/WASD keys, mutates player position
- `update` — advances game state (background scroll, clamp player to viewport)
- `draw` — clears screen, draws background, draws player

**`ScrollingBackground`** — three `StarLayer`s at different speeds (40 / 80 / 150 px/s) and brightness for parallax depth. Each layer owns its star array; stars wrap at `x < 0` back to `x += SCREEN_WIDTH`. Owns its own `shapes.begin/end` block and must be drawn before game entities.

**Screen dimensions** — `SCREEN_WIDTH` / `SCREEN_HEIGHT` constants are defined in `AlphaGame.kt` and used across files.

LibGDX is not thread-safe; all OpenGL/rendering calls must happen on the render thread.
