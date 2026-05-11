package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * a paddle class
 */
public class Paddle extends GameObject {
	private static final float MOVEMENT_SPEED = 300;



	private UserInputListener inputListener;
	private final Vector2 windowDimensions;

	public Paddle(Vector2 topLeftCorner, Vector2 dimensions,
				  Renderable renderable, UserInputListener inputListener,
				  Vector2 windowDimensions) {
		super(topLeftCorner, dimensions, renderable);
		this.inputListener = inputListener;
		this.windowDimensions = windowDimensions;
	}

	/**
	 * moving the paddle
	 * @param deltaTime
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
