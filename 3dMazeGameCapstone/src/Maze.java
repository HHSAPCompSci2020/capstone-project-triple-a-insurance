import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import processing.core.PApplet;

/**
 * Maze class where the maze is stored to run
 * @author Abbas
 *
 */
public class Maze {
	
	private int size;
	private ArrayList<Block> maze;
	private Block start;
	private Block end;
	//private Player p;
	//ArrayList<Block> b = new ArrayList<Block>();
	private boolean generated;
	
	public Maze (int size) {
		start = null;
		end = null;
		generated = false;
		this.size = size;
		maze = new ArrayList<Block> ();
		generate();
	}
	
	/**
	 * Generates a 3-dimensional maze withe 1 exit randomly
	 */
	private void generate () {
		
		ArrayList<Integer[]> copyToCheck = new ArrayList<Integer[]> ();
		for (int i = 0; i < Math.pow(size, 3); i++) { // Filling up the maze with blocks
			int x, y, z;
			x = i / (size * size); // Get the x, y, and z
			y = i % (size * size) / size;
			z = i % (size * size) % size;
			// spaces each block out by 1
			if (x%2 == 1 && y%2 == 1 && z%2 == 1) {
				maze.add(new Block (x, y, z, ' ')); // 'w' means wall
				
			} else {
				maze.add(new Block (x, y, z, 'w'));
				if (x > 0 && y > 0 && z > 0 && x < size-1 && y < size-1 && z < size-1) { // if the blocks are within the outer shell, add the blocks to the list of walls to check
					if (x%2+y%2+z%2 == 2)copyToCheck.add(new Integer[] {x, y, z});
				}
			}
			//if (x > 0 && y > 0 && z > 0 && x < size-1 && y < size-1 && z < size-1) { // checks if the coordinates are within the smaller cube that is below the first layer.
				//copyToCheck.add(new Integer[] { x, y, z });	// the Block coords left to check
			//}
		}
		
		int starty, startx, startz; // x, y and z of current block
		startz = 0; // the starting block will be at z = 0 because that is the bottom-most layer

		startx = ((int)(Math.random() * (size/2)))*2 + 1; 
		starty = ((int)(Math.random() * (size/2)))*2 + 1;
		start = get(startx, starty, startz);
		
		int endx, endy, endz; // x, y and z of end block
		endx = ((int)(Math.random() * (size/2)))*2 + 1; 
		endy = ((int)(Math.random() * (size/2)))*2 + 1;
		endz = size-1;
		end = get(endx, endy, endz);
		
		ArrayList<Integer[]> toCheck;
		boolean removed = true;
		while (removed) {
			removed = false;
			toCheck = new ArrayList<Integer[]> ();
			for (Integer[] thing : copyToCheck) {
				toCheck.add(thing);
			}
			Collections.shuffle(toCheck); // Randomizes the order of the list of coordinates to check.
			for (Integer[] coords : toCheck) {
				Block curr = get(coords[0], coords[1], coords[2]);
				ArrayList<Block> neighbors = getAdj(curr);
				boolean isJoint = false;
					for (int i = 0; i < neighbors.size() - 1 && !isJoint; i++) {
						for (int j = i+1; j < neighbors.size(); j++) {
							if (neighbors.get(j).t == ' ')
								if (neighbors.get(i).tree == neighbors.get(j).tree) {
									isJoint = true;
									break;
								}
						}
						if (isJoint) { // Even if it doesn't matter too much, don't want to spend a bunch of time iterating through the stuff unless I have too.
							break;
						}
					}
					if (!isJoint) {
						removed = true;
						copyToCheck.remove(coords);
						join(curr); // Joins all of the sets, changes the type of the block.
					}
				}
		}
		ArrayList<Block> t = new ArrayList<Block>();
		for (int i = 0; i < 5*size; i++) {
			ArrayList<Block> b = getWalls();
			
			int rand = (int)(Math.random()*b.size());
			int x = b.get(rand).x;
			int y = b.get(rand).y;
			int z = b.get(rand).z;
			set(x, y, z, new Trap (x, y, z));
		}
		start.t = ' '; // sets the type of the start and end blocks
		end.t = 'e';
	}
	
	/**
	 * With the block as the base it finds all adjacent blocks and puts them in an ArrayList
	 * @param b the current block
	 * @return All adjacent blocks
	 */
	public ArrayList<Block> getAdj (Block b) { // gets the adjacent blocks
		ArrayList<Block> neighbors = new ArrayList<Block> ();
		if (b.x+1 < size) neighbors.add(get(b.x+1, b.y, b.z));
		if (b.x-1 > -1) neighbors.add(get(b.x-1, b.y, b.z));
		if (b.y+1 < size) neighbors.add(get(b.x, b.y+1, b.z));
		if (b.y-1 > -1) neighbors.add(get(b.x, b.y-1, b.z));
		if (b.z+1 < size) neighbors.add(get(b.x, b.y, b.z+1));
		if (b.z-1 > -1) neighbors.add(get(b.x, b.y, b.z-1));
		return neighbors;
	}
	
	
	/**
	 * This joins two walls together as part of Kruskal's maze generation
	 * @param b block to check
	 */
	private void join (Block b) {
		ArrayList<Block> neighbors = getAdj(b); // All of the neighbors.
		for (Block neighbor: neighbors) { // Iterates through all of the neighbors.
			if (neighbor.t == ' ') { // Important, checks if the neighbor is a wall or not.
				for (Block member: neighbor.tree) {
					b.tree.add(member);
				}
			}
		}
		for (Block member : b.tree) { // Iterates through all of the members of the tree.
			if (member.t == ' ') member.tree = b.tree; // Sets the trees of the members to the shared tree. 
		}
		b.t = ' ';
	}
	/*
	private double random(double lower, double upper) {
		return Math.random() * (upper - lower) + lower;
	}
	*/

	/**
	 * Updates the state of the blocks
	 * @param p the player object
	 */
	public void update(Player p) {
		ArrayList<Block> b= getWalls();
		p.act(b);
	}

	/**
	 * Displays all the blocks in the maze
	 * @param g the renderer Panel
	 */
	public void display(PApplet g) { // this needs to be changed to a smaller radius to avoid unecessary computations.
		for (Block b : maze)
			b.display(g);
	}
	
	/**
	 * This method checks if the two sets have the same block in them if so
	 * return true
	 * @param a A set or collection of blocks
	 * @param b A set or collection of blocks
	 * @return boolean true or false
	 */
	public boolean disjoint (Set<Block> a, Set<Block> b) {
		for (Block block : a) {
			if (b.contains(block)) return false;
		}
		return true;
	}

	/**
	 * Returns the block at the coordiantes specified
	 * @param x X-Coordinate of the block
	 * @param y Y-Coordinate of the block
	 * @param z Z-Coordinate of the block
	 * @return the block at the position of the coordinates
	 */
	public Block get (int x, int y, int z) {
		return maze.get(x*size*size + y*size + z);
	}
	
	/**
	 * Puts a block at the coordinates specified
	 * @param x X-Coordinate of the block
	 * @param y Y-Coordinate of the block
	 * @param z Z-Coordinate of the block
	 * @param b A block to be set
	 */
	private void set (int x, int y, int z, Block b) {
		maze.set(x*size*size + y*size + z, b);
	}
	
	/**
	 * Returns the player to the start of the maze
	 * @param player the player object that is being controlled
	 */
	public void setPlayerAtStart(Player player) {
		player.moveTo(start.getX()*10, start.getY()*10, start.getZ()*10);
	}
	
	/**
	 * Finds all the walls in the maze and returns them in an ArrayList
	 * @return ArrayList consisting of the walls in the maze
	 */
	public ArrayList<Block> getWalls() {
		ArrayList<Block> b = new ArrayList<Block>();
		for (int i = 0; i <maze.size(); i++) {
			if (maze.get(i).t == 'w') {
				b.add(maze.get(i));
			}
		}
		return b;
	}
	
	/**
	 * returns the starting block of the maze
	 * @return the starting block of the maze
	 */
	public Block returnStart() {
		return start;
	}
	
	/**
	 * Checks if the maze has been generated
	 * @return a boolean of the maze generation
	 */
	public boolean generated () {
		return generated;
	}
	
	/**
	 * Gets the block at the start of the maze
	 * @return the block at the start
	 */
	public Block getStart () {
		return start;
	}
	
	/**
	 * Gets the block at the end of the maze
	 * @return end of the maze block
	 */
	public Block getEnd () {
		return end;
	}
}
