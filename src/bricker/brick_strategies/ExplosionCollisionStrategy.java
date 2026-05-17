package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.Sound;

public class ExplosionCollisionStrategy implements CollisionStrategy{

	private final GameObjectCollection gameObjects;
	private final Sound explosionSound;

	public ExplosionCollisionStrategy(GameObjectCollection gameObjects, Sound explosionSound){
		this.gameObjects = gameObjects;
		this.explosionSound = explosionSound;
	}
	@Override
	public void onCollision(Brick brick, GameObject object) {
		gameObjects.removeGameObject(brick, Layer.STATIC_OBJECTS);
		explosionSound.play();
		for (Brick neighbor : brick.getNeighborBricks()){
			if (neighbor != null){
				neighbor.activateCollision(object);
			}
		}
	}
}
