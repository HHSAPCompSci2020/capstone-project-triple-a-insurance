
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import processing.awt.PSurfaceAWT;
import processing.core.PApplet;
import processing.opengl.PSurfaceJOGL;

public class Main {
	
	public static void main(String args[]) {
		
		GraphicsPanel drawing = new GraphicsPanel();
		PApplet.main("GraphicsPanel");
		/*PApplet.runSketch(new String[]{""}, drawing);
		PSurfaceJOGL surf = (PSurfaceJOGL) drawing.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		JFrame window = (JFrame)canvas.getFrame();

		window.setSize(800, 600);
		window.setMinimumSize(new Dimension(100,100));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);

		window.setVisible(true);
		canvas.requestFocus();*/
	}

}
