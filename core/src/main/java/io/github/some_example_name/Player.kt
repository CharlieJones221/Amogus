package io.github.some_example_name

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector
import com.badlogic.gdx.math.Vector2

class Player(startPosition: Vector2 = Vector2(100f, 100f))
{

   private val texture = Texture("PlayerCharacter.png")
   private val position = Vector2(100f,100f)
   private val speed = 200f

    fun update(delta: Float)
    {
        position.x += speed * delta

    }

    fun draw(batch: SpriteBatch)
    {
        batch.draw(texture, position.x, position.y)
    }

    fun dispose()
    {
        texture.dispose()
    }

    fun move(direction: Vector2, delta: Float) {
        position.x += direction.x * speed * delta
        position.y += direction.y * speed * delta
    }

    fun getPosition(): Vector2 {
        return position
    }

}
