package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;

/**
 * A basic collision strategy where the brick is simply removed upon collision.
 */

public class BasicCollisionStrategy implements CollisionStrategy{
	private final GameObjectCollection gameObject;
	/**
	 * Constructs a basic collision strategy.
	 *
	 * @param gameObject Collection managing all active game objects.
	 */
	public BasicCollisionStrategy(GameObjectCollection gameObject){
		this.gameObject = gameObject;
	}

	/**
	 * Handles collision by removing the brick from the game.
	 *
	 * @param brick  The brick that was hit.
	 * @param object The object that collided with the brick.
	 */
	@Override
	public void onCollision(Brick brick, GameObject object) {
		gameObject.removeGameObject(brick, Layer.STATIC_OBJECTS);
	}
}
