package bricker.gameobjects;

import bricker.LivesCounter;
import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class NumericLifeCounter extends GameObject {

	private final LivesCounter livesCounter;
	private final TextRenderable textRenderable;

	public NumericLifeCounter(Vector2 topLeftCorner, Vector2 dimensions,
							   LivesCounter livesCounter) {
		super(topLeftCorner, dimensions, new TextRenderable(String.valueOf(livesCounter)));
		this.livesCounter = livesCounter;
		this.textRenderable = (TextRenderable) renderer().getRenderable();
		updateAppearance();
	}

	/**
	 * @param deltaTime
	 */
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		updateAppearance();
	}

	private void updateAppearance() {
		int lives = livesCounter.getValue();
		textRenderable.setString(String.valueOf(lives));
		if (lives >= 3) {
			textRenderable.setColor(Color.GREEN);
		} else if (lives == 2) {
			textRenderable.setColor(Color.YELLOW);
		} else {
			textRenderable.setColor(Color.RED);
		}
	}
}
