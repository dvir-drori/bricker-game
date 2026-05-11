package bricker.gameobjects;

import bricker.LivesCounter;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class GraphicLifeCounter extends GameObject {
	private final Vector2 startPosition;
	private final LivesCounter livesCounter;
	private final GameObjectCollection gameObjects;
	private final Renderable heartImage;
	private final int maxLives;
	private final Heart[] hearts;
	private int currentDisplayedLives;

	private static final int HEART_SIZE = 20;
	private static final int HEART_SPACING = 5;


	/**
	 * @param deltaTime
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

	public GraphicLifeCounter(Vector2 startPosition, Vector2 dimensions, LivesCounter livesCounter,
							  Renderable heartImage,GameObjectCollection gameObjects, int maxLives
							  ) {
		super(startPosition, dimensions, null);
		this.startPosition = startPosition;
		this.livesCounter = livesCounter;
		this.gameObjects = gameObjects;
		this.heartImage = heartImage;
		this.maxLives = maxLives;
		this.hearts = new Heart[maxLives];
		this.currentDisplayedLives = 0;

		for (int i = 0; i < livesCounter.getValue(); i++) {
			addHeart();
		}
	}

	private void addHeart() {
		Vector2 position = new Vector2(startPosition.x() + currentDisplayedLives * (HEART_SIZE + HEART_SPACING), startPosition.y());

		Heart heart = new Heart(position, new Vector2(HEART_SIZE,HEART_SIZE), heartImage);
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
