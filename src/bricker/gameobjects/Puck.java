package bricker.gameobjects;

import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * a special ball when hitting specific kind of brick
 */

public class Puck extends Ball{

	/**
	 * Constructs a new Puck instance.
	 *
	 * @param topLeftCorner   Initial position of the puck.
	 * @param dimensions      Size of the puck.
	 * @param renderable      Visual representation of the puck.
	 * @param collisionSound  Sound to be played upon collision.
	 */
	public Puck(Vector2 topLeftCorner, Vector2 dimensions,
				Renderable renderable, Sound collisionSound) {
		super(topLeftCorner, dimensions, renderable, collisionSound);
	}
}
