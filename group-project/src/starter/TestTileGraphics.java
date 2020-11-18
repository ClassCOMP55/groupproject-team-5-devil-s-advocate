package starter;

import acm.program.GraphicsProgram;
import acm.graphics.*;

public class TestTileGraphics extends GraphicsProgram {
	TMXLevelReader reader;
	private final int WINDOW_SIZE = 700;
	GCompound compound = new GCompound();
	
	public void init() {
		setSize(WINDOW_SIZE,WINDOW_SIZE);
		requestFocus();
		reader = new TMXLevelReader("/levels/test/Test_Level.tmx", "/SpriteSheet/tileset.png", WINDOW_SIZE);
		for (GImage image : reader.allGImages) {
			compound.add(image);
		}
		add(compound);
	}

	public void run() {
		gameLoop();
	}
	
	private void gameLoop() {
	    long now;
	    long updateTime;
	    long wait;

	    final int TARGET_FPS = 60;
	    final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

	    while (true) {
	        now = System.nanoTime();

	        updateTime = System.nanoTime() - now;
	        wait = (OPTIMAL_TIME - updateTime) / 1000000;
	        
	        if (-compound.getX() == compound.getWidth() - WINDOW_SIZE) {
	        	
	        } else {
	        	compound.move(-1,0);
	        }
	        
	        try {
	            Thread.sleep(wait);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
}
