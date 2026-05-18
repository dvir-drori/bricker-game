package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * Represents the player-controlled paddle in the game.
 * The paddle responds to keyboard input and moves horizontally within the
 * game window boundaries.
 */
public class Paddle extends GameObject {
	public static final String MAIN_PADDLE_TAG = "MainPaddle";
	private static final float MOVEMENT_SPEED = 300;

	private UserInputListener inputListener;
	private final Vector2 windowDimensions;

	/**
	 * Constructs a new Paddle instance.
	 *
	 * @param topLeftCorner     Initial position of the paddle.
	 * @param dimensions        Size of the paddle.
	 * @param renderable        Image of the paddle.
	 * @param inputListener     Handles keyboard input for movement.
	 * @param windowDimensions  Size of the game window (used for boundary constraints).
	 */
	public Paddle(Vector2 topLeftCorner, Vector2 dimensions,
				  Renderable renderable, UserInputListener inputListener,
				  Vector2 windowDimensions) {
		super(topLeftCorner, dimensions, renderable);
		this.inputListener = inputListener;
		this.windowDimensions = windowDimensions;
		setTag(MAIN_PADDLE_TAG);
	}

	/**
	 * Updates the paddle each frame based on user input.
	 *
	 * @param deltaTime Time elapsed since last frame (in seconds).
	 */
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		Vector2 movementDir = Vector2.ZERO;
		if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)){
			movementDir = movementDir.add(Vector2.LEFT);
		}
		if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)){
			movementDir = movementDir.add(Vector2.RIGHT);
		}
		setVelocity(movementDir.mult(MOVEMENT_SPEED));
		if (getTopLeftCorner().x()<0){
			setTopLeftCorner(new Vector2(0, getTopLeftCorner().y()));
		}
		if (getTopLeftCorner().x() + getDimensions().x()>windowDimensions.x()){
			setTopLeftCorner(new Vector2(windowDimensions.x()-getDimensions().x(), getTopLeftCorner().y()) );
		}
	}
}
