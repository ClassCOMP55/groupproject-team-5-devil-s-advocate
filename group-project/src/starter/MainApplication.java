package starter;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.Component;
import acm.graphics.*;
import jdk.internal.util.xml.impl.Input;

public class MainApplication extends GraphicsApplication {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final String MUSIC_FOLDER = "sound";
	private static final String CLICK = "in/clique.mp3"; 	
	private static final String THEME = "in/theme.mp3";
	private static final String DEAD = "in/dead.mp3";		// imported a new .mp3 file for click sound...
	private static final String WIN = "in/levelPass.mp3";
	private static final String[] SOUND_FILES = { "in/theme.mp3", "in/dead.mp3" };
	public static SpriteSheet sheetNew;
	public static Sprite playerArray[]= new Sprite[10];
	public static GImage playerGImage[] = new GImage[10];
	public static Sprite goombaArray[]= new Sprite[3];
	public static GImage goombaGImage[] = new GImage[3];
	private Graphics g;
	private boolean w = false;
	private boolean a = false;
	private boolean s = false;
	private boolean d = false;

	private InstructionsPane InstructionsPane;
	private MainMenu menu;
	private DeadScreen DeadScreen;
	private WinScreen WinScreen; 
	private GameScreen GameScreen; 
	private PhysicsEngine Physics;
	private Entity Mario, Goomba;
	private GRect Mario_debug_hitbox;
	private int count;

	private String currScreen = "";

	private Level levelOne = new Level("/levels/OfficialLevel1/OfficialLevel1.tmx", "/SpriteSheet/tileset.png", WINDOW_HEIGHT);
	private Level currentLevel;
	private GCompound levelCompound = new GCompound();

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
		Mario = new Entity(100, 400, 27, 50, true, Id.player, playerGImage);
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
		Mario_debug_hitbox = new GRect(Mario.getX(), Mario.getY(), Mario.getWidth(), Mario.getHeight()); // Hitbox visualizer, can be deleted
	}
	/**
	 * Initializes 1 Goomba and variables for all associated actions.
	 */
	public void GoombaInit(double x, double y) {
		Goomba = new Entity(x, y, 25, 25, true, Id.enemy, goombaGImage);//setting goomba away from Mario for testing
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
		for (GRect a : currentLevel.hitboxes_debug) {
			levelCompound.add(a);
		}

		add(levelCompound);
		add(Mario.EntImage);
		for (Entity m: Physics.movable) {
			if (m.id.equals(Id.enemy)) {
				add(m.EntImage);
				add(m.entity);
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
				Mario_debug_hitbox.setLocation(Mario.getX(), Mario.getY()); // Hitbox visualizer, can be deleted
				if (Mario.getY() > 650 || Mario.dead == true) {
					switchToDead();
				}
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

	public void switchToMenu() { // change/time the audio in the switchTo functions 
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.stopSound(MUSIC_FOLDER, DEAD);
		audio.playSound(MUSIC_FOLDER, THEME);

		if (currScreen != "MainMenu") {
			removeAll();
			for (GObject a : menu.objects) {
				add(a);
			}
			currScreen = "MainMenu";
		}
		audio.playSound(MUSIC_FOLDER, THEME);
	}

	public void switchToInstructions() {
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.stopSound(MUSIC_FOLDER, THEME);
		if (currScreen != "InstructionsPane") {
			removeAll(); //removes all the contents of the previous screen
			for (GObject a : InstructionsPane.objects) {
				add(a);
			}
			currScreen = "InstructionsPane";
		}
		playClickSound();	// called function to play click sound...
	}

	public void switchToGameScreen() {
		removeAll();

		AudioPlayer audio = AudioPlayer.getInstance();
		audio.stopSound(MUSIC_FOLDER, THEME);

		levelOne.reset();
		MarioInit();
		for (GPoint p : levelOne.goomba_points) {
			GoombaInit(p.getX(), p.getY());
		}
		GameInit();
		currScreen = "GameScreen";
	}

	public void switchToDead() {
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.stopSound(MUSIC_FOLDER, THEME);// plays the in-game music...
		audio.playSound(MUSIC_FOLDER, DEAD);		// plays the dead-screen sound...

		if (currScreen != "DeadScreen") {
			removeAll(); //removes all the contents of the previous screen
			for (GObject a : DeadScreen.objects) {
				add(a);
			}
			currScreen = "DeadScreen";
		}

	}

	public void switchToWin() {
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.playSound(MUSIC_FOLDER, WIN);		// plays the Win-screen sound...
		playClickSound();
		if (currScreen != "WinScreen") {
			removeAll(); //removes all the contents of the previous screen
			for (GObject a : WinScreen.objects) {
				add(a);
			}
			currScreen = "WinScreen";
		}
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
	public void playThemeSound() {				//function to play the theme sound...
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.playSound(MUSIC_FOLDER, THEME);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = getElementAt(e.getX(), e.getY());
		if (obj == menu.playButton) {
			switchToInstructions();
		}

		if(obj == menu.exitButton) {  //exiting from the application...
			System.exit(0);
		}

		if (obj == InstructionsPane.returnIcon) {
			playClickSound();  
			playThemeSound();//play the clique.mp3 sound on button click...
			switchToMenu();

		}

		if(obj == InstructionsPane.continueButton) {
			playClickSound();		//play the clique.mp3 sound on button click...
			switchToGameScreen();
			stopRandomSound();     //stop the ongoing sound...
		}

		if (obj == DeadScreen.quitButton) { 
			playClickSound();
			switchToMenu();   //If you quit the game, why are we calling the menu again?
			stopRandomSound();  	//stop the ongoing sound...
			System.exit(0);
		}
		if (obj== DeadScreen.playAgainButton) {
			playClickSound();
			switchToMenu();
			// switchToWin();//added to test WinScreen ***remove and uncomment line 181 for normal behavior** 
		} 
		if (obj== WinScreen.playAgainButton) {
			playClickSound();
			switchToMenu();
			playThemeSound();
		}
		if (obj == WinScreen.quitButton) {
			playClickSound();
			stopRandomSound();  	//stop the ongoing sound...
			System.exit(0);
			//switchToInstructions();
		}
	}
	private class Input implements KeyListener {
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
			// TODO Auto-generated method stub

		}	
	}
}