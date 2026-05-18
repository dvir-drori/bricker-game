package bricker.brick_strategies;

import bricker.main.LivesCounter;
import bricker.gameobjects.Brick;
import bricker.gameobjects.Heart;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * A collision strategy that spawns a falling heart when a brick is destroyed.
 * <p>
 * When the brick is hit, it is removed from the game and replaced by a falling heart.
 * If the player collects the heart, it increases the number of lives (up to a limit).
 */
public class ExtraLifeCollisionStrategy implements CollisionStrategy{
	private static final int HEART_VELOCITY = 100;

	private final GameObjectCollection gameObject;
	private final Renderable heartImage;
	private final Vector2 heartDimensions;
	private final LivesCounter livesCounter;
	private final int maxLives;
	private final float windowHeight;

	/**
	 * Constructs a collision strategy that spawns an extra life pickup.
	 *
	 * @param gameObject      The game object collection used to manage objects in the game.
	 * @param heartImage      Image of the heart pickup.
	 * @param heartDimensions Size of the heart.
	 * @param livesCounter    Shared counter tracking the player's lives.
	 * @param maxLives        Maximum number of lives allowed.
	 * @param windowHeight    Height of the game window.
	 */
	public ExtraLifeCollisionStrategy(GameObjectCollection gameObject, Renderable heartImage,
									  Vector2 heartDimensions, LivesCounter livesCounter,
									  int maxLives, float windowHeight){
		this.gameObject = gameObject;
		this.heartImage = heartImage;
		this.heartDimensions = heartDimensions;
		this.livesCounter = livesCounter;
		this.maxLives = maxLives;
		this.windowHeight = windowHeight;
	}

	/**
	 * Handles collision by removing the brick and spawning a falling heart pickup.
	 *
	 * @param brick  The brick that was hit.
	 * @param object The object that collided with the brick.
	 */
	@Override
	public void onCollision(Brick brick, GameObject object) {
		gameObject.removeGameObject(brick, Layer.STATIC_OBJECTS);
		Heart heart = new Heart(Vector2.ZERO, heartDimensions, heartImage,
				livesCounter, gameObject, maxLives, windowHeight);
		heart.setCenter(brick.getCenter());
		heart.setVelocity(Vector2.DOWN.mult(HEART_VELOCITY));
		gameObject.addGameObject(heart);
	}
}