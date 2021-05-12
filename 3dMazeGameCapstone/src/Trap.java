import processing.core.PApplet;

/**
 * 
 * @author asampath803 This class represents a block in 3D space, whether it is
 *         the ground or the wall
 */
public class Trap extends Block{
	private float x, y, z, size;
	private int fillColor;
	private boolean visited;
	

	public Trap(float x, float y, float z, float size) {
		super(x, y, z, size);
		fillColor = (int) (50 + 155);
	}

	public void display(PApplet g) {
		g.pushMatrix();
		g.translate(x, y, z);
		g.fill(0,fillColor,fillColor);
		g.box(size, size, size);
		g.popMatrix();
	}

	public boolean isPointInCube(float x, float y, float z) {
		// the x y z coords of the block are in the center so +/- by size/2 in all
		// directions to get the edges
		float left = this.x - size / 2;
		float right = this.x + size / 2;
		float top = this.y - size / 2;
		float bottom = this.y + size / 2;
		float front = this.z - size / 2;
		float back = this.z + size / 2;
		if (x > left && x < right && y > top && y < bottom && z > front && z < back) {
			return true;
		}

		return false;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public float getSize() {
		return size;
	}
	
	public void moveDown(){
	    y += 5;
	}
	
	public void setVisited(boolean b) {
		visited = b;
	}
	
	public boolean getVisited() {
		return visited;
	}
}
