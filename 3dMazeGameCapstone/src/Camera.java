
/**
 * ##library.name##
 * ##library.sentence##
 * ##library.url##
 *
 * Copyright ##copyright## ##author##
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 * 
 * @author      ##author##
 * @modified    ##date##
 * @version     ##library.prettyVersion## (##library.version##)
 */

import java.awt.*;

import com.jogamp.newt.opengl.GLWindow;

import processing.core.*;

/**
 * 
 * @author jrc03c This class represents the camera on the screen that you can use
 *         to move/look around
 *
 */
public class Camera {

	private Robot robot;
	private PVector center, right, forward, position, velocity;
	private float speed, xSensitivity, ySensitivity, pan, tilt, friction, fov, viewDistance;
	private Point mouse, pMouse;

	public Camera() {
		this(3, 1, 1, .75f, PConstants.PI / 3f, 1000f);
	}

	public Camera(float speed, float xSensitivity, float ySensitivity, float friction, float fov, float viewDistance) {
		try {
			robot = new Robot();
		} catch (AWTException e) {
		}

		this.speed = speed;
		this.xSensitivity = xSensitivity;
		this.ySensitivity = ySensitivity;
		this.friction = friction;
		this.fov = fov;
		this.viewDistance = viewDistance;

		position = new PVector(0f, 0f, 0f);
		right = new PVector(1f, 0f, 0f);
		forward = new PVector(0f, 0f, 1f);
		velocity = new PVector(0f, 0f, 0f);

		pan = 0;
		tilt = 0;
	}

	/**
	 * Sets up all the things to render the perspective
	 * @param g the renderer for the perspective
	 */
	public void setup(PApplet g) {
		g.perspective(fov, (float) g.width / (float) g.height, 0.01f, viewDistance);
		// Moves the mouse to the center of the screen at the start of the game
		robot.mouseMove((int) ((GLWindow) g.getSurface().getNative()).getX() + g.width / 2,
				(int) ((GLWindow) g.getSurface().getNative()).getY() + g.height / 2);
	}

	/**
	 * Displays the perspective of the player
	 * @param g The renderer for the perspective
	 */
	public void draw(PApplet g) {
		// Get the coordinates of the borders of the window
		int top = ((GLWindow) g.getSurface().getNative()).getY();
		int left = ((GLWindow) g.getSurface().getNative()).getX();
		int windowRight = g.width + left;
		int bottom = g.height + top;

		mouse = MouseInfo.getPointerInfo().getLocation();

		if (pMouse == null)
			pMouse = new Point(mouse.x, mouse.y);

		// means that the mouse went off the screen to the left so move it to the right
		if (mouse.x < left + 2 && (mouse.x - pMouse.x) < 0) {
			robot.mouseMove(windowRight - 2, mouse.y);
			mouse.x = windowRight - 2;
			pMouse.x = windowRight - 2;
		}

		// means that the mouse went off the screen to the right so move it to the left
		if (mouse.x > windowRight - 2 && (mouse.x - pMouse.x) > 0) {
			robot.mouseMove(left + 2, mouse.y);
			mouse.x = left + 2;
			pMouse.x = left + 2;
		}

		// means that the mouse went up off the screen so move it to the bottom
		if (mouse.y < top + 10 && (mouse.y - pMouse.y) < 0) {
			robot.mouseMove(mouse.x, bottom - 5);
			mouse.y = bottom - 5;
			pMouse.y = bottom - 5;
		}

		// means that the mouse went down off the screen so move it to the top
		if (mouse.y > bottom - 5 && (mouse.y - pMouse.y) > 0) {
			robot.mouseMove(mouse.x, top + 10);
			mouse.y = top + 10;
			pMouse.y = top + 10;
		}

		// map the mouse value to the corresponding angle between 0 and 2PI for x
		// rotation(pan) because you have 360º rotation
		pan += PApplet.map(mouse.x - pMouse.x, 0, g.width, 0, PConstants.TWO_PI) * xSensitivity;
		tilt += PApplet.map(mouse.y - pMouse.y, 0, g.height, 0, PConstants.PI) * ySensitivity;
		tilt = clamp(tilt, -PConstants.PI / 2.01f, PConstants.PI / 2.01f);

		// tan of pi/2 or -pi/2 is undefined so if it happens to be exactly that
		// increase it so the code works
		if (tilt == PConstants.PI / 2)
			tilt += 0.001f;
		if (tilt == -PConstants.PI / 2)
			tilt -= 0.001f;

		//Vector representing what forward is relative to the camera right now
		forward = new PVector(PApplet.cos(pan), PApplet.tan(tilt), PApplet.sin(pan));

		// make it a unit vector because the direction is all that matters
		forward.normalize();

		// subtract pi/2 from pan to get the vector perpendicular to forward to show
		// which way is right
		right = new PVector(PApplet.cos(pan - PConstants.PI / 2), 0, PApplet.sin(pan - PConstants.PI / 2));

		//have the previous mouse set to the current mouse to use it for the next call to draw()
		pMouse = new Point(mouse.x, mouse.y);

		// account for friction
		velocity.mult(friction);
		// use velocity to find out location of new position
		position.add(velocity);
		// center of the sketch is in the direction of forward but translated based on
		// how you moved so you need to take into account position
		center = PVector.add(position, forward);
		g.camera(position.x, position.y, position.z, center.x, center.y, center.z, 0, 1, 0);
	}

	
	/**
	 * Clamps the x value to within the range of min-max
	 * @param x The int x input value
	 * @param min The minimum value x can be
	 * @param max The maximum value the x can be
	 * @return
	 */
	private float clamp(float x, float min, float max) {
		if (x > max)
			return max;
		if (x < min)
			return min;
		return x;
	}

	/**
	 * Gets the vector for the forard direction
	 * @return the vector for the forward direction
	 */
	public PVector getForward() {
		return forward;
	}

	/**
	 * Gets the current position of the camera
	 * @return the current position
	 */
	public PVector getPosition() {
		return position;
	}
	
	/**
	 * Gets the current velocity of the camera
	 * @return the current velocity
	 */
	public PVector getVelocity() {
		return velocity;
	}

	/**
	 * Gets the current center of the camera
	 * @return the current center
	 */
	public PVector getCenter() {
		return center;
	}

	/**
	 * Gets the current vector for the right direction of the camera
	 * @return the current vector of the right direction
	 */
	public PVector getRight() {
		return right;
	}

	/**
	 * Gets the current pan of the camera
	 * @return the current pan
	 */
	public float getPan() {
		return pan;
	}
	
	/**
	 * Gets the current tilt of the camera
	 * @return the current tilt
	 */
	public float getTilt() {
		return tilt;
	}
	
	/**
	 * Moves the camera in the x direction by dir
	 * @param dir amount to be moved
	 */
	public void moveX(int dir) {
		velocity.add(PVector.mult(right, speed * dir));
	}
	
	/**
	 * Moves the camera in the z direction by dir
	 * @param dir amount to be moved
	 */
	public void moveZ(int dir) {
		velocity.add(PVector.mult(forward, speed * dir));
	}
	/**
	 * Returns the current X-Sensitivity of the camera
	 * @return the current X Sensitivity
	 */
	public float xSensitivity() {
		return xSensitivity;
	}

	/**
	 * Returns the current Y-Sensitivity of the camera
	 * @return the current Y Sensitivity
	 */
	public float ySensitivity() {
		return ySensitivity;
	}
	/**
	 * sets the XSensitivity of the camera
	 * @param f the new X-Sensitivity
	 */
	public void setXSensitivity(float f) {
		xSensitivity = f;
	}

	/**
	 * sets the YSensitivity of the camera
	 * @param f the new Y-Sensitivity
	 */
	public void setYSensitivity(float f) {
		ySensitivity = f;
	}

	/**
	 * Gets the speed of the camera
	 * @return the current speed
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * sets the speed of the camera
	 * @param f the new speed
	 */
	public void setSpeed(float f) {
		speed = f;
	}

	/**
	 * Gets the current friction of the camera
	 * @return the current friction
	 */
	public float getFriction() {
		return friction;
	}

	/**
	 * Sets the friction of the camera
	 * @param f the new friction
	 */
	public void setFriction(float f) {
		friction = f;
	}

	/**
	 * Gets the current FOV of the camera
	 * @return the current FOV
	 */
	public float getFOV() {
		return fov;
	}

	/**
	 * Sets the FOV of the camera
	 * @param f the new FOV
	 */
	public void setFOV(float f) {
		fov = f;
	}

	/**
	 * Gets the viewDistance of the camera
	 * @return the view distance
	 */
	public float getViewDistance() {
		return viewDistance;
	}

	/**
	 * sets the viewDistance of the camera
	 * @param f a float that is the view distance
	 */
	public void setViewDistance(float f) {
		viewDistance = f;
	}

	/**
	 * Gets the mouse point and returns it
	 * @return the mouse
	 */
	public Point getMouse() {
		return mouse;
	}

	/**
	 * Sets the mouse at the coordinates of the mouse passed in
	 * @param mouse a point that is a mouse
	 */
	public void setMouse(Point mouse) {
		robot.mouseMove(mouse.x, mouse.y);
	}

	/**
	 * Sets the mouse at the coordinates specified
	 * @param x X-Coordinate of the mouse
	 * @param y Y-Coordinate of the mouse
	 */
	public void setMouse(int x, int y) {
		robot.mouseMove(x, y);
	}

	/**
	 * sets the pan of the camera by the angle specified
	 * @param angle the angle for the pan
	 */
	public void setPan(double angle) {
		pan = (float) angle;
	}
	
	/**
	 * Sets the position of the perspective at the coordinates specified
	 * @param x X-Coordinate of the camera
	 * @param y Y-Coordinate of the camera
	 * @param z Z-Coordinate of the camera
	 */
	public void setPosition(int x,int y,int z) {
		position.x = x;
		position.y = y;
		position.z = z;
	}
}
