package starter;

import java.awt.Color;
import java.awt.Image;

import acm.graphics.*;

public class Entity {
	public double locationX, locationY;
	public double width, height;
	public double xVel, yVel;
    public double xVelMax, yVelMax;
    public String xDirection, yDirection, lastDirection;
	public boolean movable;
    public boolean hitTop = false, hitBottom = false, hitLeft = false, hitRight = false;
	public Id id;
	public GRect entity;
	public GImage EntImage;
	public static GImage EntImages[] = new GImage[8];
	private static int rcount = 0;
	private static int lcount = 4;

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
	//Murad commit below
	Entity(double x, double y, double width, double height, boolean movable, Id id, GImage EntImages[]) {
		entity = new GRect(x, y, width, height);
		setLocation(x, y);
		locationX = x;
		locationY = y;
		this.width = width;
		this.height = height;
		this.movable = movable;
		this.id = id;
		for (int i = 0; i < EntImages.length; i++) {
			this.EntImages[i] = EntImages[i];
		}
		
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
	 * This function allows a GImage object to be returned for viewing on the screen in MainApplication.
	 * Extra functionality added to ensure mario faces in the last direction moved.
	 */
	public GImage display() {//maybe turn this into a case statment?
		if (lastDirection == "" || lastDirection == "right") {
			EntImage = EntImages[0];
			EntImage.setSize(50, 50);
			EntImage.setLocation(entity.getX(), entity.getY());
		}
		else {
			EntImage = EntImages[4];
			EntImage.setSize(50, 50);
			EntImage.setLocation(entity.getX(), entity.getY());
		}
		if (xDirection == "right") {
			EntImages[rcount].setSize(50, 50);
			EntImage = EntImages[rcount];
			rcount++;
			if (rcount > 3) {
				rcount = 0;
			}
			EntImage.setLocation(entity.getX(), entity.getY());
			return EntImage;
		}
		if (xDirection == "left") {
			EntImages[lcount].setSize(50, 50);
			EntImage = EntImages[lcount];
			lcount++;
			if (lcount > 7) {
				lcount = 4;
			}
			EntImage.setLocation(entity.getX(), entity.getY());
			return EntImage;
		}
		return EntImage;
		
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
			




