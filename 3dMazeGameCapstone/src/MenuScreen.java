import processing.core.PApplet;

/**
 * using processing to create this library, run when you click escape
 * @author ameya
 *
 */
public class MenuScreen extends PApplet{
	public void draw() {
		background(40);
		this.textSize(14);
		this.rect(width/7, height/3, width/6, height/8);
		this.text("Reset the Game", width/7, height/3, width/7+width/6, height/3+height/8);
		this.rect(width-width/7-width/6, height/3, width/6, height/8);
		this.text("Return to HomeScreen", width-width/7-width/6, height/3, width-width/7, height/3+height/8);
	}
	
	private boolean isChosen(int clickX, int clickY, int rectX, int rectY, int rectX1, int rectY1) {
		if((clickX>=rectX && clickX<=rectX1)&&(clickY>=rectY && clickY<=rectY1)) {
			return true;
		}
		return false;
	}
	
	
	public void mouseClicked() {
		if (this.isChosen(mouseX, mouseY, width/7, height/2, width/7+width/6, 2*height/3+height/10)){
			System.out.println("easy mode");
		}
		if (this.isChosen(mouseX, mouseY,width-width/7-width/6, height/2, width-width/7, 2*height/3+height/10)) {
			System.out.println("hard mode");
		}
		if (this.isChosen(mouseX, mouseY, width/4, 3*height/4, width/4+width/2, 3*height/4+height/8)) {
			System.out.println("play game");
		}
	}
	
}
