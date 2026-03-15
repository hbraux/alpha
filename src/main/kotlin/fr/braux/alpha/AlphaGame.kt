package fr.braux.alpha

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch

const val SCREEN_WIDTH   = 1280
const val SCREEN_HEIGHT  = 720
const val SCROLL_SPEED   = 130f

class AlphaGame : Game() {

    lateinit var batch: SpriteBatch
        private set

    override fun create() {
        batch = SpriteBatch()
        setScreen(GameScreen(this))
    }

    override fun dispose() {
        screen?.dispose()
        batch.dispose()
    }
}
