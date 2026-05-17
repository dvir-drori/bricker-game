package bricker.brick_strategies;

import bricker.LivesCounter;
import bricker.gameobjects.Brick;
import bricker.gameobjects.Heart;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class ExtraLifeCollisionStrategy implements CollisionStrategy{
	private static final int HEART_VELOCITY = 100;
	private final GameObjectCollection gameObject;
	private final Renderable heartImage;
	private final Vector2 heartDimensions;
	private final LivesCounter livesCounter;
	private final int maxLives;
	private final float windowHeight;

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