# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

**alpha** — a 2D horizontal shooter written in Kotlin using [LibGDX](https://libgdx.com/).

- **Language:** Kotlin 2.2.20, targeting JVM 17
- **Game framework:** LibGDX 1.12.1 (desktop backend via LWJGL3)
- **Build system:** Maven
- Window: 1280×720, 60 FPS with VSync


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


## Architecture

All source is under `src/main/kotlin/fr/braux/alpha/`. The game follows LibGDX's `Game`/`Screen` pattern:

- **`AlphaGame`** — extends `Game`; owns the shared `SpriteBatch`, defines global constants (`SCREEN_WIDTH`, `SCREEN_HEIGHT`, `SCROLL_SPEED = 130f`).
- **`GameScreen`** — main gameplay screen; orchestrates all managers, runs the `handleInput → update → draw` loop, and handles collisions.
- **`GameOverScreen`** — shown on player death; SPACE restarts, ESC quits.

### Manager pattern

Entity systems follow a consistent structure: a private inner class for individual entities, and a public manager class that owns a mutable list and exposes `update(delta)`, `draw(batch/shapes)`, `dispose()`, and collision methods.

| Manager | Entities | Collision API |
|---|---|---|
| `BulletManager` | player bullets, explosions | `removeFirstHit(rect)` → returns hit center |
| `MeteorManager` | meteors | `collidesWith(rect)`, `isHitBy(rect)`, `boundingRectangles()` |
| `EnemyManager` | enemies, enemy missiles | `collidesWith(rect)`, `removeFirstHit(rect)`, `missileCollidesWithShip(rect)` |
| `ScrollingBackground` | star layers (parallax) | — |

`GameScreen` is the only place that wires managers together for collision resolution.

### Render order (z-order in `GameScreen.draw`)

Background → meteors → enemies → player bullets → explosions → enemy missiles → player ship → UI (score)

### Input

`GameConfig` (singleton) centralises all input. The enum `Action` (UP, DOWN, LEFT, RIGHT, FIRE, QUIT) maps to keyboard keys or joystick buttons. The active device is set in `src/main/resources/Game.properties` (`input.device=keyboard` or `joystick`).

### Constants

Global constants (`SCREEN_WIDTH`, `SCREEN_HEIGHT`, `SCROLL_SPEED`) live in `AlphaGame.kt`. Per-manager constants (spawn intervals, entity sizes, speeds) are defined locally inside each manager file.
