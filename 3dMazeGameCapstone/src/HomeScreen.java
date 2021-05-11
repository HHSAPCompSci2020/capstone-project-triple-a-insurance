import processing.core.PApplet;

/**
 * The first screen a user sees has parameters, default is easy mode
 * @author Ameya
 *
 */
public class HomeScreen extends PApplet{
	public int width, height;
	public void draw()
	{
		this.text("3D Maze",width/5 ,height/10);
		this.rect(2*height/3, width/7, width/6, height/10);
		this.rect(2*height/3, width-width/7-width/6, width/6, height/10);
		this.rect(3*height/4, width/4, width/2, height/10);
	
	}
}
