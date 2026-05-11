package bricker.gameobjects;

import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * a special ball when hitting specific kind of brick
 */

public class Puck extends Ball{
	public Puck(Vector2 topLeftCorner, Vector2 dimensions,
				Renderable renderable, Sound collisionSound) {
		super(topLeftCorner, dimensions, renderable, collisionSound);
	}
}
