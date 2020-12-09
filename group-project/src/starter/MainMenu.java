package starter;

import java.awt.Color;
import java.util.ArrayList;
import acm.graphics.GImage;
import acm.graphics.GObject;

public class MainMenu {
	private GImage background;
	private GImage marioLogo;
	private GImage ground;
	private GImage cloud1;
	private GImage cloud2;
	public GButton playButton;
	public GButton exitButton;
	public ArrayList<GObject> objects = new ArrayList<GObject>();
	
	public MainMenu() {
		background = new GImage("Background/SolidBlueBackground.png", 0, 0);
		marioLogo = new GImage("Background/MarioLogo.png", 150, 50);
		ground = new GImage("Background/Ground.png", 0,550);
		ground.setSize(800.0,75.0);
		cloud1 = new GImage("Background/Cloud.png",35,100);
		cloud1.setSize(125.0,75.0);
		cloud2 = new GImage("Background/Cloud.png",625,20);
		cloud2.setSize(125.0,75.0);
		playButton = new GButton("Play", 250, 325, 300, 75);
		playButton.setFillColor(Color.RED);
		exitButton = new GButton("Exit", 250, 425, 300, 75);
		exitButton.setFillColor(Color.RED);
		objects.add(background);
		objects.add(marioLogo);
		objects.add(ground);
		objects.add(cloud1);
		objects.add(cloud2);
		objects.add(playButton);
		objects.add(exitButton);
	}
}
