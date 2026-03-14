package fr.braux.alpha

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

class GameScreen(private val game: AlphaGame) : ScreenAdapter() {

    private val shapes = ShapeRenderer()

    // World units per second
    private val playerSpeed = 200f

    // Player position (world coordinates, origin = bottom-left)
    private var playerX = 80f
    private var playerY = SCREEN_HEIGHT / 2f

    // Player size
    private val playerW = 40f
    private val playerH = 20f

    override fun render(delta: Float) {
        handleInput(delta)
        update(delta)
        draw()
    }

    private fun handleInput(delta: Float) {
        if (Gdx.input.isKeyPressed(Keys.UP)    || Gdx.input.isKeyPressed(Keys.W))
            playerY += playerSpeed * delta
        if (Gdx.input.isKeyPressed(Keys.DOWN)  || Gdx.input.isKeyPressed(Keys.S))
            playerY -= playerSpeed * delta
        if (Gdx.input.isKeyPressed(Keys.LEFT)  || Gdx.input.isKeyPressed(Keys.A))
            playerX -= playerSpeed * delta
        if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D))
            playerX += playerSpeed * delta
    }

    private fun update(delta: Float) {
        // Clamp player inside the viewport
        playerX = playerX.coerceIn(0f, SCREEN_WIDTH  - playerW)
        playerY = playerY.coerceIn(0f, SCREEN_HEIGHT - playerH)
    }

    private fun draw() {
        Gdx.gl.glClearColor(0f, 0f, 0.1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        shapes.begin(ShapeRenderer.ShapeType.Filled)

        // Player ship (simple triangle)
        shapes.color = Color.CYAN
        shapes.triangle(
            playerX,           playerY + playerH / 2,   // nose
            playerX - playerW, playerY + playerH,        // top-left
            playerX - playerW, playerY                   // bottom-left
        )

        shapes.end()
    }

    override fun dispose() {
        shapes.dispose()
    }
}
