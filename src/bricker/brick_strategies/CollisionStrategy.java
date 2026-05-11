package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import danogl.GameObject;

/**
 * interface for collision strategies
 */
public interface CollisionStrategy {
	void onCollision(Brick brick, GameObject object);
}
