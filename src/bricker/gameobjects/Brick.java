package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * rendering a brick and a grid of bricks
 */

public class Brick extends GameObject {
	private final int row;
	private final int col;
	private final CollisionStrategy collisionStrategy;

	/**
	 *
	 * @param topLeftCorner
	 * @param dimensions
	 * @param renderable
	 * @param row
	 * @param col
	 */
	public Brick(Vector2 topLeftCorner, Vector2 dimensions,
				 Renderable renderable, int row, int col, CollisionStrategy strategy) {
		super(topLeftCorner, dimensions, renderable);
		this.col = col;
		this.row = row;
		this.collisionStrategy = strategy;
	}

	/**
	 * @param other
	 * @param collision
	 */
	@Override
	public void onCollisionEnter(GameObject other, Collision collision) {
		super.onCollisionEnter(other, collision);
		collisionStrategy.onCollision(this,other);
	}

}
