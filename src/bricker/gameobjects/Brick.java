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
	private static final int MAX_NUM_OF_NEIGHBORS = 4;
	private final int row;
	private final int col;
	private final CollisionStrategy collisionStrategy;
	private Brick[] neighborBricks;
	private int lastNeighbor;
	private boolean destroyed;

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
		this.neighborBricks = new Brick[MAX_NUM_OF_NEIGHBORS];
		this.lastNeighbor = 0;
		this.destroyed = false;
		this.collisionStrategy = strategy;
	}

	/**
	 * @param other
	 * @param collision
	 */
	@Override
	public void onCollisionEnter(GameObject other, Collision collision) {
		super.onCollisionEnter(other, collision);
		activateCollision(other);
	}

	public void activateCollision(GameObject other){
		if (destroyed){
			return;
		}
		destroyed = true;
		collisionStrategy.onCollision(this, other);
	}

	public void addNeighbor(Brick neighbor){
		neighborBricks[lastNeighbor] = neighbor;
		lastNeighbor++;
	}

	public Brick[] getNeighborBricks(){
		return this.neighborBricks;
	}

}
