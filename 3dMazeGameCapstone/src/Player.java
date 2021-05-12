import java.util.ArrayList;

import processing.core.*;

/**
 * 
 * @author asampath803 This class represents the player, a type of camera that
 *         is affected by gravity, collides with blocks
 */
public class Player extends Camera {
	private float w, h, d;
	private boolean grounded;
	private float gravity;

	public Player() {
		// speed is at .12f max
		this(1, 3, 1, .01f, .5f, .5f, .75f, PConstants.PI / 3f, 60f);
	}

	/**
	 * 
	 * @param w            Width of the player
	 * @param h            Height of the player
	 * @param d            Depth of the player
	 * @param speed        How fast the player moves
	 * @param xSensitivity Mouse sensitivity on the x-axis
	 * @param ySensitivity Mouse sensitivity on the y-axis
	 * @param friction     The amount of friction the player experiences while
	 *                     moving
	 * @param fov          The player's field of view
	 * @param viewDistance How far the player can look in the distance
	 */
	public Player(float w, float h, float d, float speed, float xSensitivity, float ySensitivity, float friction,
			float fov, float viewDistance) {
		super(speed, xSensitivity, ySensitivity, friction, fov, viewDistance);
		this.w = w;
		this.h = h;
		this.d = d;
		grounded = true;
		gravity = 0.06f;
	}

	/**
	 * Checks to see if the player is colliding with any of the Block objects inside
	 * the specified ArrayList
	 * 
	 * @param blocks ArrayList of Block objects to check collision with
	 */
	public void act(ArrayList<Block> blocks) {
		PVector position = getPosition();
		PVector velocity = getVelocity();
		for (Block b : blocks) {
			// position is in the center of the so you have to
			// add/substract
			// its (dimension in axis)/2 to get the edges
			float left = position.x - w / 2;
			float right = position.x + w / 2;
			float top = position.y - h / 2;
			float bottom = position.y + h / 2;
			float front = position.z + d / 2;
			float back = position.z - d / 2;
			// block position is in the center of the block so
			// you have to add/substract its
			// dimensions/2 to get the edges
			float blockSize = b.getSize();
			float blockHeight = b.getSize();
			float blockLeft = b.getX() - blockSize / 2;
			float blockRight = b.getX() + blockSize / 2;
			float blockTop = b.getY() - blockHeight / 2;
			float blockBottom = b.getY() + blockHeight / 2;
			float blockFront = b.getZ() - blockSize / 2;
			float blockBack = b.getZ() + blockSize / 2;
			// accordingly
			if (b.isPointInCube(left, position.y, position.z)) {
				// move right
				position.x = blockRight + w / 2;
			} else if (b.isPointInCube(right, position.y, position.z)) {
				// move left
				position.x = blockLeft - w / 2;
			}
			if (b.isPointInCube(position.x, top, position.z)) {// move down
				position.y = blockBottom + h / 2;
			} else if (b.isPointInCube(position.x, bottom, position.z)) {
				// move up/grounded
				position.y = blockTop - h / 2;
				velocity.y = 0;
				grounded = true;
			}
			if (b.isPointInCube(position.x, position.y, front)) {
				// move back
				position.z = blockFront - d / 2;
			} else if (b.isPointInCube(position.x, position.y, back)) {
				// move forward
				position.z = blockBack + d / 2;
			}
		}
		velocity.y += gravity;

	}

	public void jump() {
		if (grounded) {
			grounded = false;
			getVelocity().y -= 3f;
			getPosition().y -= .5;
		}
	}

	public float getWidth() {
		return w;
	}

	public float getHeight() {
		return h;
	}

	public float getDepth() {
		return d;
	}

	/**
	 * Sets the position of the player to the given coordinates
	 * 
	 * @param x x-coordinate of where to move the player
	 * @param y y-coordinate of where to move the player
	 * @param z z-coordinate of where to move the player
	 */
	public void moveTo(float x, float y, float z) {
		this.getPosition().x = x;
		this.getPosition().y = y;
		this.getPosition().z = z;
	}
}
