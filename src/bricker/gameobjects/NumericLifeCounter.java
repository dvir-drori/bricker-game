package bricker.gameobjects;

import bricker.main.LivesCounter;
import danogl.GameObject;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * A UI component that displays the player's remaining lives as a numeric value.
 * <p>
 * The counter updates dynamically based on a shared the current life count,
 * and changes its color to indicate the player's status:
 */
public class NumericLifeCounter extends GameObject {

	private final LivesCounter livesCounter;
	private final TextRenderable textRenderable;

	/**
	 * Constructs a numeric life counter.
	 *
	 * @param topLeftCorner  Position of the text on screen.
	 * @param dimensions     Size of the text area.
	 * @param livesCounter   Shared counter tracking the player's lives.
	 */
	public NumericLifeCounter(Vector2 topLeftCorner, Vector2 dimensions,
							   LivesCounter livesCounter) {
		super(topLeftCorner, dimensions, new TextRenderable(String.valueOf(livesCounter)));
		this.livesCounter = livesCounter;
		this.textRenderable = (TextRenderable) renderer().getRenderable();
		updateAppearance();
	}

	/**
	 * Updates the displayed number and its color every frame.
	 *
	 * @param deltaTime Time elapsed since last frame.
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
