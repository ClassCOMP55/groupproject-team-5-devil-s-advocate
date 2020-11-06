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
	private static final String[] SOUND_FILES = { "in/levelPass.mp3", "in/theme.mp3" };
	public static SpriteSheet sheet;
	public static Sprite player;
	private Graphics g;
	
	private InstructionsPane InstructionsPane;
	private MainMenu menu;
	private DeadScreen DeadScreen;
	private WinScreen WinScreen; 

	
	private int count;
	

	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		sheet = new SpriteSheet("/SpriteSheet/SpriteSheet.png");//Code to read in the first sprite sheet
		player = new Sprite (sheet, 1, 1);
	}

	public void run() {
		System.out.println("Hello, world!");
		InstructionsPane = new InstructionsPane(this);
		DeadScreen = new DeadScreen(this);
		WinScreen = new WinScreen(this);
		menu = new MainMenu(this);
		switchToMenu();                                      //Timer after menu then gameloop 
		GImage mario = new GImage("", 300, 100);
		add(mario);
		GImage gPlayer = new GImage(player.getBufferedImage(), 250, 100);
		gPlayer.setSize(50, 50);
		add(gPlayer);
	}

	public void switchToMenu() { // change/time the audio in the switchTo functions 
		playRandomSound();
		count++;            // Move to next audio file...
		switchToScreen(menu); 
	}

	public void switchToInstructions() {
		playRandomSound();
		count ++;
		switchToScreen(InstructionsPane);
		stopRandomSound(); 	// to stop the theme sound before switching to menu page...
	}
	
	public void switchToDead() {
		playRandomSound();
		switchToScreen(DeadScreen);
	}
	
	public void switchToWin() {
		playRandomSound();
		switchToScreen(WinScreen); 
	}

	private void playRandomSound() {
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.playSound(MUSIC_FOLDER, SOUND_FILES[count % SOUND_FILES.length]);
	}


	public void stopRandomSound() {				// function to stop the random sound from being played...
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.stopSound(MUSIC_FOLDER, SOUND_FILES[count % SOUND_FILES.length]);
	}
}
	
