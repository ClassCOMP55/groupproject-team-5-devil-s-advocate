package starter;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Graphics;
import javax.imageio.ImageIO;

public class SpriteSheet {
	public BufferedImage sheet;
	
	public SpriteSheet(String filePath) {
		try {
			sheet = ImageIO.read(getClass().getResource(filePath));
		} catch (IOException e) {
			System.out.println("Error in reading from sprite sheet");
			e.printStackTrace();
		}
	}
	
	public BufferedImage getSprite(int x, int y, int w, int h) {
		return sheet.getSubimage(x * w, y * h, w, h);
	}

}
