package starter;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.Component;
import acm.graphics.GImage;
import acm.graphics.GObject;

public class MainApplication extends GraphicsApplication {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final String MUSIC_FOLDER = "sound";
	private static final String CLICK = "in/clique.mp3"; 	
	private static final String THEME = "in/theme.mp3";
	private static final String DEAD = "in/dead.mp3";// imported a new .mp3 file for click sound...
	private static final String WIN = "in/levelPass.mp3";
	private static final String[] SOUND_FILES = { "in/theme.mp3", "in/dead.mp3" };
	public static SpriteSheet sheet;
	public static Sprite player;
	private Graphics g;
	
	private InstructionsPane InstructionsPane;
	private MainMenu menu;
	private DeadScreen DeadScreen;
	private WinScreen WinScreen; 
	private GameScreen GameScreen; 
	
	public Player futureMario;
	public Entity futureEnemy;
	public GImage players;
	private int count;
	
	private String currScreen = "";
	

	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		sheet = new SpriteSheet("/SpriteSheet/SpriteChar.png");//Code to read in the first sprite sheet
		player = new Sprite (sheet, 2,0);
		
	}

	public void run() {
		System.out.println("Hello, world!");
		InstructionsPane = new InstructionsPane();
		DeadScreen = new DeadScreen();
		WinScreen = new WinScreen();
		menu = new MainMenu();
		GameScreen = new GameScreen(this);
		switchToMenu();                                      //Timer after menu then gameloop 
//		players = new GImage(player.getBufferedImage(), 450, 125);
//		players.setSize(60, 60);
//		add(players);//Sprite of the Mario that is represented by GImage
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
		audio.playSound(MUSIC_FOLDER, THEME);

		if (currScreen != "InstructionsPane") {
			removeAll(); //removes all the contents of the previous screen
			for (GObject a : InstructionsPane.objects) {
				add(a);
			}
			currScreen = "InstructionsPane";
			
		}
		
		playClickSound();					// called function to play click sound...
		 					// to stop the theme sound before switching to menu page...
	}
	
	public void switchToGameScreen() {
		count++;
		playRandomSound();
		switchToScreen(GameScreen); //Professor will look at a GraphicsProgram to GraphicsPane conversion***
	}
	
	public void switchToDead() {
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.stopSound(MUSIC_FOLDER, THEME);		// plays the in-game music...
		audio.playSound(MUSIC_FOLDER, DEAD);		// plays the dead-screen sound...
		playClickSound();
		
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
			switchToDead();
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
			//switchToMenu();
			switchToWin();//added to test WinScreen ***remove and uncomment line 181 for normal behavior** 
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
	
}

	
