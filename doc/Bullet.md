# Bullet

A **Bullet** is a projectile fired by the **[Ship](Ship.md)** to destroy **[Enemy](Enemy.md)** objects.

## Requirements

1. When the player presses key SPACE, the **[Ship](Ship.md)** must fire exactly one **Bullet**.
2. Each **Bullet** must travel rightward at 700 px/s from the **[Ship](Ship.md)**'s nose position.
3. When a **Bullet** collides with a **[Meteor](Meteor.md)**, the **Bullet** must be removed immediately; the **[Meteor](Meteor.md)** shall not be destroyed.
4. When a **Bullet** collides with an **[Enemy](Enemy.md)**, the **[Enemy](Enemy.md)** must be destroyed, the **Bullet** must be removed, and an **[Explosion](Explosion.md)** must be displayed briefly at the **[Enemy](Enemy.md)**'s position.
5. When the **[Ship](Ship.md)** fires a **Bullet**, a shoot sound must play.
6. When an **[Enemy](Enemy.md)** is destroyed by a **Bullet**, an explosion sound must play.
