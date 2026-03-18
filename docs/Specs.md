# Specs

## Gameplay

- The player controls the Ship using the configured input device to navigate within the 2D game window.
- The game world scrolls horizontally; Enemy and Meteor entities spawn on the right side of the screen and move leftward.

## Configuration

- The Configuration file defines runtime settings for the game.
- It specifies the active input device: Keyboard or Joystick.
- It defines the mapping of physical keys or controller buttons to the abstract action names LEFT, RIGHT, UP, DOWN, FIRE, and QUIT.

## Controls

### Keyboard

- The Keyboard supports the abstract actions UP, DOWN, LEFT, RIGHT, FIRE, and QUIT.
- The mapping of physical keys to these abstract actions is defined in the Configuration file.
- During gameplay, FIRE triggers the Ship to fire one Bullet and QUIT exits the game immediately.
- On the GameOver screen, FIRE starts a fresh session and QUIT exits the game.

### Joystick

- When the Joystick is selected as the active input device, the Ship is controlled via the first connected controller.
- The mapping of controller buttons to abstract actions is defined in the Configuration file.
- On the GameOver screen, FIRE starts a fresh session and QUIT exits the game.

## Ship

- The Ship is the player-controlled spacecraft.

## Background

- The Background is the scrolling parallax star field rendered behind all game entities.
- The Background is rendered as the first visual layer each frame, drawn before all game entities.
- The Background consists of three parallax star layers, each scrolling at a distinct speed to create a depth illusion.

## Enemy

- Each Enemy spawns on the right edge of the screen and moves leftward at scroll speed.
- Each Enemy fires exactly one Missile leftward when the Ship's vertical centre crosses the Enemy's vertical bounds.
- Each Enemy may fire at most one Missile per gameplay session.

## Missile

- A Missile travels leftward from the Enemy that fired it.
- When an Enemy fires a Missile, a missile sound plays.

## Meteor

- A Meteor is an obstacle that spawns on the right side of the screen and moves leftward.


## Bullet

- When the player performs FIRE, the Ship fires exactly one Bullet.
- Each Bullet travels rightward from the Ship's nose position.
- When a Bullet collides with a Meteor, the Bullet is removed immediately; the Meteor is not destroyed.
- When a Bullet collides with an Enemy, the Enemy is destroyed, the Bullet is removed, and an Explosion is displayed briefly at the Enemy's position.
- When the Ship fires a Bullet, a shoot sound plays.
- When an Enemy is destroyed by a Bullet, an explosion sound plays.

## Explosion

- The Explosion is displayed briefly, then removed automatically.
- The Explosion does not affect gameplay; it is a purely visual effect.

## Score

- The Score tracks the number of Enemies destroyed by the Ship during a gameplay session.
- The Score increments by 1 for each Enemy destroyed by a Bullet.
- The Score is displayed in the top-right corner of the screen at all times during gameplay.

## GameOver

- When the Ship collides with a Meteor, an Enemy, or a Missile, the game immediately transitions to the GameOver screen.
- The GameOver screen displays a prominent centred message indicating that the game has ended.
- The GameOver screen displays a secondary centred hint message indicating the actions available to the player.
- When the player performs FIRE on the GameOver screen, the game discards the current session and starts a fresh one, returning to active gameplay.
- When the player performs QUIT on the GameOver screen, the game exits.
- The GameOver screen renders a plain dark background, without the scrolling Background star layers.
