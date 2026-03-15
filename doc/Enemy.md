# Enemy

An **Enemy** is a hostile spacecraft that spawns on the right side of the screen and moves leftward.

## Requirements

1. Each **Enemy** shall spawn on the right edge of the screen and move leftward at scroll speed.
2. Each **Enemy** shall fire exactly one **[Missile](Missile.md)** leftward when the **[Ship](Ship.md)**'s vertical centre crosses the **Enemy**'s vertical bounds.
3. When an **Enemy** is hit by a **[Bullet](Bullet.md)**, the **Enemy** must be destroyed and an **[Explosion](Explosion.md)** must be displayed briefly at its position.
