/**
 * The place where collisions happens and the player moves
 * @author Ameya
 * 
 */
public class Player extends Perspective{
	private float width, height, depth;
	
	
	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public float getDepth() {
		return depth;
	}
	public void moveTo(float x, float y, float z) {
		this.getPosition().x = x;
		this.getPosition().y = y;
		this.getPosition().z = z;
	}

}
