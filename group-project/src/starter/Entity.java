package starter;

import java.awt.Color;
import java.awt.Image;

import acm.graphics.*;

public class Entity {
	public double locationX, locationY;
	public double width, height;
	public double xVel, yVel, testVel;//added for testing
    public double xVelMax, yVelMax;
    public String xDirection, yDirection;
    public String lastDirection = "right"; // Just initializied this to prevent null errors
	public boolean movable;
    public boolean hitTop = false, hitBottom = false, hitLeft = false, hitRight = false;
	public Id id;
	public GRect entity;
	public GImage EntImage;
	public GImage EntImages[] = new GImage[10];
	public Image EntityImages[] = new Image[10]; // ***private*** Stores the Images of the Mario's movement frames, NOT GImages
	private double rcount = 0;
	private double lcount = 4;
	private double gcount = 0;
	public Boolean falling = true;//added to test falling
	public Boolean dead = false;

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
			/**
			 * Commented the line below out because switching images no longer use GImages,
			 * Images are used instead.
			 */
			// this.EntImages[i] = EntImages[i];
			/**
			 * The line below is where the array of EntImages from the constructor is converted into Images.
			 */
		
			this.EntityImages[i] = EntImages[i].getImage().getScaledInstance(50, 50, Image.SCALE_REPLICATE);
			
		}
		EntImage = new GImage(EntImages[0].getImage());
		EntImage.setSize(50, 50);
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
	public void playerDisplay() {
		switch (xDirection) {
		case "left":
			EntImage.setImage(EntityImages[(int)lcount]);
			lcount += 0.3;
			lastDirection = "left";
			if (lcount > 7) {
				lcount = 4;
			}
			break;
			
		case "right":
			EntImage.setImage(EntityImages[(int)rcount]);
			rcount += 0.3;
			if (rcount > 3) {
				rcount = 0;
			}
			lastDirection = "right";
			break;
			
		case "stop":
			switch (lastDirection) {
			case "left":
				EntImage.setImage(EntityImages[4]);
				break;
			case "right":
				EntImage.setImage(EntityImages[0]);
				break;
			}
			break;
		}
		switch (yDirection) {
		case "jump":
			if (lastDirection == "left") {
				EntImage.setImage(EntityImages[8]);
			}
			else if (lastDirection == "right") {
				EntImage.setImage(EntityImages[9]);
			}
			break;
		}
		EntImage.setLocation(entity.getX()-14, entity.getY()+2);

	public void enemyDisplay() {
		EntImage.setSize(50, 50);
		EntImage.setImage(EntityImages[(int)gcount % 2]);
		gcount += 0.1; // Change this value to change speed
		if (gcount == 100) {
			gcount = 0;
		}
		EntImage.setLocation(entity.getX()-12, entity.getY()-22);
	}
	
	/**
	 * This function passes to GObject's setFilled(), only applies if entity is GRect
	 * @param a - whether object is filled with color or not
	 */
	public void setFilled(boolean a) { //*** This code is slated for deletion
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
			




