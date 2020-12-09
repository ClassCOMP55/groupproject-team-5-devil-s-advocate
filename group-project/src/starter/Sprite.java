package starter;

import java.awt.image.BufferedImage;
/*
 * Sprite class is used to get images from the sprite sheet.
 * After the sprite sheet is obtained, this class turns it into a buffered image
 */

public class Sprite {
	public SpriteSheet sheet;
	public BufferedImage image;
	
	/*
	 * Sprite() is used to read in image from a sprite sheet
	 * After the sprite sheet is obtained, this class turns it into a buffered image
	 */
	public Sprite(SpriteSheet sheet, int x, int y) {
		 image = sheet.getSprite(x, y);
	}
	
	public BufferedImage getBufferedImage() {
		return image;
	}
}
