import java.util.ArrayList;

import processing.core.*;

/**
 *  This class represents the player, a type of camera that
 *         is affected by gravity, collides with blocks
 * @author asampath803  edits by: Ameya, Aayush
 *        
 */
public class Player extends Camera {
	private float w, h, d;
	//private boolean grounded;
	//private float gravity;
	//private int ix, iy, iz;
	private Block start;
	private boolean canAct = true;

	public Player(Block start) {
		// speed is at .12f max
		this(5, 3, 1, .04f, .5f, .5f, .75f, PConstants.PI / 3f, 80f);
		this.start = start;
		System.out.println("" + start.x + " " + start.y);
		
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
		this.w = 0;
		this.h = 0;
		this.d = 0;
		
		//grounded = true;
		//gravity = 0.06f;
	}

	/**
	 * Checks to see if the player is colliding with any of the Block objects inside
	 * the specified ArrayList
	 * 
	 * @param blocks ArrayList of Block objects to check collision with
	 */
	public void act(ArrayList<Block> blocks) {
		if (canAct) {
			canAct = false;
			PVector position = getPosition();
			PVector velocity = getVelocity();
			for (Block b : blocks) {
				// position is in the center of the player so you have to
				// add/substract
				// its (dimension in axis)/2 to get the edges
				//System.out.println("" + position.x + " "+ position.y + " " +position.z);
				float left = position.x + 2.5f;
				float right = position.x - 2.5f;
				float top = position.y - 2.5f;
				float bottom = position.y +2.5f;
				float front = position.z + 2.5f;
				float back = position.z - 2.5f;
				// block position is in the center of the block so
				// you have to add/substract its
				// dimensions/2 to get the edges
				if (b.isPointInCube(left, position.y, position.z)) {
					if(b instanceof Trap) {
						moveTo(start.x*10, start.y*10, start.z*10);
					}
					else {
						moveTo(position.x-0.25f, position.y, position.z);

					}
				} if (b.isPointInCube(right, position.y, position.z)) {
					// move left
					if(b instanceof Trap) {
						moveTo(start.x*10, start.y*10, start.z*10);
					}
					else {
						moveTo(position.x + 0.25f, position.y, position.z);
					}
				}
				if (b.isPointInCube(position.x, top, position.z)) {// move down
					if(b instanceof Trap) {
						moveTo(start.x*10, start.y*10, start.z*10);
					}
					else {
						moveTo(position.x, position.y + 0.25f, position.z);
					}
				} if (b.isPointInCube(position.x, bottom, position.z)) {
					if(b instanceof Trap) {
						moveTo(start.x*10, start.y*10, start.z*10);
					}
					else {
						moveTo(position.x, position.y - 0.25f, position.z);
						velocity.y = 0;
					//grounded = true;
					}
				}
				if (b.isPointInCube(position.x, position.y, front)) {
					// move back
					if(b instanceof Trap) {
						moveTo(start.x*10, start.y*10, start.z*10);
					}
					else {
						moveTo(position.x, position.y, position.z - 0.25f);
						
					}
				} if (b.isPointInCube(position.x, position.y, back)) {
					// move forward
					if(b instanceof Trap) {
						moveTo(start.x*10, start.y*10, start.z*10);
					}
					else {
						moveTo(position.x, position.y, position.z + 0.5f);
					}
				}
			}
			canAct = true;
		}
	}

	/**
	 * Gets the width of the box
	 * @return the width of the box
	 */
	public float getWidth() {
		return w;
	}
	
	/**
	 * Gets the height of the box
	 * @return the height of the box
	 */
	public float getHeight() {
		return h;
	}

	/**
	 * Gets the depth of the box
	 * @return the depth of the box
	 */
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
