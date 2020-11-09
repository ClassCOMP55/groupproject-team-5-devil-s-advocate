package starter;

import java.awt.Graphics;

import acm.graphics.GRect;

import java.awt.Color;


public class Player extends Entity {

	public Player(int x, int y, int width, int height, boolean move, Id id) {
		super(x, y, width, height, move, id);
		// TODO Auto-generated constructor stub
	}
	
	public GRect display() {
		GRect mario = new GRect(width, height);
		mario.setLocation(locationX, locationY);
		mario.setFillColor(Color.green);
		mario.setFilled(true);
		return mario;
	}

}
