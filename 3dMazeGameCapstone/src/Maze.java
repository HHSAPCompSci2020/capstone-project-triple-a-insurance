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
		boolean[][][] tempArr = new boolean[size][size][size];
		generate (tempArr);
	}
	
	private void generate (boolean [][][] arr) {
		// Generate Larger Area
		//    - keep track of volume
		//    - make sure it is self-contained
		//    - all contents of this hedged off area is contained in list
				
		
		double randx = (int)(Math.random()*(maze.length - 7) + 3);
		int randy = (int)(Math.random()*(maze.length - 7) + 3);
		int randWidth = (int)(Math.random()*2);
		while (true) {
			
		}
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
	
}
