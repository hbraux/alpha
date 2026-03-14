package fr.braux.alpha

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import kotlin.random.Random

private const val SPAWN_INTERVAL_MIN = 1.5f
private const val SPAWN_INTERVAL_MAX = 3.5f
private const val ENEMY_WIDTH        = 48f
private const val ENEMY_HEIGHT       = 32f

private class Enemy(texture: Texture) {
    val sprite = Sprite(texture).apply {
        setSize(ENEMY_WIDTH, ENEMY_HEIGHT)
        setOriginCenter()
        rotation = -90f
        x = SCREEN_WIDTH.toFloat()
        y = Random.nextFloat() * (SCREEN_HEIGHT - ENEMY_HEIGHT)
    }

    fun update(delta: Float) {
        sprite.x -= SCROLL_SPEED * delta
    }

    val isOffScreen get() = sprite.x + sprite.width < 0f
}

class EnemyManager {

    private val texture     = Texture(Gdx.files.internal("sprites/enemy.png"))
    private val enemies     = mutableListOf<Enemy>()
    private var spawnTimer  = nextSpawnInterval()

    fun update(delta: Float) {
        enemies.forEach { it.update(delta) }
        enemies.removeAll { it.isOffScreen }

        spawnTimer -= delta
        if (spawnTimer <= 0f) {
            enemies += Enemy(texture)
            spawnTimer = nextSpawnInterval()
        }
    }

    fun draw(batch: SpriteBatch) {
        batch.begin()
        enemies.forEach { it.sprite.draw(batch) }
        batch.end()
    }

    fun collidesWith(rect: Rectangle) = enemies.any { it.sprite.boundingRectangle.shrink(8f).overlaps(rect) }

    /** Removes the first enemy hit by [rect] and returns its center, or null if no hit. */
    fun removeFirstHit(rect: Rectangle): Vector2? {
        val hit = enemies.firstOrNull { it.sprite.boundingRectangle.shrink(8f).overlaps(rect) } ?: return null
        enemies.remove(hit)
        val b = hit.sprite.boundingRectangle
        return Vector2(b.x + b.width / 2f, b.y + b.height / 2f)
    }

    fun dispose() = texture.dispose()

    private fun nextSpawnInterval() =
        Random.nextFloat() * (SPAWN_INTERVAL_MAX - SPAWN_INTERVAL_MIN) + SPAWN_INTERVAL_MIN
}
