package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.Sound;

/**
 * A collision strategy that creates an explosion effect when a brick is hit.
 * <p>
 * When triggered, the brick is removed, an explosion sound is played, and all
 * neighboring bricks are also activated, potentially creating a chain reaction.
 */
public class ExplosionCollisionStrategy implements CollisionStrategy{

	private final GameObjectCollection gameObjects;
	private final Sound explosionSound;

	/**
	 * Constructs an explosion collision strategy.
	 *
	 * @param gameObjects    The game object collection used to manage objects in the game.
	 * @param explosionSound The sound effect played when the explosion occurs.
	 */
	public ExplosionCollisionStrategy(GameObjectCollection gameObjects, Sound explosionSound){
		this.gameObjects = gameObjects;
		this.explosionSound = explosionSound;
	}

	/**
	 * Handles collision by removing the brick, playing an explosion sound,
	 * and triggering all neighboring bricks to activate their collision logic.
	 *
	 * @param brick  The brick that was hit.
	 * @param object The object that collided with the brick.
	 */
	@Override
	public void onCollision(Brick brick, GameObject object) {
		gameObjects.removeGameObject(brick, Layer.STATIC_OBJECTS);
		explosionSound.play();
		for (Brick neighbor : brick.getNeighborBricks()){
			if (neighbor != null){
				neighbor.activateCollision(object);
			}
		}
	}
}
