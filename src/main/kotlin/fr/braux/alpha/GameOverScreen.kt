package fr.braux.alpha

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout

class GameOverScreen(private val game: AlphaGame) : ScreenAdapter() {

    private val font = BitmapFont().apply {
        color = Color.RED
        data.setScale(3f)
        region.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
    }
    private val hint = BitmapFont().apply {
        color = Color.LIGHT_GRAY
        data.setScale(1.5f)
        region.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
    }
    private val layout = GlyphLayout()

    override fun render(delta: Float) {
        if (Gdx.input.isKeyJustPressed(Keys.SPACE)) game.setScreen(GameScreen(game))
        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) Gdx.app.exit()

        Gdx.gl.glClearColor(0f, 0f, 0.1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        game.batch.begin()

        layout.setText(font, "GAME OVER")
        font.draw(game.batch, layout,
            (SCREEN_WIDTH  - layout.width)  / 2f,
            (SCREEN_HEIGHT + layout.height) / 2f + 20f)

        layout.setText(hint, "SPACE to restart  •  ESC to quit")
        hint.draw(game.batch, layout,
            (SCREEN_WIDTH  - layout.width)  / 2f,
            (SCREEN_HEIGHT - layout.height) / 2f - 20f)

        game.batch.end()
    }

    override fun dispose() {
        font.dispose()
        hint.dispose()
    }
}
