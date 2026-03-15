# Keyboard

The **Keyboard** is one of the supported input devices used to control the Ship. The mapping of physical keys to abstract action names is defined in the Configuration file.

## Requirements

1. The **Keyboard** shall support the abstract actions UP, DOWN, LEFT, RIGHT, FIRE, and QUIT.
2. The mapping of physical keys to these abstract actions shall be defined in the [Configuration](Configuration.md) file.
3. During gameplay, FIRE triggers the [Ship](Ship.md) to fire one [Bullet](Bullet.md) and QUIT exits the game immediately.
4. On the [GameOver](GameOver.md) screen, FIRE starts a fresh session and QUIT exits the game.
