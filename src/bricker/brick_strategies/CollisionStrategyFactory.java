package bricker.brick_strategies;

import bricker.main.LivesCounter;
import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.util.Random;

/**
 * Factory responsible for creating random collision strategy instances for bricks.
 * <p>
 * This class encapsulates the logic for assigning different behaviors to bricks.
 */
public class CollisionStrategyFactory {
	private static final int NUM_OF_OPTIONS = 5;
	private static final int SECOND_TIME = 4;
	private static final int MAX_NUM_OF_STRATEGIES = 3;

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

	/**
	 * Constructs a factory for generating collision strategies.
	 *
	 * @param gameObject        The game object collection used to manage objects in the game.
	 * @param puckImage         Image of pucks.
	 * @param puckSound         Sound played by pucks.
	 * @param puckSize          Size of spawned pucks.
	 * @param puckSpeed         Speed of spawned pucks.
	 * @param paddleImage       Image of extra paddle.
	 * @param inputListener     Handles user input for paddle movement.
	 * @param paddleDimensions  Dimensions of the extra paddle.
	 * @param windowDimensions  Game window size.
	 * @param heartImage        Image of hearts.
	 * @param heartDimensions   Size of hearts.
	 * @param explosionSound    Sound played during explosion effects.
	 * @param livesCounter      Shared lives counter.
	 * @param maxLives          Maximum number of allowed lives.
	 */
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

	/**
	 * Returns a randomly selected collision strategy.
	 *
	 * @return A new collision strategy instance.
	 */
	public CollisionStrategy getNewCollisionStrategy(){

		if (random.nextBoolean()){
			return new BasicCollisionStrategy(gameObject);
		}
		return buildRandomSpecialStrategy();
	}

	private CollisionStrategy buildRandomSpecialStrategy() {
		int roll = random.nextInt(NUM_OF_OPTIONS);
		switch (roll) {
		case 0:
			return new PuckCollisionStrategy(gameObject, puckImage, puckSound, puckSize, puckSpeed);
		case 1:
			return new ExtraPaddleCollisionStrategy(gameObject, paddleImage, inputListener,
					paddleDimensions, windowDimensions);
		case 2:
			return new ExtraLifeCollisionStrategy(gameObject, heartImage, heartDimensions,
					livesCounter, maxLives, windowDimensions.y());
		case 3:
			return new ExplosionCollisionStrategy(gameObject, explosionSound);
		case 4:{
			CollisionStrategy[] strategies = new CollisionStrategy[MAX_NUM_OF_STRATEGIES];
			int i = 2;
			int j = 0;
			boolean doublePicked = false;
			while (i>0){
				int strategyNum;
				if (!doublePicked){
					strategyNum = random.nextInt(NUM_OF_OPTIONS);
					if (strategyNum == 4){
						doublePicked = true;
						i++;
						continue;
					}
				}
				else {
					strategyNum = random.nextInt(SECOND_TIME);
				}
				strategies[j] = randomStrategyNoDouble(strategyNum);
				j++;
				i--;
			}
			return new DoubleCollisionStrategy(strategies, gameObject);
		}
		default:
			return new BasicCollisionStrategy(gameObject);
		}
	}

	private CollisionStrategy randomStrategyNoDouble(int strategyNum) {
		return switch (strategyNum) {
			case 0 -> new PuckCollisionStrategy(gameObject, puckImage, puckSound, puckSize, puckSpeed);
			case 1 ->
					new ExtraPaddleCollisionStrategy(gameObject, paddleImage, inputListener,
							paddleDimensions, windowDimensions);
			case 2 -> new ExtraLifeCollisionStrategy(gameObject, heartImage, heartDimensions,
					livesCounter, maxLives, windowDimensions.y());
			case 3 -> new ExplosionCollisionStrategy(gameObject, explosionSound);
			default -> new BasicCollisionStrategy(gameObject);
		};
	}

}