package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;

public class DoubleCollisionStrategy implements CollisionStrategy{
	private CollisionStrategy[] strategies;
	private final GameObjectCollection gameObject;

	public DoubleCollisionStrategy(CollisionStrategy[]  strategies,GameObjectCollection gameObject){
		this.strategies = strategies;
		this.gameObject = gameObject;
	}
	@Override
	public void onCollision(Brick brick, GameObject object) {
		for (int i = 0; i < strategies.length; i++) {
			if (strategies[i]!=null){
				strategies[i].onCollision(brick, object);
				gameObject.addGameObject(brick);
			}
		}
		gameObject.removeGameObject(brick);
	}
}
