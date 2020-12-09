package starter;

import java.awt.event.*;
import acm.graphics.*;

public class MainApplication extends GraphicsApplication {
	// Window Options
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	
	// Sound Files
	public static final String MUSIC_FOLDER = "sound";
	private static final String CLICK = "in/clique.mp3"; 	
	private static final String THEME = "in/theme.mp3";
	private static final String GOOMBA = "in/goomba.mp3";
	private static final String DEAD = "in/dead.mp3";
	private static final String WIN = "in/levelPass.mp3";
	private static final String[] SOUND_FILES = { "in/theme.mp3", "in/dead.mp3" };
	
	// Graphics, Entities, and Panes
	public static SpriteSheet sheetNew;
	public static Sprite playerArray[]= new Sprite[10];
	public static GImage playerGImage[] = new GImage[10];
	public static Sprite goombaArray[]= new Sprite[3];
	public static GImage goombaGImage[] = new GImage[3];
	private InstructionsPane InstructionsPane;
	private MainMenu menu;
	private DeadScreen DeadScreen;
	private WinScreen WinScreen; 
	private Entity Mario, Goomba;
	private String currScreen = "";
	private GCompound levelCompound = new GCompound();

	// Physics and level
	private PhysicsEngine Physics;
	private Level levelOne = new Level("/levels/OfficialLevel1/OfficialLevel1.tmx", "/SpriteSheet/tileset.png", WINDOW_HEIGHT);
	private Level currentLevel;

	// Boolean for inputs
	private boolean w = false;
	private boolean a = false;
	private boolean s = false;
	private boolean d = false;
	
	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		sheetNew = new SpriteSheet("/SpriteSheet/MarioFinalChar.png");//C
		for (int i = 0; i < playerArray.length; i++) {
			playerArray[i] = new Sprite (sheetNew, i, 0);
			playerGImage[i] = new GImage (playerArray[i].getBufferedImage());
			if (i < 3) {
				goombaArray[i] = new Sprite (sheetNew, i, 1);
				goombaGImage[i] = new GImage (goombaArray[i].getBufferedImage());
			}
		}
	}

	/**
	 * Initializes the mario, physics and other stuff to be used in the game
	 * Also used to reset the game when player wins/lose.
	 */
	public void MarioInit() {
		currentLevel = levelOne;
		Mario = new Entity(100, 400, 27, 50, Id.player, playerGImage);
		Physics = new PhysicsEngine (Mario);
		Physics.windowWidth = WINDOW_WIDTH;
		Physics.setWinningSpace(currentLevel.winningSpace);
		for (Entity a: currentLevel.hitboxes) {
			Physics.addImmovable(a);
		}
		Mario.xVel = 0;
		Mario.yVel = 0;
		Mario.xVelMax = 6;
		Mario.yVelMax = 10;
		Mario.xDirection = Mario.yDirection = Mario.lastDirection = "";
	}
	
	/**
	 * Initializes 1 Goomba and variables for all associated actions.
	 */
	public void GoombaInit(double x, double y) {
		Goomba = new Entity(x, y, 25, 25, Id.enemy, goombaGImage);
		Physics.addMovable(Goomba);
		Goomba.yVel = 0;
		Goomba.xVel = Goomba.xVelMax = 2;
		Goomba.yVelMax = 10;
		Goomba.xDirection = Goomba.yDirection = Goomba.lastDirection = "";
	}

	public void GameInit() {
		levelCompound = new GCompound();
		for (GImage a : currentLevel.allGImages) {
			levelCompound.add(a);
		}

		add(levelCompound);
		add(Mario.EntImage);
		for (Entity m: Physics.movable) {
			if (m.id.equals(Id.enemy)) {
				add(m.EntImage);
			}
		}
	}

	public void run() {
		InstructionsPane = new InstructionsPane();
		DeadScreen = new DeadScreen();
		WinScreen = new WinScreen();
		menu = new MainMenu();
		switchToMenu();                                     
		addKeyListeners(new Input());
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

			// Game code here
			if (currScreen == "GameScreen") {
				Boolean keysPressed[] = {w, a, s, d};
				Physics.update(keysPressed);
				Mario.playerDisplay();
				processCamera();

				for (Entity m: Physics.movable) {
					if (m.id.equals(Id.enemy)) {
						m.enemyDisplay();
					}
				}

				// Check if Mario is dead, either falling of the screen or Mario.dead == true
				if (Mario.getY() > 650 || Mario.dead == true) {
					playDeadSound();		// Play Dead Screen Music...
					switchToDead();
				}
				
				// Check if Mario has touched the flag pole and won
				if (Physics.won()) {
					switchToWin();
				}
			}

			try {
				Thread.sleep(wait);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Moves camera according to Mario's position on the screen
	 */
	public void processCamera() {
		if (Mario.getX() > 300) {
			levelCompound.move(-Mario.xVel, 0);
			Physics.moveHitboxes(-Mario.xVel, 0);
			Physics.moveEnemies(-Mario.xVel, 0);
			Mario.setLocation(299, Mario.getY());

			levelCompound.move(-3, 0);
			Physics.moveHitboxes(-3, 0);
			Physics.moveEnemies(-3, 0);
		}
	}

	public void switchToMenu() {
		playThemeSound();
		if (currScreen != "MainMenu") {
			removeAll();
			for (GObject a : menu.objects) {
				add(a);
			}
			currScreen = "MainMenu";
		}
	}

	public void switchToInstructions() {
		playThemeSound(); // play Theme Sound...
		if (currScreen != "InstructionsPane") {
			removeAll(); // removes all the contents of the previous screen
			for (GObject a : InstructionsPane.objects) {
				add(a);
			}
			currScreen = "InstructionsPane";
		}
		playClickSound(); // called function to play click sound...
	}

	public void switchToGameScreen() {
		playThemeSound(); // play theme sound...
		removeAll();
		levelOne.reset(); // Reloads the TMX File to reset positions and stuff
		MarioInit();
		for (GPoint p : levelOne.goomba_points) {
			GoombaInit(p.getX(), p.getY());
		}
		GameInit();
		currScreen = "GameScreen";
	}

	public void switchToDead() {
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.stopSound(MUSIC_FOLDER, THEME); // plays the theme...
		audio.playSound(MUSIC_FOLDER, DEAD); // plays the dead-screen sound...

		if (currScreen != "DeadScreen")
		{
			removeAll(); // removes all the contents of the previous screen
			for (GObject a : DeadScreen.objects) {
				add(a);
			}
			currScreen = "DeadScreen";
		}

	}

	public void switchToWin() {
		stopThemeSound(); // stop theme sound...
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.playSound(MUSIC_FOLDER, WIN); // plays the Win-screen sound...
		playClickSound();
		if (currScreen != "WinScreen") {
			removeAll(); // removes all the contents of the previous screen
			for (GObject a : WinScreen.objects) {
				add(a);
			}
			currScreen = "WinScreen";
		}
	}


	public void stopRandomSound() { // function to stop the random sound from being played...
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.stopSound(MUSIC_FOLDER, SOUND_FILES[SOUND_FILES.length - 1]);
	}

	public void playClickSound() { //function to play the button click sound...
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.playSound(MUSIC_FOLDER, CLICK);
	}
	public void playThemeSound() { //function to play the theme sound...
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.playSound(MUSIC_FOLDER, THEME);
	}
	public void playDeadSound() { //function to play the DEAD sound...
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.playSound(MUSIC_FOLDER, DEAD);
	}
	public void playGoombaSound() { //function to play the GOOMBA sound...
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.playSound(MUSIC_FOLDER, GOOMBA);
	}
	public void stopThemeSound() { //function to stop THEME sound...
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.stopSound(MUSIC_FOLDER, THEME);

	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = getElementAt(e.getX(), e.getY());
		if (obj == menu.playButton) {
			playClickSound();  
			playThemeSound();
			switchToInstructions();
		}

		if(obj == menu.exitButton) { // Exit from the application
			System.exit(0);
		}

		if (obj == InstructionsPane.returnIcon) {
			playClickSound();  
			playThemeSound(); // Play the clique.mp3 sound on button click
			switchToMenu();

		}

		if(obj == InstructionsPane.continueButton) {
			playClickSound(); // Play the clique.mp3 sound on button click
			switchToGameScreen();
			playThemeSound(); // Stop the ongoing sound
		}

		if (obj == DeadScreen.quitButton) { 
			playClickSound();
			stopRandomSound(); // Stop the ongoing sound
			System.exit(0);
		}
		if (obj== DeadScreen.playAgainButton) {
			playClickSound();
			switchToMenu();

		} 
		if (obj== WinScreen.playAgainButton) {
			playClickSound();
			switchToMenu();
			playThemeSound();
		}
		if (obj == WinScreen.quitButton) {
			playClickSound();
			stopRandomSound(); // Stop the ongoing sound
			System.exit(0);
		}
	}
	private class Input implements KeyListener {
		// Sets key pressed status for keyboard
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_W:
				w = true;
				break;
			case KeyEvent.VK_A:
				a = true;
				break;
			case KeyEvent.VK_S:
				s = true;
				break;
			case KeyEvent.VK_D:
				d = true;
				break;
			}
		}
		@Override
		public void keyReleased(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_W:
				w = false;
				break;
			case KeyEvent.VK_A:
				a = false;
				break;
			case KeyEvent.VK_S:
				s = false;
				break;
			case KeyEvent.VK_D:
				d = false;
				break;
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// Blank but needs to be implemented as there would be an error without this
		}	
	}
}