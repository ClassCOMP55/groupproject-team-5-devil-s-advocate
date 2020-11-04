package starter;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.Component;

import acm.graphics.GImage;

public class MainApplication extends GraphicsApplication {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final String MUSIC_FOLDER = "sound";
	private static final String[] SOUND_FILES = { "in/dead.mp3", "in/theme.mp3" };
	public static SpriteSheet sheet;
	public static Sprite player;
	private Graphics g;
	
	private InstructionsPane somePane;
	private MainMenu menu;
	private int count;
	

	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		sheet = new SpriteSheet("/SpriteSheet/SpriteSheet.png");//Code to read in first sprite sheet
		player = new Sprite (sheet, 1, 1);
	}

	public void run() {
		System.out.println("Hello, world!");
		somePane = new InstructionsPane(this);
		menu = new MainMenu(this);
		switchToMenu();
		GImage mario = new GImage("", 300, 100);
		add(mario);
		GImage gPlayer = new GImage(player.getBufferedImage(), 250, 100);
		gPlayer.setSize(50, 50);
		add(gPlayer);
	}

	public void switchToMenu() {
		playRandomSound();
		count++;
		switchToScreen(menu);
	}

	public void switchToSome() {
		playRandomSound();
		switchToScreen(somePane);
	}

	private void playRandomSound() {
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.playSound(MUSIC_FOLDER, SOUND_FILES[count % SOUND_FILES.length]);
	}
	
//	public void render() {                                     *** This is Ade'...Please leave this code, trying to figure out how to use buffere strategy
//		BufferStrategy buffStrat = Canvas.getBufferStrategy();
//		if (buffStrat == null) {
//			Canvas.createBufferStrategy(3);
//			return;
//		}
//		Graphics g = buffStrat.getDrawGraphics();
//		//g.drawImage(player.getBufferedImage(),250, 250, 32, 32, null);
//	}
}
