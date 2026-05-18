package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import bricker.gameobjects.ExtraPaddle;
import bricker.gameobjects.Paddle;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * A collision strategy that spawns an ExtraPaddle when a brick is destroyed.
 * When the brick is hit, it is removed from the game. If no extra paddle currently exists,
 * a new ExtraPaddle is spawned in the center of the screen. This paddle behaves like
 * the main paddle but has a limited number of hits before disappearing.
 */
public class ExtraPaddleCollisionStrategy implements CollisionStrategy{

	private static final int MAX_HITS = 4;

	private final GameObjectCollection gameObjects;
	private final Renderable paddleImage;
	private final UserInputListener inputListener;
	private final Vector2 paddleDimensions;
	private final Vector2 windowDimensions;

	/**
	 * Constructs a collision strategy that may spawn an extra paddle.
	 *
	 * @param gameObjects        The game object collection used to manage objects in the game.
	 * @param paddleImage        Image of the extra paddle.
	 * @param inputListener      Handles keyboard input for paddle movement.
	 * @param paddleDimensions   Size of the paddle to be spawned.
	 * @param windowDimensions   Size of the game window.
	 */
	public ExtraPaddleCollisionStrategy(GameObjectCollection gameObjects, Renderable paddleImage,
										UserInputListener inputListener,
										Vector2 paddleDimensions, Vector2 windowDimensions) {
		this.gameObjects = gameObjects;
		this.paddleImage = paddleImage;

		this.inputListener = inputListener;

		this.paddleDimensions = paddleDimensions;
		this.windowDimensions = windowDimensions;
	}

	/**
	 * Handles collision by removing the brick and optionally spawning an extra paddle.
	 * <p>
	 * Only one extra paddle can exist at a time. if one already exists, nothing is spawned.
	 *
	 * @param brick  The brick that was hit.
	 * @param object The object that collided with the brick.
	 */
	@Override
	public void onCollision(Brick brick, GameObject object) {
		gameObjects.removeGameObject(brick, Layer.STATIC_OBJECTS); //remove the brick

		if (extraPaddleExist()){
			return;
		}
		Paddle extraPaddle = new ExtraPaddle(Vector2.ZERO, paddleDimensions,
									paddleImage, inputListener, windowDimensions,
									gameObjects,MAX_HITS);
		extraPaddle.setCenter(windowDimensions.mult(0.5f));
		gameObjects.addGameObject(extraPaddle);
	}


	private boolean extraPaddleExist() {
		for(GameObject object : gameObjects){
			if (object instanceof ExtraPaddle){
				return true;
			}
		}
		return false;
	}
}
