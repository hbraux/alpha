# Joystick

A **Joystick** is an alternative input device that can control the Ship when selected via the Configuration file.

## Requirements

1. When the **Joystick** is selected as the active input device, the [Ship](Ship.md) shall be controlled via the first connected controller.
2. The d-pad of the controller shall map to the abstract actions UP, DOWN, LEFT, and RIGHT.
3. Button 0 of the controller shall map to the FIRE action.

## Implementation

Implemented inside [GameConfig](`GameConfig.kt`). When `input.device=joystick` is set in `Game.properties`, the `isPressed` and `isJustPressed` methods switch from keyboard polling to controller polling via the LibGDX `gdx-controllers` extension.

- `isPressed` reads `Controllers.getControllers().firstOrNull()` and maps `getPov(0)` (`PovDirection`) to UP/DOWN/LEFT/RIGHT, and `getButton(0)` to FIRE. QUIT always returns `false` (no joystick binding; keyboard ESC remains available).
- `isJustPressed` handles FIRE only: an edge-detect on `getButton(0)` using a `prevFireButton` field updated each call. D-pad actions are continuous and only need `isPressed`.
- If no controller is connected both methods return `false` silently.

> **Note:** requires the `gdx-controllers-core` and `gdx-controllers-lwjgl3` Maven dependencies (not yet in `pom.xml` — must be added manually before building with joystick support).
