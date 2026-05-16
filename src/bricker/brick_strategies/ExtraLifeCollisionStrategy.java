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
	private final int HEART_VELOCITY = 100;
	private final GameObjectCollection gameObject;
	private final Renderable heartImage;
	private final Vector2 heartDimensions;
	private final LivesCounter livesCounter;

	public ExtraLifeCollisionStrategy(GameObjectCollection gameObject, Renderable heartImage, Vector2 heartDimensions,
									  LivesCounter livesCounter){
		this.gameObject = gameObject;
		this.heartImage = heartImage;
		this.heartDimensions = heartDimensions;
		this.livesCounter = livesCounter;
	}
	@Override
	public void onCollision(Brick brick, GameObject object) {
		gameObject.removeGameObject(brick, Layer.STATIC_OBJECTS);
		GameObject heart = new Heart(Vector2.ZERO, heartDimensions, heartImage);
		heart.setCenter(brick.getCenter());
		heart.setVelocity(Vector2.DOWN.mult(HEART_VELOCITY));
		gameObject.addGameObject(heart);
	}
}
