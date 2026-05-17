package bricker.brick_strategies;

import bricker.LivesCounter;
import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

public class CollisionStrategyFactory {
	private GameObjectCollection gameObject;
	private Renderable puckImage;
	private Sound puckSound;
	private float puckSize;
	private float puckSpeed;
	private Renderable paddleImage;
	private UserInputListener inputListener;
	private Vector2 paddleDimensions;
	private Vector2 windowDimensions;
	private Renderable heartImage;
	private final Vector2 heartDimensions;
	private Sound explosionSound;
	private LivesCounter livesCounter;
	private int maxLives;
	private Random random;

	public CollisionStrategyFactory(GameObjectCollection gameObject,
									Renderable puckImage, Sound puckSound, float puckSize,
									float puckSpeed,
									Renderable paddleImage, UserInputListener inputListener,
									Vector2 paddleDimensions, Vector2 windowDimensions,
									Renderable heartImage, Vector2 heartDimensions, Sound explosionSound,
									LivesCounter livesCounter, int maxLives){
		this.gameObject = gameObject;
		this.puckImage = puckImage;
		this.puckSound = puckSound;
		this.puckSize = puckSize;
		this.puckSpeed = puckSpeed;
		this.paddleImage = paddleImage;
		this.inputListener = inputListener;
		this.paddleDimensions = paddleDimensions;
		this.windowDimensions = windowDimensions;
		this.heartImage = heartImage;
		this.heartDimensions = heartDimensions;
		this.explosionSound = explosionSound;
		this.livesCounter = livesCounter;
		this.maxLives = maxLives;
		this.random = new Random();
	}

	public CollisionStrategy getNewCollision(){

		if (random.nextBoolean()){
			return new BasicCollisionStrategy(gameObject);
		}
		CollisionStrategy strategy = null;
		int strategyNum = random.nextInt(5);
		switch (strategyNum){
			case 0 -> strategy = new PuckCollisionStrategy(gameObject, puckImage, puckSound, puckSize, puckSpeed);
			case 1 -> strategy = new ExtraPaddleCollisionStrategy(gameObject, paddleImage, inputListener, paddleDimensions, windowDimensions);
			case 2 -> strategy = new ExtraLifeCollisionStrategy(gameObject, heartImage, heartDimensions,
					livesCounter, maxLives, windowDimensions.y());
			case 3 -> strategy = new BasicCollisionStrategy(gameObject);
			case 4 -> strategy = new BasicCollisionStrategy(gameObject);
		}
		return strategy;
	}
}