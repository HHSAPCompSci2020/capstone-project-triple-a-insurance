import javax.swing.JFrame;

import processing.core.PApplet;

//author:: Aayush Lakhotia, Ameya Gandhi, Abbas D...
public class GraphicsPanel extends PApplet{
	HomeScreen homeScreen;
	float x,y,z;

	public GraphicsPanel() {
		
	}
	public void settings() {
		size(200, 200, P3D);
	}
	public void setup() {
		background(255);   // Clear the screen with a white background
	}
	
	public void draw() {
		background(100);
		rectMode(CENTER);
		fill(51);
		stroke(255);

		translate(100, 100, 0);
		rotateX(PI/8);
		rotateY(PI/8);
		rect(0, 0, 100, 100);
	}
}
