import java.awt.event.KeyEvent;

import java.util.ArrayList;

import processing.core.*;

public class MazeRunner extends PApplet {
	private ArrayList<Integer> keys = new ArrayList<Integer>();

	private Player player;
	private Maze maze;
	/**
	 * makes the screen some size
	 */
	public void settings() {
		size(1000,1000,P3D);
		
		
		//super.size(300, 200, P3D);
		 
	}
	/**
	 * sets up the game with a maze and a player in the maze
	 */
	public void setup() {
		
		strokeWeight(2);
		this.frameRate(1000);
		maze = new Maze(10);
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