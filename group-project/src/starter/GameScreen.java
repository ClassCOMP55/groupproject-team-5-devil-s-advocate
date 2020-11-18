package starter;

import acm.program.GraphicsProgram;
import acm.graphics.*;

public class GameScreen extends GraphicsPane {
	private MainApplication program;
	Level reader;
	private final int WINDOW_SIZE = 700;
	
	 public GameScreen(MainApplication app) {
	        super();
	        program = app;
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
	        
//	        if (-compound.getX() == compound.getWidth() - WINDOW_SIZE) {
//	        	
//	        } else {
//	        	compound.move(-1,0);
//	        }
	        
	        try {
	            Thread.sleep(wait);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}

	@Override
	public void showContents() {
		reader = new Level("/levels/test/Test_Level.tmx", "/SpriteSheet/tileset.png", WINDOW_SIZE);
		for (GImage image : reader.allGImages) {
			program.add(image);
		}
		gameLoop();
	}

	@Override
	public void hideContents() {
		reader = new Level("/levels/test/Test_Level.tmx", "/SpriteSheet/tileset.png", WINDOW_SIZE);
		for (GImage image : reader.allGImages) {
			program.remove(image);
		}
	}
}
