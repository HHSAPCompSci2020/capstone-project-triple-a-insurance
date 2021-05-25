import processing.core.PApplet;

/**
 * creates a trap in a location
 * @author Aayush
 */
public class Trap extends Block{
	private float x, y, z, size;
	private int fillColor;
	

	public Trap(int x, int y, int z) {
		super(x, y, z, 'w');
		this.x = x;
		this.y = y;
		this.z = z;
		gradient = 255;
		//fillColor = (int) (fillColor);
	}

}
