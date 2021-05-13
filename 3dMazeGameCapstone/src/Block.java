import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import processing.core.PApplet;

public class Block {
	
	public final int x, y, z;
	public Maze maze;
	public char t; // type of block
	public static final int SIZE = 100;
	public boolean visited;
	public Set tree;
	
	public Block (int x, int y, int z, char t) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.t = t;
		tree = new HashSet();
		tree.add(this);
	}
	
	public void add (Maze maze) {
		this.maze = maze;
		maze.add(this);
	}
	
	public void draw (PApplet g) {
		if (t == 'w') {
			g.pushMatrix();
			g.translate(x, y, z);
			g.fill(100, 255, 100, 1);
			g.box(SIZE);
			g.popMatrix();
		}
		
	}
	
	public void changeType () {
		
	}
	
}
