
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
	private int fillColor;
	public int x, y, z;
	public Maze maze;
	public char t; // type of block
	public static final int SIZE = 200;
	public boolean visited;
	public Set<Block> tree;
	
	public Block (int x, int y, int z, char t) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.t = t;
		tree = new HashSet<Block>();
		tree.add(this);
	}
	
	public void add (Maze maze) {
		this.maze = maze;
		maze.add(this);
	}
	
	public void display(PApplet g) {
		if (t == 'w') {
			g.pushMatrix();
			g.translate(x, y, z);
			g.fill(100, 255, 100, 1);
			g.box(SIZE);
			g.popMatrix();
		}
	}

	public boolean isPointInCube(float x, float y, float z) {
		// the x y z coords of the block are in the center so +/- by size/2 in all
		// directions to get the edges
		float left = this.x - SIZE / 2;
		float right = this.x + SIZE / 2;
		float top = this.y - SIZE / 2;
		float bottom = this.y + SIZE / 2;
		float front = this.z - SIZE / 2;
		float back = this.z + SIZE / 2;
		if (x > left && x < right && y > top && y < bottom && z > front && z < back) {
			return true;
		}

		return false;
	}
	
	public void setVisited(boolean b) {
		visited = b;
	}
	
	public boolean getVisited() {
		return visited;
	}
}
