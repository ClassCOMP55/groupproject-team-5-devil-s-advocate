package starter;

import acm.graphics.GImage;

public class MainApplication extends GraphicsApplication {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final String MUSIC_FOLDER = "sound";
	private static final String[] SOUND_FILES = { "in/dead.mp3", "in/theme.mp3" };
	
	private InstructionsPane InstructionsPane;
	private MainMenu menu;
	private int count;

	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	public void run() {
		System.out.println("Hello, world!");
		InstructionsPane = new InstructionsPane(this);
		menu = new MainMenu(this);
		switchToMenu();
		GImage mario = new GImage("", 300, 100);
		add(mario);
	}

	public void switchToMenu() {
		playRandomSound();
		count++;
		switchToScreen(menu);
	}

	public void switchToInstructions() {
		playRandomSound();
		switchToScreen(InstructionsPane);
	}

	private void playRandomSound() {
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.playSound(MUSIC_FOLDER, SOUND_FILES[count % SOUND_FILES.length]);
	}
}
