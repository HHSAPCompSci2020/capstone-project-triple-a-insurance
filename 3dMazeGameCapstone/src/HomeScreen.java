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
		this.rect(width/7, 2*height/3, width/6, height/10);
		this.text("Easy Mode", width/7, 2*height/3, width/7+width/6, 2*height/3+height/10);
		this.rect(width-width/7-width/6,2*height/3 , width/6, height/10);
		this.text("Hard Mode", width/7, 2*height/3, width/7+width/6, 2*height/3+height/10);
		this.rect(width/4, 3*height/4, width/2, height/8);
		this.text("Play Game", width/4, 3*height/4, width/4+width/2, 3*height/4+height/8);
	
	}
	
	public boolean isChosen(int clickX, int clickY, int rectX, int rectY, int rectX1, int rectY1) {
		if((clickX>=rectX && clickX<=rectX1)&&(clickY>=rectY && clickY<=rectY1)) {
			return true;
		}
		return false;
	}
}
