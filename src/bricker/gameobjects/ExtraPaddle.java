package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * A temporary paddle that behaves like a regular but can be removed
 * after a limited number of collisions.
 * This paddle tracks how many times it has been hit, and removes itself from the
 * game once it reaches the maximum allowed number of hits.
 */
public class ExtraPaddle extends Paddle{
	/**
	 * tag of extra paddle
	 */
	public static final String EXTRA_PADDLE_TAG = "ExtraPaddle";

	private final GameObjectCollection gameObjects;
	private final int maxHits;
	private int hitsTaken;

	/**
	 * Constructs an ExtraPaddle with a limited lifetime based on number of hits.
	 *
	 * @param topLeftCorner     Initial position of the paddle.
	 * @param dimensions        Size of the paddle.
	 * @param renderable        Image of the paddle.
	 * @param inputListener     Handles keyboard input for movement.
	 * @param windowDimensions  Size of the game window.
	 * @param gameObjects       Collection managing all active game objects.
	 * @param maxHits           Maximum number of allowed hits before removal.
	 */
	public ExtraPaddle(Vector2 topLeftCorner, Vector2 dimensions,
					   Renderable renderable, UserInputListener inputListener,
					   Vector2 windowDimensions, GameObjectCollection gameObjects, int maxHits) {
		super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions);
		this.gameObjects = gameObjects;

		this.maxHits = maxHits;
		this.hitsTaken = 0;
		setTag(EXTRA_PADDLE_TAG);
	}

	/**
	 * Handles collision events with other objects.
	 * Each collision increases the hit counter. Once the number of hits reaches
	 * the allowed limit, the paddle removes itself from the game.
	 *
	 * @param other     The object that collided with the paddle.
	 * @param collision The collision details.
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
