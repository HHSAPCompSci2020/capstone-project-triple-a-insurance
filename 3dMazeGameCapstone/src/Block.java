
import java.util.HashSet;
import java.util.Set;

import processing.core.PApplet;

/**
 * 
 * @author asampath803 This class represents a block in 3D space, whether it is
 *         the ground or the wall
 */
public class Block {
	private int gradient;
	public int x, y, z;
	public Maze maze;
	public char t; // type of block, wall if 'w', empty if ' '
	public static final int SIZE = 10;
	public boolean visited;
	public Set<Block> tree; // Set of all blocks that are connected
	
	public Block (int x, int y, int z, char t) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.t = t;
		gradient = (int)( Math.random() * 50);
		tree = new HashSet<Block> (); // initializing the maze
		tree.add(this);
	}
	
	/**
	 * Displays the block or trap.
	 * @param g The renderer for the block.
	 */
	public void display(PApplet g) {
		if (t == 'w') {
			
			g.pushMatrix();
			g.translate(x*SIZE, y*SIZE, z*SIZE);
			//g.fill(100, (255-gradient), 100);
			g.fill(100, 255-gradient, 100);
			g.box(SIZE);
			g.popMatrix();
		} else if (t == 'e') {
			g.pushMatrix();
			g.translate(x*SIZE, y*SIZE, z*SIZE);
			g.fill(100, (255), 100);
			g.fill(100, 100, 255, 100);
			g.box(SIZE);
			g.popMatrix();
		}
	}

	/**
	 * Checks if a point is the block
	 * @param x the X-coordinate of the point.
	 * @param y the Y-coordinate of the point
	 * @param z the Z-coordinate of the point
	 * @return
	 */
	public boolean isPointInCube(float x, float y, float z) {
		// the x y z coords of the block are in the center so +/- by size/2 in all
		// directions to get the edges
		//System.out.println("x " + x );
		float left = this.x*SIZE + SIZE/2 ;
		//System.out.println("l " + left);
		float right = this.x*SIZE -SIZE/2;
		//System.out.println("r " + right);
		float top = this.y*SIZE - SIZE/2 ;
		//System.out.println("t " + top);
		float bottom = this.y*SIZE + SIZE/2;
		//System.out.println("b " + bottom);
		float front = this.z*SIZE - SIZE/2;
		//System.out.println("f " +front);
		float back = this.z*SIZE + SIZE/2;
		//System.out.println("b " +back);
		//System.out.println("" + left + " " + right);
		if (x < left && x > right && y > top && y < bottom && z > front && z < back) {
			return true;
		}

		return false;
	}
	/**
	 * Gets the X-coordinate of the block
	 * @return the X-coordinate of the block
	 */
	public float getX() {
		return x;
	}
	/**
	 * Gets the Y-coordinate of the block
	 * @return the Y-coordinate of the block
	 */
	public float getY() {
		return y;
	}
	/**
	 * Gets the XZ-coordinate of the block
	 * @return the Z-coordinate of the block
	 */
	public float getZ() {
		return z;
	}

	/**
	 * Gets the the size of the block
	 * @return the size of the block
	 */
	public float getSize() {
		return SIZE;
	}
	/**
	 * Moves the block in the y direction by 5 pixels
	 */
	public void moveDown(){
	    y += 5;
	}
	
	/**
	 * Sets the block as visited or not
	 * @param b A boolean input for the block
	 */
	public void setVisited(boolean b) {
		visited = b;
	}
	/**
	 * Checks if the block has been visited or not
	 * @return a boolean thats sees if the block has been visited 
	 */
	public boolean getVisited() {
		return visited;
	}
}
