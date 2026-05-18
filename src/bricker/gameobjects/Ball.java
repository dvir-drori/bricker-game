package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * ball class creating a ball with collision sound
 */
public class Ball extends GameObject {

	private final Sound collisionSound;

	/**
	 * Constructs a new Ball instance.
	 *
	 * @param topLeftCorner   Initial position of the ball.
	 * @param dimensions      Size of the ball.
	 * @param renderable      Image of the ball.
	 * @param collisionSound  Sound to be played upon collision.
	 */

	public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
		this.collisionSound = collisionSound;
	}

	/**
	 * Called when the ball collides with another game object.
	 * <p>
	 * The ball's velocity is flipped, and a collision sound is played.
	 *
	 * @param other     The other game object involved in the collision.
	 * @param collision The collision data.
	 */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
		collisionSound.play();
    }
}
