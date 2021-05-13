import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Maze class where the maze is stored to run
 * @author Aayush
 *
 */
public class Maze {
	
	private int size;
	private Block[][][] maze;
	private int EASY = 12;
	private int MEDIUM = 24;
	private int HARD = 30;
	
	public Maze (int size) {
		this.size = size;
		maze = new Block[size][size][size];
		generate();
	}
	
	private void generate () {
		for (int i = 0; i < Math.pow(maze.length, 3); i++) {
			int x, y, z;
			x = i/size^2;
			y = i%size^2/size;
			z = i%size^2%size;
			add(new Block (x, y, z, 'w'));
		}
		
		ArrayList<Block> blocks = new ArrayList<Block> ();
		for (Block[][] b2: maze) {
			for (Block[] b1: b2) {
				for (Block b: b1) blocks.add(b);
			}
		}
		
		int count = 0;
		
		int y, x, z;
		z = 0;
		x = (int)(Math.random()*(size-2));
		y = (int)(Math.random()*(size-2));
		
		while (true) {
			if (count > 20)
				for (int i = 0; i < size; i ++)
					for (int j = 0; j < size; j++)
						for (int k = 0; k < size; k++) {
							if (maze[i][j][k].t == 'e')
								break;
							if (true) {
								x = i;
								y = j;
								z = k;
							}
						
						}
			else {
				x = (int)(Math.random()*size);
				y = (int)(Math.random()*size);
				z = (int)(Math.random()*size);
				
				ArrayList<Block> neighbors = getAdj(maze[x][y][z]);
				
				count += 1;
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
	
}
