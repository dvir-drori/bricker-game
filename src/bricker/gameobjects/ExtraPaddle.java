package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class ExtraPaddle extends Paddle{


	private final GameObjectCollection gameObjects;
	private final int maxHits;
	private int hitsTaken;
	public ExtraPaddle(Vector2 topLeftCorner, Vector2 dimensions,
					   Renderable renderable, UserInputListener inputListener,
					   Vector2 windowDimensions, GameObjectCollection gameObjects, int maxHits) {
		super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions);
		this.gameObjects = gameObjects;

		this.maxHits = maxHits;
		this.hitsTaken = 0;
	}

	/**
	 * when there is a collision between extra paddle and a ball hits taken will update. when hits taken is 4 extra paddle will disappear
	 * @param other
	 * @param collision
	 */
	@Override
	public void onCollisionEnter(GameObject other, Collision collision) {
		super.onCollisionEnter(other, collision);
		hitsTaken++;
		if (hitsTaken >= maxHits){
			gameObjects.removeGameObject(this);
		}
	}
}
