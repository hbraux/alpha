package fr.braux.alpha

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

class GameScreen(private val game: AlphaGame) : ScreenAdapter() {

    private val shapes     = ShapeRenderer()
    private val background = ScrollingBackground()
    private val meteors    = MeteorManager()
    private val enemies    = EnemyManager()
    private val bullets    = BulletManager()

    private val shipTexture = Texture(Gdx.files.internal("sprites/ship.png"))
    private val ship = Sprite(shipTexture).apply {
        setSize(60f, 40f)
        setOriginCenter()
        rotation = 90f
    }

    private val scoreFont = BitmapFont().apply {
        data.setScale(1.5f)
        region.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
    }
    private val scoreLayout = GlyphLayout()
    private var score = 0

    private val playerSpeed = 400f
    private var playerX = 80f
    private var playerY = SCREEN_HEIGHT / 2f - ship.height / 2f

    override fun render(delta: Float) {
        handleInput(delta)
        update(delta)
        draw()
    }

    private fun debugController() {
        val c = Controllers.getControllers().firstOrNull() ?: return
        (0..10).map { it to c.getAxis(it) }.filter { (_, v) -> Math.abs(v) > 0.1f }
            .takeIf { it.isNotEmpty() }?.let { Gdx.app.log("CTRL", "axes=$it") }
    }

    private fun handleInput(delta: Float) {
        debugController()
        if (GameConfig.isJustPressed(GameConfig.Action.QUIT))
            Gdx.app.exit()
        if (GameConfig.isJustPressed(GameConfig.Action.FIRE))
            bullets.fire(playerX + ship.width, playerY + ship.height / 2f)
        if (GameConfig.isPressed(GameConfig.Action.UP))
            playerY += playerSpeed * delta
        if (GameConfig.isPressed(GameConfig.Action.DOWN))
            playerY -= playerSpeed * delta
        if (GameConfig.isPressed(GameConfig.Action.LEFT))
            playerX -= playerSpeed * delta
        if (GameConfig.isPressed(GameConfig.Action.RIGHT))
            playerX += playerSpeed * delta
    }

    private fun update(delta: Float) {
        background.update(delta)
        meteors.update(delta)
        enemies.update(delta, ship.boundingRectangle, meteors.boundingRectangles())
        score += bullets.update(delta, enemies, meteors)
        playerX = playerX.coerceIn(0f, SCREEN_WIDTH  - ship.width)
        playerY = playerY.coerceIn(0f, SCREEN_HEIGHT - ship.height)
        ship.setPosition(playerX, playerY)
        val shipHitbox = ship.boundingRectangle
        if (meteors.collidesWith(shipHitbox) || enemies.collidesWith(shipHitbox) || enemies.missileCollidesWithShip(shipHitbox)) {
            game.setScreen(GameOverScreen(game))
        }
    }

    private fun draw() {
        Gdx.gl.glClearColor(0f, 0f, 0.1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        background.draw(shapes)
        meteors.draw(game.batch)
        enemies.draw(game.batch)
        bullets.drawBullets(shapes)
        enemies.drawMissiles(shapes)
        bullets.drawExplosions(game.batch)

        game.batch.begin()
        ship.draw(game.batch)
        scoreLayout.setText(scoreFont, "SCORE  $score")
        scoreFont.draw(game.batch, scoreLayout,
            SCREEN_WIDTH - scoreLayout.width - 12f,
            SCREEN_HEIGHT - 10f)
        game.batch.end()
    }

    override fun dispose() {
        shapes.dispose()
        shipTexture.dispose()
        scoreFont.dispose()
        meteors.dispose()
        enemies.dispose()
        bullets.dispose()
    }
}
