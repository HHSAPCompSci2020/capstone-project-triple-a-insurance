
import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import processing.core.PApplet;

/**
 * 
 * @author asampath803 This class represents a block in 3D space, whether it is
 *         the ground or the wall
 */
public class Block {
	//private int fillColor;
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
		tree = new HashSet<Block> (); // initializing the maze
		tree.add(this);
	}
	
	public void display(PApplet g) {
		if (t == 'w') {
			g.pushMatrix();
			g.translate(x*SIZE, y*SIZE, z*SIZE);
			g.fill(100, (255), 100);
			g.fill(100, 255, 100);
			g.box(SIZE);
			g.popMatrix();
		}
	}

	public boolean isPointInCube(float x, float y, float z) {
		// the x y z coords of the block are in the center so +/- by size/2 in all
		// directions to get the edges
		//System.out.println("x " + x );
		float left = this.x*SIZE + SIZE ;
		//System.out.println("l " + left);
		float right = this.x*SIZE ;
		//System.out.println("r " + right);
		float top = this.y*SIZE ;
		//System.out.println("t " + top);
		float bottom = this.y*SIZE + SIZE;
		//System.out.println("b " + bottom);
		float front = this.z*SIZE;
		//System.out.println("f " +front);
		float back = this.z*SIZE + SIZE;
		//System.out.println("b " +back);
		//System.out.println("" + left + " " + right);
		if (x > left && x < right && y > top && y < bottom && z > front && z < back) {
			return true;
		}

		return false;
	}
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public float getSize() {
		return SIZE;
	}
	
	public void moveDown(){
	    y += 5;
	}
	
	public void setVisited(boolean b) {
		visited = b;
	}
	
	public boolean getVisited() {
		return visited;
	}
}
