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
	private static final String CLICK = "in/clique.mp3"; 			// imported a new .mp3 file for click sound...
	private static final String[] SOUND_FILES = { "in/theme.mp3", "in/dead.mp3" };
	public static SpriteSheet sheet;
	public static Sprite player;
	private Graphics g;
	
	private InstructionsPane InstructionsPane;
	private MainMenu menu;
	private DeadScreen DeadScreen;
	private WinScreen WinScreen; 
	
	public Player futureMario;
	public Entity futureEnemy;
	public GImage players;
	private int count;
	

	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		sheet = new SpriteSheet("/SpriteSheet/SpriteChar.png");//Code to read in the first sprite sheet
		player = new Sprite (sheet, 2,0);
		futureMario = new Player(450, 150, 50, 50, true, Id.player);//**This what entity will roughly look like in implementation
		futureEnemy = new Entity(150, 150, 50, 50, true, Id.enemy);
	}

	public void run() {
		System.out.println("Hello, world!");
		InstructionsPane = new InstructionsPane(this);
		DeadScreen = new DeadScreen(this);
		WinScreen = new WinScreen(this);
		menu = new MainMenu(this);
		switchToMenu();                                      //Timer after menu then gameloop 
		add(futureMario.display());
		players = new GImage(player.getBufferedImage(), 450, 125);
		players.setSize(60, 60);
		add(players);//Sprite of the Mario that is represented by GImage
		add(futureEnemy.display());
	}

	public void switchToMenu() { // change/time the audio in the switchTo functions 
		playRandomSound();
		count++;							// Move to next audio file...
		
		switchToScreen(menu); 
	}

	public void switchToInstructions() {
		playRandomSound();
		count ++;
		switchToScreen(InstructionsPane);
		playClickSound();					// called function to play click sound...
		stopRandomSound(); 					// to stop the theme sound before switching to menu page...
	}
	
	public void switchToDead() {
		count++;
		playRandomSound();
		playClickSound();
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
	
	public void playClickSound() {				//function to play the button sound...
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.playSound(MUSIC_FOLDER, CLICK);
	}
}
	
