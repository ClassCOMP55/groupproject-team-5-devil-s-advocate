package starter;

import acm.graphics.GImage;

public class MainApplication extends GraphicsApplication {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final String MUSIC_FOLDER = "sound";
	private static final String[] SOUND_FILES = { "in/dead.mp3", "in/theme.mp3" };
	
	private InstructionsPane InstructionsPane;
	private MainMenu menu;
	private DeadScreen DeadScreen;
	private WinScreen WinScreen; 

	
	private int count;

	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	public void run() {
		System.out.println("Hello, world!");
		InstructionsPane = new InstructionsPane(this);
		DeadScreen = new DeadScreen(this);
		WinScreen = new WinScreen(this);
		menu = new MainMenu(this);
		switchToMenu();
		GImage mario = new GImage("", 300, 100);
		add(mario);
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
