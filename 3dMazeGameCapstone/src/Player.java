import java.util.ArrayList;

import processing.core.*;

/**
 * 
 * @author asampath803 This class represents the player, a type of camera that
 *         is affected by gravity, collides with blocks
 *         edits by: Ameya, Aayush
 */
public class Player extends Camera {
	private float w, h, d;
	private boolean grounded;
	//private float gravity;
	private boolean trapCollision = false;
	//private int ix, iy, iz;
	private Block start;

	public Player() {
		// speed is at .12f max
		this(1, 3, 1, .04f, .5f, .5f, .75f, PConstants.PI / 3f, 80f);
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
		//gravity = 0.06f;
	}

	/**
	 * Checks to see if the player is colliding with any of the Block objects inside
	 * the specified ArrayList
	 * 
	 * @param blocks ArrayList of Block objects to check collision with
	 */
	public void act(ArrayList<Block> blocks) {
		//System.out.println(" " +  blocks.get(0).getX() + " " + blocks.get(0).getY());
		PVector position = getPosition();
		PVector velocity = getVelocity();
		//System.out.println("" + position.x + " "+ position.y + " " +position.z);
		//System.out.println("inAct " + blocks.size());
		for (Block b : blocks) {
			//System.out.println("inLoop");
			// position is in the center of the player so you have to
			// add/substract
			// its (dimension in axis)/2 to get the edges
			//System.out.println("" + position.x + " "+ position.y + " " +position.z);
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
			float bh2 = blockSize/2;
			float blockLeft = (b.getX() + bh2 - blockSize / 2) * blockSize;
			float blockRight = (b.getX()+ bh2 + blockSize / 2) * blockSize;
			float blockTop = (b.getY()+ bh2 - blockHeight / 2) * blockSize;
			float blockBottom = (b.getY() + bh2 + blockHeight / 2) * blockSize;
			float blockFront = (b.getZ()+ bh2 - blockSize / 2) * blockSize;
			float blockBack = (b.getZ() + bh2 + blockSize / 2) * blockSize;
			if(b instanceof Trap) {
				trapCollision = false;
			}
			// accordingly
			if (b.isPointInCube(left, position.y, position.z)) {
				// move right
				if(trapCollision) {
					//moveTo(start.x, start.y-15, start.z);
				}
				else {
					moveTo(blockRight + w / 2, position.y, position.z);
				}
			} else if (b.isPointInCube(right, position.y, position.z)) {
				// move left
				if(trapCollision) {
					//moveTo(start.x, start.y-15, start.z);
				}
				else {
					moveTo(blockLeft - w / 2, position.y, position.z);
				}
			}
			if (b.isPointInCube(position.x, top, position.z)) {// move down
				if(trapCollision) {
					//(start.x, start.y-15, start.z);
				}
				else {
					moveTo(position.x, blockBottom + h / 2, position.z);
				}
			} else if (b.isPointInCube(position.x, bottom, position.z)) {
				// move up/grounded
				moveTo(position.x, blockTop - h / 2, position.z);
				velocity.y = 0;
				grounded = true;
			}
			if (b.isPointInCube(position.x, position.y, front)) {
				// move back
				if(trapCollision) {
					//moveTo(start.x, start.y-15, start.z);
				}
				else {
					moveTo(position.x, position.y, blockFront - d / 2);
				}
			} else if (b.isPointInCube(position.x, position.y, back)) {
				// move forward
				if(trapCollision) {
					//moveTo(start.x, start.y-15, start.z);
				}
				else {
					moveTo(position.x, position.y, blockBack + d / 2);
				}
			}
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
