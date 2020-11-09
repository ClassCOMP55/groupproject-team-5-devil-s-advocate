package starter;

import java.awt.Color;
import java.awt.Graphics;

import acm.graphics.GRect;


public class Entity {
	public int locationX;
	public int locationY;
	public int width;
	public int height;
	public boolean move;
	public Id id;
	public PhysicsAttr physics;
	/**
	 * I've added all the attributes from physics directly into the class,
	 * there my be some ways to sift through what actuallky needs to be seperated if any.
	 * 
	 */ 
	
	public double xVel, yVel;
    public double xVelMax, yVelMax;
    public String xDirection, yDirection;
    public boolean hitTop, hitBottom, hitLeft, hitRight;
    public boolean enableGravity = true;

	public Entity(int x, int y, int width, int height, boolean move, Id id){
		locationX = x;
		locationY = y;
		this.width = width;
		this.height = height;
		this.move = move;
		this.id = id;
	}
	
//	public void increment() {
//		locationX += xVel;
//		locationY += yVel;
//	}
	
	
	public GRect display() {
		GRect entity = new GRect(width, height);
		entity.setLocation(locationX, locationY);
		entity.setFillColor(Color.red);
		entity.setFilled(true);
		return entity;
	}

	/*
	 * Setters and getters for this class are going to be located after this commented section
	 * 
	 * 
	 * 
	 */
}
			




