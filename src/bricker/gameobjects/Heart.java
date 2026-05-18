package bricker.gameobjects;

import bricker.main.LivesCounter;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Falling heart pickup spawned by ExtraLifeCollisionStrategy.
 * Collides only with the main paddle (filtered by tag, no instanceof),
 * grants a life up to the configured maximum, and removes itself
 * when collected or when it leaves the screen.
 */
public class Heart extends GameObject {
	private final LivesCounter livesCounter;
	private final GameObjectCollection gameObjects;
	private final int maxLives;
	private final float windowHeight;

	/**
	 * Constructs a new Heart pickup.
	 *
	 * @param topLeftCorner     Initial position of the heart.
	 * @param dimensions        Size of the heart.
	 * @param renderable        Image of the heart.
	 * @param livesCounter      Shared counter tracking player lives.
	 * @param gameObjects       Collection managing all active game objects.
	 * @param maxLives          Maximum allowed number of lives.
	 * @param windowHeight      Height of the game window.
	 */
	public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
				 LivesCounter livesCounter, GameObjectCollection gameObjects,
				 int maxLives, float windowHeight) {
		super(topLeftCorner, dimensions, renderable);
		this.livesCounter = livesCounter;
		this.gameObjects = gameObjects;
		this.maxLives = maxLives;
		this.windowHeight = windowHeight;
	}

	/**
	 * Determines whether this heart can collide with another object.
	 * <p>
	 * The heart only collides with the main paddle (identified by tag).
	 *
	 * @param other The other game object.
	 * @return true if the other object is the main paddle.
	 */
	@Override
	public boolean shouldCollideWith(GameObject other) {
		return super.shouldCollideWith(other)
				&& Paddle.MAIN_PADDLE_TAG.equals(other.getTag());
	}

	/**
	 * Handles collision with the paddle.
	 * <p>
	 * When collected, the heart is removed from the game and the player's life
	 * count is increased (if below the maximum limit).
	 *
	 * @param other     The object that collided with the heart.
	 * @param collision The collision details.
	 */
	@Override
	public void onCollisionEnter(GameObject other, Collision collision) {
		super.onCollisionEnter(other, collision);
		gameObjects.removeGameObject(this);
		if (livesCounter.getValue() < maxLives) {
			livesCounter.increment();
		}
	}

	/**
	 * Updates the heart each frame.
	 * <p>
	 * If the heart falls below the bottom of the screen, it is removed.
	 *
	 * @param deltaTime Time elapsed since last frame.
	 */
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		if (getCenter().y() > windowHeight) {
			gameObjects.removeGameObject(this);
		}
	}
}