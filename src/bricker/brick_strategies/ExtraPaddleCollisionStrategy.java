package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import bricker.gameobjects.ExtraPaddle;
import bricker.gameobjects.Paddle;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class ExtraPaddleCollisionStrategy implements CollisionStrategy{
	private final GameObjectCollection gameObjects;
	private final Renderable paddleImage;
	private final UserInputListener inputListener;
	private final Vector2 paddleDimensions;
	private static final int MAX_HITS = 4;
	private final Vector2 windowDimensions;

	/**
	 * removes the brick we hit, and making an extra paddle that moves with the original one.
	 * there can be only one extra paddle in a given time.
	 * @param gameObjects
	 * @param paddleImage
	 * @param inputListener
	 * @param paddleDimensions
	 * @param windowDimensions
	 */
	public ExtraPaddleCollisionStrategy(GameObjectCollection gameObjects, Renderable paddleImage,
										UserInputListener inputListener,
										Vector2 paddleDimensions, Vector2 windowDimensions
	) {
		this.gameObjects = gameObjects;
		this.paddleImage = paddleImage;

		this.inputListener = inputListener;

		this.paddleDimensions = paddleDimensions;
		this.windowDimensions = windowDimensions;
	}

	/**
	 * dilling with the collision
	 * @param brick
	 * @param object
	 */
	@Override
	public void onCollision(Brick brick, GameObject object) {
		gameObjects.removeGameObject(brick, Layer.STATIC_OBJECTS); //remove the brick

		if (extraPaddleExist()){
			return;
		}
		Paddle extraPaddle = new ExtraPaddle(Vector2.ZERO, paddleDimensions, paddleImage, inputListener, windowDimensions, gameObjects,MAX_HITS);
		extraPaddle.setCenter(windowDimensions.mult(0.5f));
		gameObjects.addGameObject(extraPaddle);

	}

	/**
	 * checks if extra paddle already exist
	 * @return true if exists, false else
	 */

	private boolean extraPaddleExist() {
		for(GameObject object : gameObjects){
			if (object instanceof ExtraPaddle){
				return true;
			}
		}
		return false;
	}
}
