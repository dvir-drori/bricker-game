package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import danogl.GameObject;

/**
 * interface for collision strategies
 */
public interface CollisionStrategy {

	/**
	 * Called when a collision occurs between a brick and another game object.
	 *
	 * @param brick  The brick that was hit.
	 * @param object The object that collided with the brick.
	 */
	void onCollision(Brick brick, GameObject object);
}
