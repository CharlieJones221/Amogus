package io.github.some_example_name

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ScreenViewport
import io.github.some_example_name.Player
import org.omg.CORBA.portable.OutputStream
import java.net.Socket
import java.sql.DriverManager.println

class GameScreen : Screen {

    private lateinit var world: World
    private lateinit var player: Player
    private lateinit var viewport: ScreenViewport
    private lateinit var camera: OrthographicCamera
    private lateinit var batch: SpriteBatch
    private lateinit var assetManager: AssetManager
    private lateinit var map: TiledMap
    private lateinit var renderer: OrthogonalTiledMapRenderer

    // Joystick
    private lateinit var stage: Stage
    private lateinit var touchpad: Touchpad

    //client-server
    lateinit var clientSocket : Socket

    // Screen/world sizing
    private var PlayerX = 1500f
    private var PlayerY = 1500f
    private val ScreenWidth = 800f
    private val ScreenHeight = 600f
    private val WorldWidth = 3000f
    private val WorldHeight = 3000f

    override fun show() {
        val clientSocket = Socket("152.105.66.105", 4300)
        val outputStream: java.io.OutputStream = clientSocket.outputStream
        println("Connected to server")
        outputStream.write(5318008)
        outputStream.flush()



        // World and camera setup
        world = World(Vector2(0f, -9.8f), true)
        camera = OrthographicCamera()
        viewport = ScreenViewport(camera)
        batch = SpriteBatch()

        // Map loading
        map = TmxMapLoader().load("GameMap.tmx")
        renderer = OrthogonalTiledMapRenderer(map, 5f)

        // Joystick setup
        stage = Stage(ScreenViewport())
        val bgTexture = Texture("JoystickSplitted.png")
        val knobTexture = Texture("LargeHandleFilledGrey.png")
        val touchpadStyle = Touchpad.TouchpadStyle().apply {
            background = TextureRegionDrawable(bgTexture)
            knob = TextureRegionDrawable(knobTexture)
        }
        touchpad = Touchpad(10f, touchpadStyle)
        touchpad.setBounds(50f, 50f, 200f, 200f)
        stage.addActor(touchpad)

        // Center player
        PlayerX = WorldWidth / 2
        PlayerY = WorldHeight / 2
        player = Player(Vector2(PlayerX, PlayerY))

        // Input processor
        Gdx.input.inputProcessor = InputMultiplexer(stage)
    }

    override fun render(delta: Float) {
        world.step(delta, 6, 2)

        // Joystick input to move player
        val direction = Vector2(touchpad.knobPercentX, touchpad.knobPercentY)
        player.move(direction, delta)

        val playerPos = player.getPosition()
        PlayerX = playerPos.x
        PlayerY = playerPos.y

        // Clear screen
        ScreenUtils.clear(0f, 0f, 0f, 1f)

        // Update camera to follow player
        camera.position.x = PlayerX.coerceIn(ScreenWidth / 2, WorldWidth - ScreenWidth / 2)
        camera.position.y = PlayerY.coerceIn(ScreenHeight / 2, WorldHeight - ScreenHeight / 2)
        camera.update()

        // Render map
        renderer.setView(camera)
        renderer.render()

        // Render player
        batch.begin()
        player.draw(batch)
        batch.end()

        // Draw UI
        stage.act(delta)
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
        stage.viewport.update(width, height, true)
    }

    override fun pause() {}
    override fun resume() {}
    override fun hide() {}

    override fun dispose() {
        batch.dispose()
        world.dispose()
        stage.dispose()
        player.dispose()
        renderer.dispose()
        map.dispose()
        clientSocket.close()
    }
}
