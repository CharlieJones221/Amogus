package io.github.some_example_name

import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ScreenViewport
import java.util.Vector
import javax.swing.Renderer

class GameScreen: Screen
{
    private lateinit var world : World
    private lateinit var player : Player
    private lateinit var viewport: ScreenViewport
    private lateinit var camera : OrthographicCamera
    private lateinit var batch : SpriteBatch
    private lateinit var shapeRenderer: ShapeRenderer
    private lateinit var assetManager : AssetManager

    private lateinit var map : TiledMap
    private lateinit var renderer : OrthogonalTiledMapRenderer

    private var PlayerX = 1500f
    private var PlayerY = 1500f
    private var ScreenWidth = 800f
    private var ScreenHeight = 600f
    private var WorldWidth = 3000f
    private var WorldHeight = 3000f


    override fun dispose()
    {
        batch.dispose()
        world.dispose()
    }

    override fun show()
    {
        map  = TmxMapLoader().load("GameMap.tmx")
        renderer = OrthogonalTiledMapRenderer(map, 5f)
        batch = SpriteBatch()
        world =  World(Vector2(0f, -9.8f), true)
        camera = OrthographicCamera()
        viewport = ScreenViewport(camera)
        shapeRenderer = ShapeRenderer()
        player = Player()
//        renderer.setView(camera)

//        assetManager = AssetManager()
////
//        assetManager.load("GameMap.tmx", )
//
//        assetManager.finishLoading()

//        val texture = Texture("Castle2.png")
    }

    override fun render(delta: Float)
    {
        world.step(delta,6,2)
        player.update(delta)

        ScreenUtils.clear(0f,0f,0f,1f)
        batch.begin()

        camera.position.x = PlayerX.coerceIn(ScreenWidth/2, WorldWidth - ScreenWidth/2)
        camera.position.y = PlayerY.coerceIn(ScreenHeight/2, WorldHeight-ScreenHeight/2 )

        camera.update()
        renderer.setView(camera)
        renderer.render()

//        player.draw(batch)
        batch.end()
    }

    override fun resize(width: Int, height: Int)
    {
        viewport.update(width, height)
    }

    override fun pause()
    {
        TODO("Not yet implemented")
    }

    override fun resume()
    {
        TODO("Not yet implemented")
    }

    override fun hide()
    {
        TODO("Not yet implemented")
    }
}
