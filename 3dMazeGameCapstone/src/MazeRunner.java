import java.awt.Dimension;
import java.awt.event.KeyEvent;

import java.util.ArrayList;

import javax.swing.JFrame;

import processing.awt.PSurfaceAWT;
import processing.core.*;

public class MazeRunner extends PApplet {
	private ArrayList<Integer> keys = new ArrayList<Integer>();
	public int mode = 0;
	private Player player;
	private Maze maze;
	private MenuScreen m;
	private LevelTimer levelTimeLeft = new LevelTimer(120);
	/**
	 * makes the screen some size
	 */
	public void settings() {
		//size(1000,1000,P3D);
		fullScreen(P3D);
		
	}
	/**
	 * sets up the game with a maze and a player in the maze
	 */
	public void setup() {
		strokeWeight(2);
		//m = new MenuScreen();
		this.frameRate(1000);
		if (mode == 0) {
			levelTimeLeft = new LevelTimer(120);
			maze = new Maze(13);
		}
		else {
			levelTimeLeft = new LevelTimer(250);
			maze = new Maze(28);
		}
		player = new Player();
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
		if (checkKey(KeyEvent.VK_H) && m == null) {
			
			m = new MenuScreen();
			
			PApplet.runSketch(new String[]{""}, m);
			PSurfaceAWT surf2 = (PSurfaceAWT) m.getSurface();
			PSurfaceAWT.SmoothCanvas canvas2 = (PSurfaceAWT.SmoothCanvas) surf2.getNative();
			JFrame window2 = (JFrame)canvas2.getFrame();

			window2.setBounds(500,50,800, 600);
			window2.setMinimumSize(new Dimension(100,100));
			window2.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			window2.setResizable(true);
			
			canvas2.requestFocus();
		}
		

	}
	
	/**
	 * checks to see the keys that are pressed and checks if menu screen should show up
	 */
	public void keyPressed() {
		if (!checkKey(keyCode))
			keys.add(keyCode);
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