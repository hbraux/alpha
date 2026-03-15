# Configuration

## Requirements

The **Configuration** file specifies runtime settings for the game, including the active input device ([Keyboard](Keyboard.md) or [Joystick](Joystick.md)) and its mappings of physical keys or buttons to abstract action names (LEFT, RIGHT, UP, DOWN, FIRE, QUIT).

## Implementation

Implemented as `Game.properties` in `src/main/resources/`. Loaded at first use by the `GameConfig` singleton object (`GameConfig.kt`).

`GameConfig` declares an `Action` enum (UP, DOWN, LEFT, RIGHT, FIRE, QUIT) with a hardcoded default key name per action. On first access its `keys` lazy property reads `config.properties` via `Gdx.files.internal()`, resolves each `key.<ACTION>` value to a LibGDX keycode using `Input.Keys.valueOf()`, and stores the result as a `Map<Action, Int>`.

Two helpers — `isJustPressed(action)` and `isPressed(action)` — delegate to `Gdx.input`. GameScreen and GameOverScreen use these instead of hardcoded `Keys.*` constants.

The `input.device` property is present in the file for future Joystick support but is not yet consumed.
