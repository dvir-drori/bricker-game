package bricker.main;

import bricker.LivesCounter;
import bricker.brick_strategies.BasicCollisionStrategy;
import bricker.brick_strategies.CollisionStrategy;
import bricker.brick_strategies.PuckCollisionStrategy;
import bricker.gameobjects.Ball;
import bricker.gameobjects.Brick;
import bricker.gameobjects.GraphicLifeCounter;
import bricker.gameobjects.NumericLifeCounter;
import bricker.gameobjects.Paddle;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.awt.event.KeyEvent;

import java.util.Random;

/**
 * Main game manager for the Bricker game.
 * Sets up the game world (background, walls, ball, paddle, bricks) and starts the game loop.
 * The number of bricks per row and number of rows can be supplied as cmd arguments;
 * otherwise default values are used.
 */
public class BrickerGameManager extends GameManager {

	// ---- window ----
	private static final String WINDOW_TITLE  = "Bricker";
	private static final int    WINDOW_WIDTH  = 700;
	private static final int    WINDOW_HEIGHT = 500;

	// ---- ball ----
	private static final int   BALL_SIZE  = 20;
	private static final float BALL_SPEED = 200;

	// ---- paddle ----
	private static final int PADDLE_WIDTH            = 100;
	private static final int PADDLE_HEIGHT           = 15;
	private static final int PADDLE_DIST_FROM_BOTTOM = 30;

	// ---- bricks ----
	private static final int DEFAULT_BRICKS_PER_ROW = 8;
	private static final int DEFAULT_BRICK_ROWS    = 7;
	private static final int BRICK_HEIGHT          = 15;
	private static final int BRICK_SPACING         = 2;
	private static final int BRICKS_TOP_OFFSET     = 15;
	private static final int BRICKS_SIDE_OFFSET    = 15;

	// --- pucks ---
	private static final int PUCK_SIZE = 15;
	private static final double PUCK_BRICK_PROB = 0.1;

	// --- heart ---
	private static final int MAX_LIVES = 3;
	private static final int HEART_SIZE = 20;
	private static final int NUMERIC_COUNTER_SIZE = 20;
	private static final int LIVES_LEFT_MARGIN = 20;
	private static final int LIVES_BOTTOM_MARGIN = 25;
	private static final int LIVES_GAP = 10;

	// ---- walls ----
	private static final int WALL_WIDTH = 10;

	// ---- assets ----
	private static final String BALL_IMAGE       = "assets/ball.png";
	private static final String PADDLE_IMAGE     = "assets/paddle.png";
	private static final String BRICK_IMAGE      = "assets/brick.png";
	private static final String BACKGROUND_IMAGE = "assets/DARK_BG2_small.jpeg";
	private static final String COLLISION_SOUND  = "assets/blop.wav";
	private static final String HEART_IMAGE = "assets/heart.png";
	private static final String PUCK_IMAGE = "assets/mockBall.png";

	// ---- per-game configuration ----
	private final int bricksPerRow;
	private final int brickRows;

	// --- references ---
	private GameObject ball;
	private WindowController windowController;
	private Vector2	windowDimensions;
	private LivesCounter livesCounter;
	private UserInputListener inputListener;

	// --- prompts ---
	private static final String LOSE_PROMPT = "You lose! Play again?";
	private static final String WIN_PROMPT = "You win! Play again?";

	/**
	 * Constructs a new Bricker game manager.
	 *
	 * @param windowTitle      title shown on the game window
	 * @param windowDimensions size of the game window in pixels
	 * @param bricksPerRow     number of bricks to create in each row of the grid
	 * @param brickRows        number of brick rows in the grid
	 */
	public BrickerGameManager(String windowTitle, Vector2 windowDimensions,
							  int bricksPerRow, int brickRows) {
		super(windowTitle, windowDimensions);
		this.bricksPerRow = bricksPerRow;
		this.brickRows    = brickRows;

	}

	/**
	 * Called by the engine on every frame. Delegates to checkGameEnd().
	 *
	 * @param deltaTime time elapsed since the previous frame, in seconds
	 */
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		checkGameEnd();
	}

	/**
	 * Detects game-end conditions:
	 *   if ball fell off the bottom and lives remain  decrement lives, reset ball.
	 *   if ball fell off and no lives left you lose.
	 *   all bricks are broken you win.
	 */
	private void checkGameEnd() {
		String prompt = "";

		if(inputListener.isKeyPressed(KeyEvent.VK_W)){
			prompt = WIN_PROMPT;
		}

		// Ball fell below the screen
		if (ball.getCenter().y() > windowDimensions.y()) {
			livesCounter.decrement();
			if (livesCounter.getValue() > 0) {
				resetBall();
			} else {
				prompt = LOSE_PROMPT;
			}
		}

		// All bricks gone
		if (countBricks() == 0) {
			prompt = WIN_PROMPT;
		}

		if (!prompt.isEmpty()) {
			if (windowController.openYesNoDialog(prompt)) {
				windowController.resetGame();
			} else {
				windowController.closeWindow();
			}
		}
	}
	/**
	 * Repositions the ball at the center of the screen and gives it
	 * a new random upward velocity.
	 */
	private void resetBall() {
		ball.setCenter(windowDimensions.mult(0.5f));
		float[] possibleX = {0, -BALL_SPEED, BALL_SPEED};
		float velocityX = possibleX[new Random().nextInt(3)];
		ball.setVelocity(new Vector2(velocityX, -BALL_SPEED));
	}

	/**
	 * Counts the Brick objects currently in the static-objects layer.
	 *
	 * @return number of bricks remaining
	 */
	private int countBricks() {
		int count = 0;
		for (GameObject obj : gameObjects().objectsInLayer(Layer.STATIC_OBJECTS)) {
			if (obj instanceof Brick) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Initializes the game. creates the background, walls, ball, paddle, and brick grid,
	 * and configures the ball-vs-bricks collision layer.
	 *
	 * @param imageReader     used to load image assets
	 * @param soundReader     used to load sound assets
	 * @param inputListener   reports keyboard input (used by the paddle)
	 * @param windowController controls the game window (size, lifetime)
	 */
	@Override
	public void initializeGame(ImageReader imageReader,
							   SoundReader soundReader,
							   UserInputListener inputListener,
							   WindowController windowController) {
		super.initializeGame(imageReader, soundReader, inputListener, windowController);
		this.windowDimensions = windowController.getWindowDimensions();
		this.windowController = windowController;
		this.inputListener = inputListener;
		gameObjects().layers().shouldLayersCollide(
				Layer.DEFAULT, Layer.STATIC_OBJECTS, true);

		createBackground(imageReader, windowDimensions);
		createWalls(windowDimensions);
		createBall(imageReader, soundReader, windowDimensions);
		createPaddle(imageReader, inputListener, windowDimensions);
		createBricks(imageReader, soundReader, windowDimensions);
		this.livesCounter = new LivesCounter(MAX_LIVES);
		createLivesDisplay(imageReader, windowDimensions);
	}

	private void createLivesDisplay(ImageReader imageReader, Vector2 windowDimensions) {
		Renderable heartImage = imageReader.readImage(HEART_IMAGE, true);

		//numeric display
		Vector2 numericPosition = new Vector2(LIVES_LEFT_MARGIN, windowDimensions.y() - LIVES_BOTTOM_MARGIN);
		NumericLifeCounter numeric =  new NumericLifeCounter(numericPosition, new Vector2(NUMERIC_COUNTER_SIZE, NUMERIC_COUNTER_SIZE), livesCounter);
		gameObjects().addGameObject(numeric,Layer.UI);

		//graphic display
		Vector2 graphicPosition = new Vector2(LIVES_LEFT_MARGIN + NUMERIC_COUNTER_SIZE + LIVES_GAP, windowDimensions.y() - LIVES_BOTTOM_MARGIN);
		GraphicLifeCounter graphic = new GraphicLifeCounter(graphicPosition, new Vector2(HEART_SIZE, HEART_SIZE), livesCounter,heartImage, gameObjects(), MAX_LIVES);
		gameObjects().addGameObject(graphic,Layer.UI);
	}

	/**
	 * Creates the background image and adds it to the BACKGROUND layer
	 * so it is drawn behind everything else.
	 *
	 * @param imageReader      used to load the background image
	 * @param windowDimensions the size of the game window
	 */
	private void createBackground(ImageReader imageReader, Vector2 windowDimensions) {
		Renderable bgImage = imageReader.readImage(BACKGROUND_IMAGE, false);
		GameObject background = new GameObject(Vector2.ZERO, windowDimensions, bgImage);
		gameObjects().addGameObject(background, Layer.BACKGROUND);
	}

	/**
	 * Creates the three invisible walls (left, right, top) that bound the playing field.
	 * The bottom is intentionally left open so the ball can fall out (game over).
	 *
	 * @param windowDimensions the size of the game window
	 */
	private void createWalls(Vector2 windowDimensions) {
		GameObject leftWall = new GameObject(
				Vector2.ZERO,
				new Vector2(WALL_WIDTH, windowDimensions.y()),
				null);
		GameObject rightWall = new GameObject(
				new Vector2(windowDimensions.x() - WALL_WIDTH, 0),
				new Vector2(WALL_WIDTH, windowDimensions.y()),
				null);
		GameObject topWall = new GameObject(
				Vector2.ZERO,
				new Vector2(windowDimensions.x(), WALL_WIDTH),
				null);

		gameObjects().addGameObject(leftWall);
		gameObjects().addGameObject(rightWall);
		gameObjects().addGameObject(topWall);
	}

	/**
	 * Creates the game ball, places it at the center of the screen,
	 * and gives it a random initial velocity that always points upward.
	 *
	 * @param imageReader      used to load the ball image
	 * @param soundReader      used to load the collision sound
	 * @param windowDimensions the size of the game window
	 */
	private void createBall(ImageReader imageReader,
							SoundReader soundReader,
							Vector2 windowDimensions) {
		Renderable ballImage   = imageReader.readImage(BALL_IMAGE, true);
		Sound      collideSound = soundReader.readSound(COLLISION_SOUND);

		this.ball = new Ball(
				Vector2.ZERO,
				new Vector2(BALL_SIZE, BALL_SIZE),
				ballImage,
				collideSound);
		ball.setCenter(windowDimensions.mult(0.5f));

		// Random initial direction: up-left, straight up, or up-right.
		float[] possibleX = {0, -BALL_SPEED, BALL_SPEED};
		float velocityX = possibleX[new Random().nextInt(3)];
		ball.setVelocity(new Vector2(velocityX, -BALL_SPEED));

		gameObjects().addGameObject(ball);
	}

	/**
	 * Creates the paddle and places it near the bottom of the screen.
	 *
	 * @param imageReader      used to load the paddle image
	 * @param inputListener    keyboard input listener used by the paddle
	 * @param windowDimensions the size of the game window
	 */
	private void createPaddle(ImageReader imageReader,
							  UserInputListener inputListener,
							  Vector2 windowDimensions) {
		Renderable paddleImage = imageReader.readImage(PADDLE_IMAGE, true);

		Paddle paddle = new Paddle(
				Vector2.ZERO,
				new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
				paddleImage,
				inputListener);
		paddle.setCenter(new Vector2(
				windowDimensions.x() / 2,
				windowDimensions.y() - PADDLE_DIST_FROM_BOTTOM));

		gameObjects().addGameObject(paddle);
	}

	/**
	 * Creates the grid of bricks.
	 * Brick width is computed from the window width so the grid scales to the window size,
	 * leaves margins on both sides, and includes small gaps between bricks.
	 * Bricks are placed in a way that they do not check collisions
	 * against each other.
	 *
	 * @param imageReader      used to load the brick image
	 * @param windowDimensions the size of the game window
	 */
	private void createBricks(ImageReader imageReader,SoundReader soundReader, Vector2 windowDimensions) {
		Renderable brickImage = imageReader.readImage(BRICK_IMAGE, false);
		Renderable puckImage = imageReader.readImage(PUCK_IMAGE,true);
		Sound puckSound = soundReader.readSound(COLLISION_SOUND);
		Random random = new Random();

		float availableWidth = windowDimensions.x() - 2 * BRICKS_SIDE_OFFSET;
		float totalSpacing   = BRICK_SPACING * (bricksPerRow - 1);
		float brickWidth     = (availableWidth - totalSpacing) / bricksPerRow;

		for (int row = 0; row < brickRows; row++) {
			for (int col = 0; col < bricksPerRow; col++) {
				float x = BRICKS_SIDE_OFFSET + col * (brickWidth + BRICK_SPACING);
				float y = BRICKS_TOP_OFFSET  + row * (BRICK_HEIGHT + BRICK_SPACING);

				CollisionStrategy strategy;
				if (random.nextDouble() < PUCK_BRICK_PROB){
					strategy = new PuckCollisionStrategy(gameObjects(), puckImage, puckSound, PUCK_SIZE, BALL_SPEED);

				}
				else{
					strategy = new BasicCollisionStrategy(gameObjects());

				}

				Brick brick = new Brick(
						new Vector2(x, y),
						new Vector2(brickWidth, BRICK_HEIGHT),
						brickImage,
						row, col,
						strategy);
				gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
			}
		}
	}

	/**
	 * optional adding cmd arguments
	 * @param args command-line arguments: optionally, {@code bricksPerRow} and {@code brickRows}
	 */
	public static void main(String[] args) {
		int bricksPerRow;
		int brickRows;

		if (args.length == 2) {
			bricksPerRow = Integer.parseInt(args[0]);
			brickRows    = Integer.parseInt(args[1]);
		} else {
			bricksPerRow = DEFAULT_BRICKS_PER_ROW;
			brickRows    = DEFAULT_BRICK_ROWS;
		}

		new BrickerGameManager(
				WINDOW_TITLE,
				new Vector2(WINDOW_WIDTH, WINDOW_HEIGHT),
				bricksPerRow,
				brickRows
		).run();
	}
}