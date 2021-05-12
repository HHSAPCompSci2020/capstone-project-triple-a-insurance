/**
 * Maze class where the maze is stored to run
 * @author Aayush
 *
 */
public class Maze {
	
	private Block[][][] maze;
	private int EASY = 12;
	private int MEDIUM = 24;
	private int HARD = 30;
	
	public Maze (int size) {
		maze = new Block[size][size][size];
		generate (maze);
	}
	
	private void generate (Block [][][] arr) {
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
			case 1 :
				
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5: 
				break;
			case 6:
				break;
			}
			break;
		}*/
		/* Generate Maze within confined space
			1. Remove start point from larger list of walls
			2. 
				1. Add neighboring walls to wall list.
					- for a random wall of the wall:
						- if 
			2. 
				- 
		*/ 
	}
	
	public void add (Block b) {
		
	}
	
}
