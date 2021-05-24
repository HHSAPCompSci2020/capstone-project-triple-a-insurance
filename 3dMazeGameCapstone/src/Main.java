
import java.awt.Dimension;
import javax.swing.JFrame;

import processing.awt.PSurfaceAWT;
import processing.core.PApplet;
/**
 * Main class that calls the homeScreen first
 * @author ameya
 *
 */
public class Main {
	public static void main(String args[]) {

		HomeScreen drawing = new HomeScreen();
		PApplet.runSketch(new String[]{""}, drawing);
		PSurfaceAWT surf = (PSurfaceAWT) drawing.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		JFrame window = (JFrame)canvas.getFrame();
		window.setTitle("Home Screen");
		window.setBounds(50,50,1000, 1000);
		window.setMinimumSize(new Dimension(100,100));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);

		window.setVisible(true);
		//PApplet.main("HomeScreen");
	}

}
