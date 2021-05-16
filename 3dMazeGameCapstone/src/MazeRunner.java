import java.awt.event.KeyEvent;

import java.util.ArrayList;

import processing.core.*;

public class MazeRunner extends PApplet {
	private ArrayList<Integer> keys = new ArrayList<Integer>();
	public int mode = 0;
	private Player player;
	private Maze maze;
	private LevelTimer levelTimeLeft = new LevelTimer(120);
	/**
	 * makes the screen some size
	 */
	public void settings() {
		fullScreen(P3D);
		
	}
	/**
	 * sets up the game with a maze and a player in the maze
	 */
	public void setup() {
		strokeWeight(2);
		this.frameRate(1000);
		if (mode == 0) {
			levelTimeLeft = new LevelTimer(120);
			maze = new Maze(12);
		}
		else {
			levelTimeLeft = new LevelTimer(250);
			maze = new Maze(28);
		}
		player = new Player(maze.returnStart());
		player.setup(this);
		
		maze.setPlayerAtStart(player);
		
	}
	/**
	 * draws all the players and sees if the player is moving
	 */
	public void draw() {
		
		
		noCursor();
		background(51);
		maze.display(this);
		maze.update(player);
		player.draw(this);
		text(levelTimeLeft.timeStartingZeros() + "" + levelTimeLeft.getTimeLeft(), width - 20, 20);

		if (checkKey(KeyEvent.VK_W))
			player.moveZ(1);
		else if (checkKey(KeyEvent.VK_S))
			player.moveZ(-1);
		if (checkKey(KeyEvent.VK_A))
			player.moveX(1);
		else if (checkKey(KeyEvent.VK_D))
			player.moveX(-1);
		

	}
	
	/**
	 * checks to see the keys that are pressed and checks if menu screen should show up
	 */
	public void keyPressed() {
		if (!checkKey(keyCode))
			keys.add(keyCode);

		if (checkKey(KeyEvent.VK_SPACE))
			player.jump();
	}

	/**
	 * removes a key from keyPressed
	 */
	public void keyReleased() {
		while (checkKey(keyCode))
			keys.remove(new Integer(keyCode));
	}

	/**
	 * checks if the key has already been pressed
	 * @param i the index
	 * @return true if it does false if it doesn't
	 */
	private boolean checkKey(int i) {
		return keys.contains(i);
	}
}