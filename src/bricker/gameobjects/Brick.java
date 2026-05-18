package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents a brick in the game
 * <p>
 * Each brick has a position in the grid (row and column), a visual representation,
 * and a collision strategy that defines its behavior when hit.
 * <p>
 * Bricks can also keep references to neighboring bricks, which may be used by
 * certain collision strategies
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
	 * Constructs a brick with a position, size, visual representation,
	 * grid coordinates, and collision behavior strategy.
	 *
	 * @param topLeftCorner     Initial position of the brick.
	 * @param dimensions        Size of the brick.
	 * @param renderable        Visual representation of the brick.
	 * @param row               Row index of the brick in the grid.
	 * @param col               Column index of the brick in the grid.
	 * @param strategy          The collision strategy defining the brick's behavior when hit.
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
	 * Called when this brick collides with another game object.
	 *
	 * @param other     The other object involved in the collision.
	 * @param collision The collision details.
	 */
	@Override
	public void onCollisionEnter(GameObject other, Collision collision) {
		super.onCollisionEnter(other, collision);
		activateCollision(other);
	}

	/**
	 * Activates the brick's collision behavior if it has not already been destroyed.
	 *
	 * @param other The object that hit the brick.
	 */
	public void activateCollision(GameObject other){
		if (destroyed){
			return;
		}
		destroyed = true;
		collisionStrategy.onCollision(this, other);
	}

	/**
	 * Adds a neighboring brick to this brick's adjacency list.
	 *
	 * @param neighbor The neighboring brick to add.
	 */
	public void addNeighbor(Brick neighbor){
		neighborBricks[lastNeighbor] = neighbor;
		lastNeighbor++;
	}

	/**
	 * Returns the list of neighboring bricks.
	 *
	 * @return Array of neighboring bricks (may contain null values if not full).
	 */
	public Brick[] getNeighborBricks(){
		return this.neighborBricks;
	}

}
