package fr.braux.alpha

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration

fun main() {
    val config = Lwjgl3ApplicationConfiguration().apply {
        setTitle("Alpha")
        setWindowedMode(SCREEN_WIDTH, SCREEN_HEIGHT)
        setForegroundFPS(60)
        useVsync(true)
    }
    Lwjgl3Application(AlphaGame(), config)
}
