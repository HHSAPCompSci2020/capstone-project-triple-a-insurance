import processing.core.PApplet;

/**
 * using processing to create this library, run when you click h
 * @author Ameya
 *
 */
public class MenuScreen extends PApplet{
	private boolean resume = false;
	private int timer;
	/**
	 * draws rects as buttons and each do different things
	 */
	public void draw() {
		background(255);
		this.textSize(14);
		fill(0);
		this.rect(width/7, height/3, width/6, height/8);
		this.rect(width-width/7-width/6, height/3, width/6, height/8);
		fill(255);
		this.text("Resume", width/7, height/3, width/7+width/6, height/3+height/8);
		this.text("Quit Game", width-width/7-width/6, height/3, width-width/7, height/3+height/8);
		fill(0);
		this.text("Time Left: " + timer, width/4, 6*height/7);
	}
	
	/**
	 * This is a helper method that is a lot like the isPointInside method
	 * @param clickX mouseX location
	 * @param clickY mouseY location
	 * @param rectX  the left most rectangle coordinate
	 * @param rectY  the topmost rectangle coordinate
	 * @param rectX1 the rightmost rectangle coordinate
	 * @param rectY1 the bottommost rectangle coordinate
	 * @return whether or not point was inside
	 */
	private boolean isChosen(int clickX, int clickY, int rectX, int rectY, int rectX1, int rectY1) {
		if((clickX>=rectX && clickX<=rectX1)&&(clickY>=rectY && clickY<=rectY1)) {
			return true;
		}
		return false;
	}
	
	/**
	 * mouseClicked just runs to see if you clicked anything
	 * runs the prompt you wanted
	 */
	public void mouseClicked() {
		if (this.isChosen(mouseX, mouseY, width/7, height/3, width/7+width/6, height/3+height/8)){
			System.out.println("resume");
			resume = true;
		}
		if (this.isChosen(mouseX, mouseY,width-width/7-width/6, height/3, width-width/7, height/3+height/8)) {
			System.out.println("quit");
			System.exit(5);
		}
	}
	
	/**
	 * returns the resume field
	 * @return boolean whether to resume or not
	 */
	public boolean returnResume() {
		return resume;
	}
	
	/**
	 * shows the timer in the class
	 */
	public void updateTimer(int time) {
		timer = time;
	}
	
}
