package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;

/**
 * basic collision strategy. when collides brick vanish.
 */

public class BasicCollisionStrategy implements CollisionStrategy{
	private final GameObjectCollection gameObject;
	public BasicCollisionStrategy(GameObjectCollection gameObject){
		this.gameObject = gameObject;
	}
	/**
	 * @param brick
	 * @param object
	 */
	@Override
	public void onCollision(Brick brick, GameObject object) {
		gameObject.removeGameObject(brick, Layer.STATIC_OBJECTS);

	}

}
