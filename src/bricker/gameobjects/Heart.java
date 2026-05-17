package bricker.gameobjects;

import bricker.LivesCounter;
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

	public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
				 LivesCounter livesCounter, GameObjectCollection gameObjects,
				 int maxLives, float windowHeight) {
		super(topLeftCorner, dimensions, renderable);
		this.livesCounter = livesCounter;
		this.gameObjects = gameObjects;
		this.maxLives = maxLives;
		this.windowHeight = windowHeight;
	}

	@Override
	public boolean shouldCollideWith(GameObject other) {
		return super.shouldCollideWith(other)
				&& Paddle.MAIN_PADDLE_TAG.equals(other.getTag());
	}

	@Override
	public void onCollisionEnter(GameObject other, Collision collision) {
		super.onCollisionEnter(other, collision);
		gameObjects.removeGameObject(this);
		if (livesCounter.getValue() < maxLives) {
			livesCounter.increment();
		}
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		if (getCenter().y() > windowHeight) {
			gameObjects.removeGameObject(this);
		}
	}
}