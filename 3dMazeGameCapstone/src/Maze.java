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
	private Block[][][] maze;
	private int EASY = 12;
	private int MEDIUM = 24;
	private int HARD = 30;
	private Block start;
	private Block end;
	private Player p;
	ArrayList<Block> b = new ArrayList<Block>();
	
	public Maze (int size) {
		start = null;
		end = null;
		this.size = size;
		maze = new Block[size][size][size];
		generate();
	}
	
	private void generate () {
		for (int i = 0; i < Math.pow(maze.length, 3); i++) {
			int x, y, z;
			x = i / (size * size);
			y = i % (size * size) / size;
			z = i % (size * size) % size;
			add(new Block (x, y, z, 'w')); // 'w' means wall
		}
		
		int count = 0;
		
		int y, x, z; // x, y and z of current block
		z = 0; // the starting block will be at z = 0 because that is the bottommost layer
		x = (int)(Math.random()*(size-2));
		y = (int)(Math.random()*(size-2));
		maze[x][y][z].t = ' ';
		start = maze[x][y][z];
		
		int endx, endy, endz;
		endx = (int)(Math.random() * (size-2)) + 1; // random y to check
		endy = (int)(Math.random() * (size-2)) + 1;
		endz = size-1;
		maze[endx][endy][endz].t = ' ';
		// DO THE START AND END STUFF
		while (true) {
			boolean found;
			ArrayList<Block> ns = null;
			if (count > 20) {
				found = false; // indicates whether or not the 
				for (int i = 1; i < size-1; i ++) {
					for (int j = 1; j < size-1; j++) {
						for (int k = 1; k < size-1; k++) {
							boolean go = true; // indicator of whether or not the current block panned out
							if (maze[i][j][k].t == ' ') // doesn't check if the block is already empty
								break;
							ArrayList<Block> neighbors = getAdj(maze[i][j][k]);
							Block thing = maze[i][j][k];
							int num = 0;
							for (int l = 0; l < neighbors.size()-1; l++) {
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
					if (!found) break; // if nothing is found, the maze is finished
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
					for (m = l; m < neighbors.size(); m++) { // checks the neihbors ahead of it to see if their trees are joint or not
						if (!Collections.disjoint(neighbors.get(l).tree, neighbors.get(m).tree)) { // if they are not disjoint, the loop breaks
							go = true;
							break;
						}
					}
					if (go) break;
				}
				if (!go) {
					found = true;
					ns = neighbors;
				}
				if (!found) count ++;
				
			}
			
			if (found) {
				count = 0;
				maze[x][y][z].t = ' ';
				Set<Block> bset = maze[x][y][z].tree;
				for (Block curr : ns) {
					for (Block member : curr.tree) {
						bset.add(member);
					}
				}
				for (Block curr : ns) {
					curr.tree.addAll(bset);;
				}
			}
				
		}
		
		// Generate Larger Area
		//    - keep track of volume
		//    - make sure it is self-contained
		//    - all contents of this hedged off area is contained in list
				
		
		
		/*int width = arr.length/6;
		double randx = (int)(Math.random()*(maze.length - 7) + 3);
		int randy = (int)(Math.random()*(maze.length - 7) + 3);
		int dir = 1;
		while (true) {
			switch (dir) {
			case 1 : // up
				
				break;
			case 2: // right
				break;
			case 3: // front
				break;
			case 4: // down
				break;
			case 5: // left
				break;
			case 6: // back
				break;
			}
			break;
		}*/
		/* Generate Maze within confined space
			1. Remove start point from larger list of walls
			2. 
				1. Add neighboring walls to wall list.
					- for a random wall off the wall list:
						- if 
			2. 
				- 
		*/
		
		
		
	}
	
	public void addToList (ArrayList<Block> b, Block i, int depth) {
		if (depth == 0) return;
		if (!b.contains(i)) b.add(i);
		ArrayList<Block> neighbors = getAdj (i);
		for (Block n: neighbors) {
			addToList(b, n, depth-1);
		}
	}
	
	public ArrayList<Block> getAdj (Block b) {
		ArrayList<Block> neighbors = new ArrayList<Block> ();
		for (int i = Math.max(b.x-1, 0); i < Math.min(19, b.x+1); i++)
			for (int j = Math.max(b.y-1, 0); j < Math.min(19, b.y+1); j++)
				for (int k = Math.max(b.z-1, 0); k < Math.min(19, b.z+1); k++)
					if (maze[i][j][k] != null && !(i == b.x && j == b.y && k == b.z))
						neighbors.add(maze[i][j][k]);
		return neighbors;
	}
	
	public void add (Block b) {
		maze[b.x][b.y][b.z] = b;
	}
	
	private double random(double lower, double upper) {
		return Math.random() * (upper - lower) + lower;
	}

	public void update(Player p) {
		p.act(b);
	}

	public void display(PApplet g) {
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				maze[i][j][0].display(g);
			}
		}
	}

	public void setPlayerAtStart(Player player) {
		player.moveTo(start.getX(), start.getY()-15, start.getZ());
	}
	public Block returnStart() {
		return start;
	}
}
