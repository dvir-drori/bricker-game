package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import bricker.gameobjects.Puck;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

public class PuckCollisionStrategy implements CollisionStrategy{

	private final int NUM_OF_PUCKS = 2;
	private final GameObjectCollection gameObject;
	private final Renderable puckImage;
	private final Sound puckSound;
	private final float puckSize;
	private final float ballSpeed;

	public PuckCollisionStrategy(GameObjectCollection gameObject, Renderable puckImage, Sound puckSound, float puckSize,
								 float ballSpeed) {
		this.gameObject = gameObject;
		this.puckImage = puckImage;
		this.puckSound = puckSound;
		this.puckSize = puckSize;
		this.ballSpeed = ballSpeed;
	}

	/**
	 * remove the bricks and in it place put two pucks
	 * @param brick
	 * @param object
	 */
	@Override
	public void onCollision(Brick brick, GameObject object) {

		gameObject.removeGameObject(brick, Layer.STATIC_OBJECTS); //remove the brick
		//step 2- make 2 pucks
		Vector2 brickCenter = brick.getCenter();
		Random random = new Random();
		for (int i = 0; i < NUM_OF_PUCKS; i++) {
			Puck puck = new Puck(Vector2.ZERO, new Vector2(puckSize,puckSize), puckImage, puckSound);
			puck.setCenter(brickCenter);
			double angle = random.nextDouble() * Math.PI;
			float velocityX = (float)Math.cos(angle) * ballSpeed;
			float velocityY = (float)Math.sin(angle) * ballSpeed;
			puck.setVelocity(new Vector2(velocityX,velocityY));
			gameObject.addGameObject(puck);
		}



	}
}
