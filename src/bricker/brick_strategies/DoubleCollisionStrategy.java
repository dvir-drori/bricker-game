package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;

/**
 * A collision strategy that combines 2 collision strategies into one.
 * <p>
 * When activated, each contained strategy is executed sequentially on the same brick.
 */
public class DoubleCollisionStrategy implements CollisionStrategy{
	private CollisionStrategy[] strategies;
	private final GameObjectCollection gameObject;

	/**
	 * Constructs a collision strategy composed of 2 strategies.
	 *
	 * @param strategies Array of collision strategies to execute.
	 * @param gameObject The game object collection used to manage objects in the game.
	 */
	public DoubleCollisionStrategy(CollisionStrategy[]  strategies,GameObjectCollection gameObject){
		this.strategies = strategies;
		this.gameObject = gameObject;
	}

	/**
	 * Executes all contained collision strategies sequentially.
	 *
	 * @param brick  The brick that was hit.
	 * @param object The object that collided with the brick.
	 */
	@Override
	public void onCollision(Brick brick, GameObject object) {
		for (int i = 0; i < strategies.length; i++) {
			if (strategies[i]!=null){
				strategies[i].onCollision(brick, object);
				gameObject.addGameObject(brick);
			}
		}
		gameObject.removeGameObject(brick);
	}
}
