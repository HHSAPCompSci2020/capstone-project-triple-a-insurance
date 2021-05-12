import processing.core.PApplet;
import processing.core.PImage;

/**
 * The first screen a user sees has parameters, default is easy mode
 * @author Ameya
 *
 */
public class HomeScreen extends PApplet{

	//PImage backgroundImg;
	public void settings() {
		size(1000,1000);

	}
	public void setup() {
		//backgroundImg = loadImage("MazeGame.jpg");
		
	}
	public void draw(){
		background(100);
		//image(backgroundImg,0,0);
		fill(255);
		rect(width/7, 2*height/3, width/6, height/10);
		rect(width/4, 3*height/4, width/2, height/8);
		rect(width-width/7-width/6,2*height/3 , width/6, height/10);
		textSize(24);
		text("3D Maze", width/3 , height/4);
		textSize(12);
		fill(100);
		text("Easy Mode", width/7, 2*height/3, width/7+width/6, 2*height/3+height/10);
		
		text("Hard Mode", width/7, 2*height/3, width/7+width/6, 2*height/3+height/10);
		
		text("Play Game", width/4, 3*height/4, width/4+width/2, 3*height/4+height/8);
	
	}
	
	private boolean isChosen(int clickX, int clickY, int rectX, int rectY, int rectX1, int rectY1) {
		if((clickX>=rectX && clickX<=rectX1)&&(clickY>=rectY && clickY<=rectY1)) {
			return true;
		}
		return false;
	}
	
	
	public void mouseClicked() {
		if (this.isChosen(mouseX, mouseY, width/7, 2*height/3, width/6, height/10)){
			System.out.println("easy mode");
		}
		if (this.isChosen(mouseX, mouseY, width-width/7-width/6,2*height/3 , width/6, height/10)) {
			System.out.println("hard mode");
		}
		if (this.isChosen(mouseX, mouseY, width/4, 3*height/4, width/4+width/2, 3*height/4+height/8)) {
			System.out.println("play game");
		}
	}
}
