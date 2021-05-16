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
	
	private int size;
	private ArrayList<Block> maze;
	private int EASY = 12;
	private int MEDIUM = 24;
	private int HARD = 30;
	private Block start;
	private Block end;
	private Player p;
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
		
		ArrayList<Integer[]> toCheck = new ArrayList<Integer[]> ();
		for (int i = 0; i < Math.pow(size, 3); i++) { // Filling up the maze with blocks
			int x, y, z;
			x = i / (size * size);
			y = i % (size * size) / size;
			z = i % (size * size) % size;
			maze.add(new Block (x, y, z, 'w')); // 'w' means wall
			if (Math.min(x, Math.max(y, z)) > 0 && Math.max(x, Math.max(y,z)) < size-1) { // checks if the coordinates are within the smaller cube that is below the first layer.
				toCheck.add(new Integer[] { x, y, z });	// the Block coords left to check
			}
		}
		
		Collections.shuffle(toCheck); // Randomizes the order of the Array of coordinates to check.
		
		int y, x, z; // x, y and z of current block
		z = 0; // the starting block will be at z = 0 because that is the bottom-most layer
		x = (int)(Math.random()*(size-2))+1;
		y = (int)(Math.random()*(size-2))+1;
		start = get(x, y, z);
		start.t = ' ';
		
		int endx, endy, endz;
		endx = (int)(Math.random() * ((size-2)/2)) + 1; 
		endy = (int)(Math.random() * ((size-2)/2)) + 1;
		endz = size-1;
		end = get(x, y, z);
		end.t = ' ';
		
		for (Integer[] coords : toCheck) {
			Block curr = get(coords[0], coords[1], coords[2]);
			ArrayList<Block> neighbors = getAdj(curr);
			boolean isJoint = false;
			for (int i = 0; i < neighbors.size()-1 && !isJoint; i++) {
				if (neighbors.get(i).t == ' ') {
					for (int j = i; j < neighbors.size(); j++) {
						if (neighbors.get(j).t == ' ')
						if (Collections.disjoint(neighbors.get(i).tree, neighbors.get(j).tree)) {
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
				join(curr); // Joins all of the sets, changes the type of the block.
			}
		}
		/*int c = 0;
		while (c < 100000000) {
			boolean found;
			c++;
			ArrayList<Block> ns = null;
			if (count > 20) {
				found = false; // indicates whether or not the 
				for (int i = 1; i < size-1; i ++) {
					for (int j = 1; j < size-1; j++) {
						for (int k = 1; k < size-1; k++) {
							boolean go = true; // indicator of whether or not the current block panned out
							if (maze[i][j][k].t == ' ') // doesn't check if the block is already empty
								break;
							ArrayList<Block> neighbors = getAdj(maze[i][j][k]); // adjacent cells to check
							for (int l = 0; l < neighbors.size()-1; l++) { // iterates through the cells to find 
								int m;
								for (m = l + 1; m < neighbors.size(); m++) {
									if (!Collections.disjoint(neighbors.get(l).tree, neighbors.get(m).tree)) { // if there is a joint set found, break
										go = true;
										break;
									}
								}
								if (go) break; // if there is a joint set found, break
							}
							if (!go) {
								x = i; // x
								y = j; // y
								z = k; // z
								found = true; // if the for loop finds no joint sets between the blocks, it sets found = true, and indicates where to continue process
							}
							if (found) break;
						}
						if (found) break;
					}
					if (found) break;
				}
				if (!found) {
					generated = true;
					break; // if nothing is found, the maze is finished
				}
			} else {
				
				found = false; // found a location to remove wall
				boolean go = false; // 
				x = (int)(Math.random() * (size-2)) + 1; // random x to check
				y = (int)(Math.random() * (size-2)) + 1; // random y to check
				z = (int)(Math.random() * (size-2)) + 1; // random z to check
				
				ArrayList<Block> neighbors = getAdj(maze[x][y][z]); // gets the neighbors
				
				for (int l = 0; l < neighbors.size(); l++) { // for loop through the neighbors
					int m;
					for (m = l; m < neighbors.size(); m++) { // checks the neighbors ahead of it to see if their trees are joint or not
						if (!Collections.disjoint(neighbors.get(l).tree, neighbors.get(m).tree)) { // if they are not disjoint, the loop breaks
							go = true;
							break;
						}
					}
					if (go) break;
				}
				if (!go) {
					found = true;
					count = 0;
					ns = neighbors;
				}
				if (!found) count ++;
				c++;
				
			}
			
			if (found) {
				count = 0;
				maze[x][y][z].t = ' ';
				Set<Block> bset = maze[x][y][z].tree;
				for (Block curr : ns) {
					bset.addAll(curr.tree);
				}
				for (Block curr : ns) {
					curr.tree.addAll(bset);;
				}
			}
				
		}*/
		
		
	}
	
	public ArrayList<Block> getAdj (Block b) {
		ArrayList<Block> neighbors = new ArrayList<Block> ();
		if (b.x+1 < size) neighbors.add(get(b.x+1, b.y, b.z));
		if (b.x-1 > -1) neighbors.add(get(b.x-1, b.y, b.z));
		if (b.y+1 < size) neighbors.add(get(b.x, b.y+1, b.z));
		if (b.y-1 > -1) neighbors.add(get(b.x, b.y-1, b.z));
		if (b.z+1 < size) neighbors.add(get(b.x, b.y, b.z+1));
		if (b.z-1 > -1) neighbors.add(get(b.x, b.y, b.z-1));
		return neighbors;
	}
	
	private void join (Block b) {
		b.t = ' '; // Changes the type of the block
		ArrayList<Block> neighbors = getAdj(b); // All of the neighbors.
		for (Block neighbor: neighbors) { // Iterates through all of the neighbors.
			if (neighbor.t == ' ') { // Important, checks if the neighbor is a wall or not.
				b.tree.addAll(neighbor.tree); // If it isn't a wall, it adds all of the members of the trees of the neighbors it used to separate.
			}
		}
		for (Block neighbor: neighbors) { // For each member of the block's tree, makes sure that the tree is shared over all of it's members.
			Set<Block> neighborTree = neighbor.tree; // The neighbor's tree
			for (Block member : neighborTree) { // Iterates through all of the members of the tree.
				member.tree = b.tree; // Sets the trees of the members to the shared tree. 
			}
		}
	}
	
	private double random(double lower, double upper) {
		return Math.random() * (upper - lower) + lower;
	}

	public void update(Player p) {
		p.act(b);
	}

	public void display(PApplet g) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				get(i, j, 0).display(g);
			}
		}
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
