package fr.braux.alpha

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import kotlin.random.Random

private const val SPAWN_INTERVAL_MIN = 1.5f
private const val SPAWN_INTERVAL_MAX = 3.5f
private const val ENEMY_WIDTH        = 48f
private const val ENEMY_HEIGHT       = 32f
private const val MISSILE_SPEED      = 700f
private const val MISSILE_W          = 10f
private const val MISSILE_H          = 4f

private data class Missile(var x: Float, val y: Float) {
    val rect get() = Rectangle(x - MISSILE_W, y - MISSILE_H / 2f, MISSILE_W, MISSILE_H)
    val isOffScreen get() = x < 0f
}

private class Enemy(texture: Texture, spawnY: Float) {
    val sprite = Sprite(texture).apply {
        setSize(ENEMY_WIDTH, ENEMY_HEIGHT)
        setOriginCenter()
        rotation = -90f
        x = SCREEN_WIDTH.toFloat()
        y = spawnY
    }
    var hasFired = false

    val centerY get() = sprite.y + sprite.height / 2f

    fun update(delta: Float) {
        sprite.x -= SCROLL_SPEED * delta
    }

    val isOffScreen get() = sprite.x + sprite.width < 0f
}

class EnemyManager {

    private val texture        = Texture(Gdx.files.internal("sprites/enemy.png"))
    private val missileSound   = Gdx.audio.newSound(Gdx.files.internal("sounds/missile.ogg"))
    private val enemies        = mutableListOf<Enemy>()
    private val missiles    = mutableListOf<Missile>()
    private var spawnTimer  = nextSpawnInterval()

    fun update(delta: Float, shipRect: Rectangle, meteorRects: List<Rectangle>) {
        val shipCenterY = shipRect.y + shipRect.height / 2f

        for (enemy in enemies) {
            enemy.update(delta)
            if (!enemy.hasFired && shipCenterY in enemy.sprite.y..(enemy.sprite.y + enemy.sprite.height)) {
                missiles += Missile(enemy.sprite.x, enemy.centerY)
                missileSound.play()
                enemy.hasFired = true
            }
        }
        enemies.removeAll { it.isOffScreen }

        missiles.forEach { it.x -= MISSILE_SPEED * delta }
        missiles.removeAll { it.isOffScreen }

        spawnTimer -= delta
        if (spawnTimer <= 0f) {
            spawnEnemy(meteorRects)
            spawnTimer = nextSpawnInterval()
        }
    }

    fun missileCollidesWithShip(shipRect: Rectangle) = missiles.any { it.rect.overlaps(shipRect) }

    fun draw(batch: SpriteBatch) {
        batch.begin()
        enemies.forEach { it.sprite.draw(batch) }
        batch.end()
    }

    fun drawMissiles(shapes: ShapeRenderer) {
        shapes.begin(ShapeRenderer.ShapeType.Filled)
        shapes.color = Color.ORANGE
        for (m in missiles) shapes.rect(m.x - MISSILE_W, m.y - MISSILE_H / 2f, MISSILE_W, MISSILE_H)
        shapes.end()
    }

    fun collidesWith(rect: Rectangle) = enemies.any { it.sprite.boundingRectangle.shrink(8f).overlaps(rect) }

    /** Removes the first enemy hit by [rect] and returns its center, or null if no hit. */
    fun removeFirstHit(rect: Rectangle): Vector2? {
        val hit = enemies.firstOrNull { it.sprite.boundingRectangle.shrink(8f).overlaps(rect) } ?: return null
        enemies.remove(hit)
        val b = hit.sprite.boundingRectangle
        return Vector2(b.x + b.width / 2f, b.y + b.height / 2f)
    }

    fun dispose() {
        texture.dispose()
        missileSound.dispose()
    }

    private fun spawnEnemy(meteorRects: List<Rectangle>) {
        val candidateRect = Rectangle(SCREEN_WIDTH.toFloat(), 0f, ENEMY_WIDTH, ENEMY_HEIGHT)
        repeat(10) {
            val y = Random.nextFloat() * (SCREEN_HEIGHT - ENEMY_HEIGHT)
            candidateRect.y = y
            if (meteorRects.none { it.overlaps(candidateRect) }) {
                enemies += Enemy(texture, y)
                return
            }
        }
    }

    private fun nextSpawnInterval() =
        Random.nextFloat() * (SPAWN_INTERVAL_MAX - SPAWN_INTERVAL_MIN) + SPAWN_INTERVAL_MIN
}
