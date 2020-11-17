package starter;

import acm.program.GraphicsProgram;
import acm.graphics.*;

public class TestTileGraphics extends GraphicsProgram {
	
	TMXLevelReader reader;
	private final int WINDOW_SIZE = 700;
	
	public void init() {
		setSize(WINDOW_SIZE,WINDOW_SIZE);
		requestFocus();
		reader = new TMXLevelReader("/levels/test/Test_Level.tmx", "/SpriteSheet/tileset.png", WINDOW_SIZE);
	}

	public void run() {
		for (GImage image : reader.allGImages) {
			add(image);
		}
	}
}
