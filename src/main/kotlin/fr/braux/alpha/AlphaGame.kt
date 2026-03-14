package fr.braux.alpha

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch

const val SCREEN_WIDTH  = 800
const val SCREEN_HEIGHT = 480

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
