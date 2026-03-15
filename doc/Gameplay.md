# Gameplay

This is a horizontal 2D shooter game.

## Requirements

1. The player shall control the **[Ship](Ship.md)** using **[Keyboard](Keyboard.md)** input to move within the 2D window.
2. The game world shall scroll horizontally; **[Enemy](Enemy.md)** and **[Meteor](Meteor.md)** entities shall spawn on the right side of the screen and move leftward.
3. The **[Ship](Ship.md)** must not collide with any **[Enemy](Enemy.md)**, **[Meteor](Meteor.md)**, or **[Missile](Missile.md)**; any such collision shall immediately transition the game to the **[GameOver](GameOver.md)** screen.
4. The **[Background](Background.md)** shall be rendered as the first visual layer each frame, drawn before all game entities.
5. The **[Background](Background.md)** shall consist of three parallax star layers, each scrolling at a distinct speed to create a depth illusion.
