package bricker.gameobjects;

import bricker.main.LivesCounter;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * A graphical UI component that visually represents the player's remaining lives using heart icons.
 * <p>
 * Hearts are added or removed dynamically based on the current life count,
 * up to a predefined maximum.
 */
public class GraphicLifeCounter extends GameObject {

	private static final int HEART_SIZE = 20;
	private static final int HEART_SPACING = 5;

	private final Vector2 startPosition;
	private final LivesCounter livesCounter;
	private final GameObjectCollection gameObjects;
	private final Renderable heartImage;
	private final int maxLives;
	private final GameObject[] hearts;
	private int currentDisplayedLives;


	/**
	 * Constructs a graphical life counter.
	 *
	 * @param startPosition  Starting position of the first heart icon.
	 * @param dimensions     Placeholder dimensions for the UI element.
	 * @param livesCounter   Shared counter tracking the player's lives.
	 * @param heartImage     Image used to render each heart icon.
	 * @param gameObjects    Collection managing all active game objects.
	 * @param maxLives       Maximum number of lives to display.
	 */
	public GraphicLifeCounter(Vector2 startPosition, Vector2 dimensions, LivesCounter livesCounter,
							  Renderable heartImage,GameObjectCollection gameObjects, int maxLives
							  ) {
		super(startPosition, dimensions, null);
		this.startPosition = startPosition;
		this.livesCounter = livesCounter;
		this.gameObjects = gameObjects;
		this.heartImage = heartImage;
		this.maxLives = maxLives;
		this.hearts = new GameObject[maxLives];
		this.currentDisplayedLives = 0;

		for (int i = 0; i < livesCounter.getValue(); i++) {
			addHeart();
		}
	}

	/**
	 * Adds or removes heart icons so that the visual display always reflects
	 * the number of lives.
	 *
	 * @param deltaTime Time elapsed since last frame.
	 */
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		int curLives = livesCounter.getValue();
		while (curLives < currentDisplayedLives){
			removeHeart();
		}
		while (currentDisplayedLives < curLives && currentDisplayedLives < maxLives){
			addHeart();
		}
	}


	private void addHeart() {
		Vector2 position = new Vector2(startPosition.x() + currentDisplayedLives * (HEART_SIZE + HEART_SPACING), startPosition.y());

		GameObject heart = new GameObject(position, new Vector2(HEART_SIZE,HEART_SIZE), heartImage);
		gameObjects.addGameObject(heart, Layer.UI);
		hearts[currentDisplayedLives] = heart;
		currentDisplayedLives++;
	}


	private void removeHeart() {
		currentDisplayedLives--;
		gameObjects.removeGameObject(hearts[currentDisplayedLives],Layer.UI);
		hearts[currentDisplayedLives] = null;
	}
}
