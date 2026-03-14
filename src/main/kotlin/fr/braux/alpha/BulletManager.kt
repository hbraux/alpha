package fr.braux.alpha

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle

private const val BULLET_SPEED      = 700f
private const val BULLET_W          = 10f
private const val BULLET_H          = 4f
private const val EXPLOSION_SIZE    = 80f
private const val EXPLOSION_DURATION = 1f

private data class Bullet(var x: Float, val y: Float) {
    val rect get() = Rectangle(x, y - BULLET_H / 2f, BULLET_W, BULLET_H)
}

private data class Explosion(val x: Float, val y: Float, var timer: Float = EXPLOSION_DURATION)

class BulletManager {

    private val explosionTexture = Texture(Gdx.files.internal("sprites/explode.png"))
    private val bullets    = mutableListOf<Bullet>()
    private val explosions = mutableListOf<Explosion>()

    fun fire(x: Float, y: Float) {
        bullets += Bullet(x, y)
    }

    fun update(delta: Float, enemies: EnemyManager, meteors: MeteorManager) {
        val dead = mutableListOf<Bullet>()

        for (bullet in bullets) {
            bullet.x += BULLET_SPEED * delta
            if (bullet.x > SCREEN_WIDTH) { dead += bullet; continue }

            val rect = bullet.rect

            val hitCenter = enemies.removeFirstHit(rect)
            if (hitCenter != null) {
                explosions += Explosion(hitCenter.x - EXPLOSION_SIZE / 2f, hitCenter.y - EXPLOSION_SIZE / 2f)
                dead += bullet
                continue
            }

            if (meteors.isHitBy(rect)) {
                dead += bullet
            }
        }

        bullets.removeAll(dead)
        explosions.forEach  { it.timer -= delta }
        explosions.removeAll { it.timer <= 0f }
    }

    fun drawBullets(shapes: ShapeRenderer) {
        shapes.begin(ShapeRenderer.ShapeType.Filled)
        shapes.color = Color.YELLOW
        for (b in bullets) shapes.rect(b.x, b.y - BULLET_H / 2f, BULLET_W, BULLET_H)
        shapes.end()
    }

    fun drawExplosions(batch: SpriteBatch) {
        batch.begin()
        for (e in explosions) batch.draw(explosionTexture, e.x, e.y, EXPLOSION_SIZE, EXPLOSION_SIZE)
        batch.end()
    }

    fun dispose() = explosionTexture.dispose()
}
