import java.util.ArrayList;

import processing.core.*;

public class Maze {
	private Block[][] blocks;
	private Block start;

	ArrayList<Block> b = new ArrayList<Block>();
	
	public Maze(int size) {
		blocks = new Block[size][size];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				float x = i * 5;
				float y = 0;
				float z = j * 5;
				blocks[i][j] = new Block(x, y, z, 5);
				b.add(blocks[i][j]);
			}
		}
			start = blocks[0][0];
			int row = (int) (random(1, size - 1));
			int col = (int) (random(1, size - 1));
			start = blocks[row][col];
			for (int i = 0; i < size * size * size / 10; i++) {
				if (!blocks[row][col].getVisited())
					blocks[row][col].moveDown();
				blocks[row][col].setVisited(true);
				if (random(0, 1) < 0.5) {
					if (random(0, 1) < 0.5 && row > 1)
						row -= 1;
					else if (row < size - 2)
						row += 1;
				} 
				else {
					if (random(0, 1) < 0.5 && col > 1)
						col -= 1;
					else if (col < size - 2)
						col += 1;
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
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks[i].length; j++) {
				blocks[i][j].display(g);
			}
		}
	}

	public void setPlayerAtStart(Player player) {
		player.moveTo(start.getX(), start.getY()-15, start.getZ());
	}
}