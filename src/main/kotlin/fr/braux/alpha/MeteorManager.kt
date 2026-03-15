package fr.braux.alpha

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import kotlin.random.Random

private const val SPAWN_INTERVAL_MIN = 0.8f
private const val SPAWN_INTERVAL_MAX = 2.5f
private const val SIZE_MIN           = 20f
private const val SIZE_MAX           = 60f

private class Meteor(texture: Texture) {
    val sprite = Sprite(texture).apply {
        val size = Random.nextFloat() * (SIZE_MAX - SIZE_MIN) + SIZE_MIN
        setSize(size, size)
        setOriginCenter()
        x = SCREEN_WIDTH.toFloat()
        y = Random.nextFloat() * (SCREEN_HEIGHT - size)
    }
    val rotationRate = Random.nextFloat() * 60f - 30f   // -30..+30 deg/s

    fun update(delta: Float) {
        sprite.x -= SCROLL_SPEED * delta
        sprite.rotate(rotationRate * delta)
    }

    val isOffScreen get() = sprite.x + sprite.width < 0f
}

class MeteorManager {

    private val texture  = Texture(Gdx.files.internal("sprites/meteor.png"))
    private val meteors  = mutableListOf<Meteor>()
    private var spawnTimer = nextSpawnInterval()

    fun update(delta: Float) {
        meteors.forEach { it.update(delta) }
        meteors.removeAll { it.isOffScreen }

        spawnTimer -= delta
        if (spawnTimer <= 0f) {
            meteors += Meteor(texture)
            spawnTimer = nextSpawnInterval()
        }
    }

    fun draw(batch: SpriteBatch) {
        batch.begin()
        meteors.forEach { it.sprite.draw(batch) }
        batch.end()
    }

    fun collidesWith(rect: Rectangle) = meteors.any { it.sprite.boundingRectangle.overlaps(rect) }

    fun isHitBy(rect: Rectangle) = meteors.any { it.sprite.boundingRectangle.overlaps(rect) }

    fun boundingRectangles(): List<Rectangle> = meteors.map { it.sprite.boundingRectangle }

    fun dispose() = texture.dispose()

    private fun nextSpawnInterval() =
        Random.nextFloat() * (SPAWN_INTERVAL_MAX - SPAWN_INTERVAL_MIN) + SPAWN_INTERVAL_MIN
}
