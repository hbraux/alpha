# Missile

A **Missile** is a projectile fired by an **[Enemy](Enemy.md)** that travels leftward and ends the game on collision with the **[Ship](Ship.md)**.

## Requirements

1. Each **[Enemy](Enemy.md)** shall fire exactly one **Missile** leftward at 700 px/s when the **[Ship](Ship.md)**'s vertical centre crosses the **[Enemy](Enemy.md)**'s vertical bounds.
2. Each **[Enemy](Enemy.md)** must fire at most one **Missile** per gameplay session (a fired flag prevents repeat firing).
3. When a **Missile** collides with the **[Ship](Ship.md)**, the game must immediately transition to the **[GameOver](GameOver.md)** screen.
4. When an **[Enemy](Enemy.md)** fires a **Missile**, a missile sound must play.
