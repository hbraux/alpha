# GameOver

The **GameOver** screen is displayed when the **[Ship](Ship.md)** is destroyed, allowing the player to restart or quit.

## Requirements

1. When the **[Ship](Ship.md)** collides with a **[Meteor](Meteor.md)**, an **[Enemy](Enemy.md)**, or a **[Missile](Missile.md)**, the game must immediately transition to the **GameOver** screen.
2. The **GameOver** screen must display a prominent centred message indicating that the game has ended.
3. The **GameOver** screen must display a secondary centred hint message indicating the actions the player may take.
4. When the player presses SPACE on the **GameOver** screen, the game must discard the current session and start a fresh one, returning to active gameplay.
5. When the player presses ESC on the **GameOver** screen, the game must exit.
6. The **GameOver** screen must render a plain dark background, without the scrolling **[Background](Background.md)** star layers.
