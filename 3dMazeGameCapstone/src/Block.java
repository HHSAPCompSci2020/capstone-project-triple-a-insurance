import processing.core.PApplet;

public class Block {
	
	public final int x, y, z;
	public Maze maze;
	public char t; // type of block
	
	public Block (int x, int y, int z, char t) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.t = t;
	}
	
	public void add (Maze maze) {
		this.maze = maze;
		maze.add(this);
	}
	
	public void draw (PApplet g) {
		
	}
	
	public void changeType () {
		
	}
	
}
