package fr.braux.alpha

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import kotlin.random.Random

private data class Star(var x: Float, val y: Float, val size: Float)

private class StarLayer(count: Int, val speed: Float, val color: Color) {
    val stars = Array(count) {
        Star(
            x    = Random.nextFloat() * SCREEN_WIDTH,
            y    = Random.nextFloat() * SCREEN_HEIGHT,
            size = Random.nextFloat() * 1.5f + 0.5f
        )
    }

    fun update(delta: Float) {
        for (star in stars) {
            star.x -= speed * delta
            if (star.x < 0f) star.x += SCREEN_WIDTH
        }
    }

    fun draw(shapes: ShapeRenderer) {
        shapes.color = color
        for (star in stars) {
            shapes.circle(star.x, star.y, star.size)
        }
    }
}

class ScrollingBackground {

    private val layers = listOf(
        StarLayer(count = 80,  speed = 40f,  color = Color(0.4f, 0.4f, 0.5f, 1f)),  // far, dim
        StarLayer(count = 40,  speed = 80f,  color = Color(0.7f, 0.7f, 0.8f, 1f)),  // mid
        StarLayer(count = 15,  speed = 150f, color = Color(1f,   1f,   1f,   1f)),  // near, bright
    )

    fun update(delta: Float) = layers.forEach { it.update(delta) }

    fun draw(shapes: ShapeRenderer) {
        shapes.begin(ShapeRenderer.ShapeType.Filled)
        layers.forEach { it.draw(shapes) }
        shapes.end()
    }
}
