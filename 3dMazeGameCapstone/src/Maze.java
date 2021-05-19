import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import processing.core.PApplet;

/**
 * Maze class where the maze is stored to run
 * @author Abbas
 *
 */
public class Maze {
	
	private int flags = 0;
	private int size;
	private ArrayList<Block> maze;
	private int EASY = 12;
	private int MEDIUM = 24;
	private int HARD = 30;
	private Block start;
	private Block end;
	//private Player p;
	ArrayList<Block> b = new ArrayList<Block>();
	private boolean generated;
	
	public Maze (int size) {
		start = null;
		end = null;
		generated = false;
		this.size = size;
		maze = new ArrayList<Block> ();
		generate();
	}
	
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
				if (x > 0 && y > 0 && z > 0 && x < size-1 && y < size-1 && z < size-1) // if the blocks are within the outer shell, add the blocks to the list of walls to check
					copyToCheck.add(new Integer[] {x, y, z});
			}
			//if (x > 0 && y > 0 && z > 0 && x < size-1 && y < size-1 && z < size-1) { // checks if the coordinates are within the smaller cube that is below the first layer.
				//copyToCheck.add(new Integer[] { x, y, z });	// the Block coords left to check
			//}
		}
		
		flag();
		int y, x, z; // x, y and z of current block
		z = 0; // the starting block will be at z = 0 because that is the bottom-most layer

		x = ((int)(Math.random() * (size/2)))*2 + 1; 
		y = ((int)(Math.random() * (size/2)))*2 + 1;
		start = get(x, y, z);
		
		int endx, endy, endz; // x, y and z of end block
		endx = ((int)(Math.random() * (size/2)))*2 + 1; 
		endy = ((int)(Math.random() * (size/2)))*2 + 1;
		endz = size-1;
		end = get(x, y, z);
		
		ArrayList<Integer[]> toCheck;
		boolean removed;
		do {
			removed = false;
			toCheck = new ArrayList<Integer[]> ();
			toCheck.addAll(copyToCheck);
			Collections.shuffle(toCheck); // Randomizes the order of the list of coordinates to check.
			
			for (Integer[] coords : toCheck) {
				Block curr = get(coords[0], coords[1], coords[2]); // current block
				ArrayList<Block> neighbors = getAdj(curr); // gets adjacent blocks
				boolean isJoint = false; // Are the sets of the adjacent blocks joint
				if (whiteSpaces(neighbors) > 1) { // If there are more whitespace neighbors than one do this
					for (int i = 0; i < neighbors.size() - 1 && !isJoint; i++) { // checks if there are any whitespace neighbors that have shared sets
						if (neighbors.get(i).t == ' ') {
							for (int j = i; j < neighbors.size(); j++) {
								if (neighbors.get(j).t == ' ')
									if (neighbors.get(i).tree.equals(neighbors.get(j).tree)) { // if the sets are the same, exit
										isJoint = true;
										break;
									}
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
		} while (removed);
		
		for (int i = 0; i < size * size; i++) {
			System.out.print(get(0, i / size, i % size).t);
			if ((i+1)%size == 0) {
				System.out.println();
			}
		}
		
		
		start.t = ' '; // sets the type of the start and end blocks
		end.t = ' ';
	}
	
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
	
	private void flag () {
		System.out.println(++flags);
	}
	
	private void join (Block b) {
		ArrayList<Block> neighbors = getAdj(b); // All of the neighbors.
		for (Block neighbor: neighbors) { // Iterates through all of the neighbors.
			if (neighbor.t == ' ') { // Important, checks if the neighbor is a wall or not.
				b.tree.addAll(neighbor.tree); // If it isn't a wall, it adds all of the members of the trees of the neighbors it used to separate.
			}
		}
		for (Block member : b.tree) { // Iterates through all of the members of the tree.
			if (member.t == ' ') member.tree = b.tree; // Sets the trees of the members to the shared tree. 
		}
		b.t = ' ';
	}
	
	private int whiteSpaces(ArrayList<Block> neighbors) { // gets number of whitespace neighbors
		int c = 0;
		for (Block b : neighbors)
			if (b.t == ' ') c++;
		return c;
	}
	
	private double random(double lower, double upper) {
		return Math.random() * (upper - lower) + lower;
	}

	public void update(Player p) {
		p.act(b);
	}

	public void display(PApplet g) { // this needs to be changed to a smaller radius to avoid unecessary computations.
		for (Block b : maze)
			b.display(g);
	}
	
	public boolean disjoint (Set<Block> a, Set<Block> b) {
		for (Block block : a) {
			if (b.contains(block)) return false;
		}
		return true;
	}

	public Block get (int x, int y, int z) {
		return maze.get(x*size*size + y*size + z);
	}
	
	public void setPlayerAtStart(Player player) {
		player.moveTo(start.getX(), start.getY()-15, start.getZ());
	}
	
	public Block returnStart() {
		return start;
	}
	
	public boolean generated () {
		return generated;
	}
	
	public Block getStart ( ) {
		return start;
	}
	
	public Block getEnd () {
		return end;
	}
}
