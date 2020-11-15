package starter;

import java.awt.Color;
import acm.graphics.*;

public class Entity {
	public double locationX, locationY;
	public double width, height;
	public double xVel, yVel;
    public double xVelMax, yVelMax;
    public String xDirection, yDirection;
	public boolean movable;
    public boolean hitTop = false, hitBottom = false, hitLeft = false, hitRight = false;
	public Id id;
	public GRect entity;

    Entity() {} // Constructor to not do anything
    
	Entity(double x, double y, double width, double height, boolean movable, Id id) {
		entity = new GRect(x, y, width, height);
		setLocation(x, y);
		locationX = x;
		locationY = y;
		this.width = width;
		this.height = height;
		this.movable = movable;
		this.id = id;
	}

	/**
	 * This function passes to GObject's move()
	 * @param x - pixels to move in x direction
	 * @param y - pixels to move in x direction
	 */
	public void move(double x, double y) {
		entity.move(x, y);
	}
	
	/**
	 * This function passes to GObject's setFilled(), only applies if entity is GRect
	 * @param a - whether object is filled with color or not
	 */
	public void setFilled(boolean a) {
		entity.setFilled(a);
	}
	
	/**
	 * This function passes to GObject's setColor()
	 * @param c - Color
	 */
	public void setColor(Color c) {
		entity.setColor(c);
	}
	
	/**
	 * This function passes to GObject's setFillColor()
	 * @param c - Color
	 */
	public void setFillColor(Color c) {
		entity.setColor(c);
	}
	
	/**
	 * This function passes to GObject's setLocation()
	 * @param x - X coordinate
	 * @param y - Y coordinate
	 */
	public void setLocation(double x, double y) {
		entity.setLocation(x, y);
	}
	
	/**
	 * @return Entity's X Coordinate
	 */
	public double getX() {
		return entity.getX();
	}
	
	/**
	 * @return Entity's Y Coordinate
	 */
	public double getY() {
		return entity.getY();
	}
	
	/**
	 * @return Entity's width
	 */
	public double getWidth() {
		return entity.getWidth();
	}
	
	/**
	 * @return Entity's height
	 */
	public double getHeight() {
		return entity.getHeight();
	}
	
	@Override
	public String toString() {
		return entity.getX() + ", " + entity.getY() + ", " + (entity.getX() + entity.getWidth()) + ", " + (entity.getY() + entity.getHeight()); 
	}
}
			




