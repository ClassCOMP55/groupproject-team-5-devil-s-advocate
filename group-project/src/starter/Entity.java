package starter;

import java.awt.Graphics;//whatever we are using to render

public class Entity {
	public int locationX;
	public int locationY;
	public int width;
	public int height;
	public boolean move;

	public Entity(int x, int y, int width, int height, boolean move){
		locationX = x;
		locationY = y;
		this.width = width;
		this.height = height;
		this.move = move;
	}
	
	public void displayImage(Graphics b) {
		
		
	}
	
}
			




