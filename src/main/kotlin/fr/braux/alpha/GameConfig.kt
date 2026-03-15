package fr.braux.alpha

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.controllers.Controllers
import java.util.Properties

// Standard SDL gamepad d-pad button indices
private const val DPAD_UP    = 11
private const val DPAD_DOWN  = 12
private const val DPAD_LEFT  = 13
private const val DPAD_RIGHT = 14

object GameConfig {

    enum class Action(val default: String) {
        UP("W"), DOWN("S"), LEFT("A"), RIGHT("D"), FIRE("Space"), QUIT("Escape")
    }

    private val props: Properties by lazy {
        Properties().also { Gdx.files.internal("Game.properties").reader().use(it::load) }
    }

    private val device: String by lazy { props.getProperty("input.device", "keyboard") }

    private val keys: Map<Action, Int> by lazy {
        Action.entries.associateWith { action ->
            Keys.valueOf(props.getProperty("key.${action.name}", action.default))
        }
    }

    private var prevFireButton = false

    fun isJustPressed(action: Action): Boolean {
        if (device == "joystick") {
            if (action != Action.FIRE) return false
            val cur = Controllers.getControllers().firstOrNull()?.getButton(0) ?: false
            val fired = cur && !prevFireButton
            prevFireButton = cur
            return fired
        }
        return Gdx.input.isKeyJustPressed(keys.getValue(action))
    }

    fun isPressed(action: Action): Boolean {
        if (device == "joystick") {
            val controller = Controllers.getControllers().firstOrNull() ?: return false
            return when (action) {
                Action.UP    -> controller.getButton(DPAD_UP)    || controller.getAxis(1) < -0.7f
                Action.DOWN  -> controller.getButton(DPAD_DOWN)  || controller.getAxis(1) >  0.7f
                Action.LEFT  -> controller.getButton(DPAD_LEFT)  || controller.getAxis(0) < -0.7f
                Action.RIGHT -> controller.getButton(DPAD_RIGHT) || controller.getAxis(0) >  0.7f
                Action.FIRE  -> controller.getButton(0)
                Action.QUIT  -> false
            }
        }
        return Gdx.input.isKeyPressed(keys.getValue(action))
    }
}
