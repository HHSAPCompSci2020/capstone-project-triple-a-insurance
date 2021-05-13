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
	

	public Trap(int x, int y, int z) {
		super(x, y, z, 't');
		fillColor = (int) (100);
		visited = false;
	}

	public void display(PApplet g) {
		g.pushMatrix();
		g.translate(x, y, z);
		g.fill(0,200,200);
		g.box(size, size, size);
		g.popMatrix();
	}
}
