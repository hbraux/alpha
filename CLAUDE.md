# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

**alpha** — a 2D horizontal shooter written in Kotlin using [LibGDX](https://libgdx.com/).

- **Language:** Kotlin 2.2.20, targeting JVM 17
- **Game framework:** LibGDX 1.12.1 (desktop backend via LWJGL3)
- **Build system:** Maven

## Commands

```bash
just          # list available goals
just build    # compile
just run      # compile and run

mvn test
mvn test -Dtest=MyTestClass
```

Sources are in `src/main/kotlin`, declared via `<sourceDirs>` inside the kotlin-maven-plugin (not via `<sourceDirectory>` — that was intentionally removed to stay idiomatic).

## Architecture

### Screens

`DesktopLauncher.kt` creates the `Lwjgl3Application` and hands off to `AlphaGame`, which extends `Game`, owns the shared `SpriteBatch`, and starts on `GameScreen`. Screen transitions use `game.setScreen(...)`.

`GameScreen` drives the main loop with a strict `handleInput → update → draw` order each frame. `GameOverScreen` is shown on death; SPACE restarts a fresh `GameScreen`, ESC quits.

### Shared constants (`AlphaGame.kt`)

- `SCREEN_WIDTH = 1280`, `SCREEN_HEIGHT = 720`
- `SCROLL_SPEED = 130f` — horizontal scroll speed used by all rightward-spawning entities
- `Rectangle.shrink(amount)` extension — returns a new rectangle inset on all sides, used to reduce hitboxes

### Manager pattern

All game entities follow the same pattern — spawn off the right edge, move left, remove when off-screen:

- **`MeteorManager`** — spawns `sprites/meteor.png` every 0.8–2.5s at random Y and size (20–60px), rotates slowly. Exposes `collidesWith(rect)` (with 8px shrink) and `isHitBy(rect)` (unshrunk, for bullet hits) and `boundingRectangles()` (used to avoid enemy overlap on spawn).
- **`EnemyManager`** — spawns `sprites/enemy.png` every 1.5–3.5s. On spawn, tries up to 10 random Y positions to avoid overlapping any meteor. Each enemy fires one missile leftward (700 px/s, orange rect) when the ship's Y center crosses its vertical bounds (`hasFired` flag). Exposes `collidesWith`, `removeFirstHit` (returns center `Vector2` of destroyed enemy), `missileCollidesWithShip`, and `drawMissiles(shapes)`.
- **`BulletManager`** — SPACE fires a yellow bullet (700 px/s) from the ship's nose. Hit enemy → explosion sprite shown for 1s, kill counted. Hit meteor → bullet disappears only. `update()` returns kill count for that frame, which `GameScreen` accumulates into the score displayed top-right.
- **`ScrollingBackground`** — three star layers at 40/80/150 px/s with varying brightness for parallax. Drawn first, before all entities.

### Draw order in `GameScreen`

1. `background.draw(shapes)` — star layers
2. `meteors.draw(batch)` — meteor sprites
3. `enemies.draw(batch)` — enemy sprites
4. `bullets.drawBullets(shapes)` — yellow bullet rects
5. `enemies.drawMissiles(shapes)` — orange missile rects
6. `bullets.drawExplosions(batch)` — explosion sprites
7. `batch.begin()` → ship sprite + score HUD → `batch.end()`

### Assets

| Path | Contents |
|---|---|
| `src/main/resources/sprites/` | `ship.png`, `enemy.png`, `meteor.png`, `explode.png` |
| `src/main/resources/sounds/` | `shoot.ogg`, `explode.ogg`, `missile.ogg` |

Ship sprite faces up in the asset; rotated 90° at load time. Enemy sprite rotated -90°.

LibGDX is not thread-safe; all OpenGL/rendering calls must happen on the render thread.
