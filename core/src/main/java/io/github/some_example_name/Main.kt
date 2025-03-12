package io.github.some_example_name

import com.badlogic.gdx.Game

class Main : Game() {
    override fun create() {
        setScreen(GameScreen())
    }
}
